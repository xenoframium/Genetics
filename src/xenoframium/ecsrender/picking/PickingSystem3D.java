package xenoframium.ecsrender.picking;

import org.lwjgl.glfw.GLFW;
import xenoframium.ecs.*;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;
import xenoframium.ecsrender.GraphicsManager;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.ecsrender.gl.Camera;
import xenoframium.ecsrender.gl.Projection;
import xenoframium.ecsrender.gl.Window;
import xenoframium.ecsrender.input.InputManager;
import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.*;

import java.util.Set;

/**
 * Created by chrisjung on 18/12/17.
 */

public class PickingSystem3D implements BaseSystem {
    public static final EventID PICK_EVENT_3D = new EventID();
    public static final EventID UNPICK_EVENT_3D = new EventID();

    private static EntityFilter filter = new BasicFilter(PickingComponent3D.class);

    private Entity selectedEntity;
    private Vec3 intersectionPoint;
    private Vec3 triangleNormal;

    public Projection projection;
    public Camera camera;
    public float reach;

    public PickingSystem3D(Camera camera, Projection projection, float reach) {
        this.projection = projection;
        this.camera = camera;
        this.reach = reach;
    }

    private void pickEntities(Space space, Set<Entity> entities) {
        selectedEntity = null;
        intersectionPoint = null;
        triangleNormal = null;

        Window window = GraphicsManager.getWindow();
        float maxT = Float.NEGATIVE_INFINITY;
        Vec4 ndc = new Vec4(0, 0, -1, 1);
        ndc.x = (float) InputManager.getCursorX() / window.width * 2 - 1;
        ndc.y = (float) -InputManager.getCursorY() / window.height * 2 + 1;
        ndc.z = -1;
        if (window.isCursorDisabled()) {
            ndc = new Vec4(0, 0, -1, 1);
        }
        Mat4 ip = projection.getMat().inv();
        Vec4 ws1 = ip.mult(ndc);
        ws1.div(ws1.w);
        Line3 cameraSpaceline = GLM.lineFromPoints(new Vec3(0, 0, 0), new Vec3(ws1.x, ws1.y, ws1.z));
        cameraSpaceline.a.normalize();
        Mat4 ic = camera.getMat().inv();
        Vec3 cameraPos = new Vec3(ic.m[3][0], ic.m[3][1], ic.m[3][2]);

        PickingComponent3D collisionComponent = null;
        Entity pickedEntity = null;
        Vec3 rayIntersection = null;
        Vec3 modelIntersection = null;
        Vec3 normal = null;
        int tc = 0;

        for (Entity collidable : entities) {
            PickingComponent3D comp = collidable.getComponent(PickingComponent3D.class);
            Mat4 modelMatrix = collidable.getComponent(TransformComponent3D.class).getModelMatrix();

            Mat4 mv = camera.getMat().mult(modelMatrix);

            Mat4 imv = new Mat4(mv).inv();

            Triangle[] mesh = comp.triangles;

            //Line sphere early culling
            {
                Line3 modelSpaceLine = new Line3(cameraSpaceline).transform(new Mat4(imv));

                Vec3 l = new Vec3(modelSpaceLine.a).normalize();
                Vec3 o = modelSpaceLine.r0;

                float dot = l.dot(o);
                if (dot * dot - o.magSq() + comp.radiusSq < 0) {
                    continue;
                }
            }

            for (Triangle triangle : mesh) {
                Plane trianglePlane = GLM.planeFromTriangle(triangle);
                Plane cameraSpacePlane = new Plane(trianglePlane).transform(mv);
                if (cameraSpacePlane.n.dot(cameraSpaceline.a) < 0) {
                    continue;
                }
                tc++;
                Vec4 cameraSpaceIntersection = new Vec4(GLM.findLinePlaneIntersection(cameraSpaceline, cameraSpacePlane), 1);
                Vec3 modelSpaceIntersection = new Vec3(imv.mult(cameraSpaceIntersection));
                if (GLM.isPointInTriangle(triangle, modelSpaceIntersection)) {
                    float cameraSpaceT;

                    if (cameraSpaceline.a.z != 0) {
                        cameraSpaceT = new Vec3(cameraSpaceIntersection).subt(cameraSpaceline.r0).z / cameraSpaceline.a.z;
                    } else if (cameraSpaceline.a.y != 0) {
                        cameraSpaceT = new Vec3(cameraSpaceIntersection).subt(cameraSpaceline.r0).y / cameraSpaceline.a.y;
                    } else {
                        cameraSpaceT = new Vec3(cameraSpaceIntersection).subt(cameraSpaceline.r0).x / cameraSpaceline.a.x;
                    }

                    if (maxT < cameraSpaceT && cameraSpaceT < 0) {
                        rayIntersection = new Vec3(cameraSpaceIntersection);
                        modelIntersection = modelSpaceIntersection;
                        maxT = cameraSpaceT;
                        collisionComponent = comp;
                        pickedEntity = collidable;
                        normal = trianglePlane.n;
                    }
                }
            }
        }

        if (selectedEntity != null && (selectedEntity != pickedEntity || rayIntersection.mag() >= reach)) {
            EventBus.post(new Event(UNPICK_EVENT_3D, new UnpickEventData3D()), space, selectedEntity);
        }
        if (collisionComponent != null && rayIntersection.mag() < reach) {
            EventBus.post(new Event(PICK_EVENT_3D, new PickEventData3D(modelIntersection, normal)), space, pickedEntity);
            selectedEntity = pickedEntity;
            intersectionPoint = modelIntersection;
            triangleNormal = normal;
        }
    }

    @Override
    public void update(Space space, double delta, double time) {
        Set<Entity> entities = space.getEntities(filter);
        pickEntities(space, entities);
    }

    public PickInfo3D pick() {
        if (selectedEntity == null) {
            return new PickInfo3D(null, null, null);
        }
        return new PickInfo3D(selectedEntity, new Vec3(intersectionPoint), new Vec3(triangleNormal));
    }
}