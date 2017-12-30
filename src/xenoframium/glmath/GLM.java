package xenoframium.glmath;

import xenoframium.glmath.linearalgebra.*;
import xenoframium.glmath.quaternion.Quat;

import java.util.Vector;

public final class GLM {
	public static float epsilon = 1e-5f;
	
	public static Vec3 findLinePlaneIntersection(Line3 line, Plane plane) {
		float a = plane.n.x;
		float b = plane.n.y;
		float c = plane.n.z;
		float d = plane.r0.x * plane.n.x + plane.r0.y * plane.n.y + plane.r0.z * plane.n.z;
		float rhs = d - (a * line.r0.x + b * line.r0.y + c * line.r0.z);
		float lhs = a * line.a.x + b * line.a.y + c * line.a.z;
		return line.getPointAtT(rhs / lhs);
	}

	public static Line3 lineFromPoints(Vec3 point1, Vec3 point2) {
		return new Line3(point1, GLM.subt(point1, point2));
	}

	public static Plane planeFromTriangle(Triangle triangle) {
		Vec3 normal = GLM.cross(GLM.subt(triangle.b, triangle.a), GLM.subt(triangle.c, triangle.a));
		return new Plane(normal, triangle.a);
	}

	public static boolean isPointInTriangle(Triangle triangle, Vec3 point) {
		Vec3 v0 = GLM.subt(triangle.b, triangle.a);
		Vec3 v1 = GLM.subt(triangle.c, triangle.a);
		Vec3 v2 = GLM.subt(point, triangle.a);

		if (v2.dot(GLM.cross(v0, v1)) > epsilon) {
			return false;
		}

		float v0mag = v0.magSq();
		float v1mag = v1.magSq();
		
		float d01 = v0.dot(v1);
		float d20 = v2.dot(v0);
		float d21 = v2.dot(v1);

		float inverseDenominator = 1 / (v0mag * v1mag - d01 * d01);

		float u = inverseDenominator * (v1mag * d20 - d01 * d21);
		float v = inverseDenominator * (v0mag * d21 - d01 * d20);

		return u >= 0 && v >= 0 && u + v <= 1;
	}
	
	public static boolean doesLineIntersectTriangle(Line3 line, Triangle triangle) {
		return isPointInTriangle(triangle, findLinePlaneIntersection(line, planeFromTriangle(triangle)));
	}
	
	public static Mat4 perspective(float fov, float aspect, float near, float far) {
		Mat4 perspectiveMatrix = new Mat4();

		float angle = (float) Math.toRadians(fov);
		float f = (float) (1 / Math.tan(angle * 0.5));
		perspectiveMatrix.m[0][0] = f / aspect;
		perspectiveMatrix.m[1][1] = f;
		perspectiveMatrix.m[2][2] = (far + near) / (near - far);
		perspectiveMatrix.m[2][3] = -1;
		perspectiveMatrix.m[3][2] = (2 * far * near) / (near - far);
		perspectiveMatrix.m[3][3] = 0;

		return perspectiveMatrix;
	}
	
	public static Mat4 ortho(float width, float height, float near, float far) {
		Mat4 orthographicMatrix = new Mat4();

		float right = width / 2;
		float top = height / 2;
		
		orthographicMatrix.m[0][0] = 1 / right;
		orthographicMatrix.m[1][1] = 1 / top;
		orthographicMatrix.m[2][2] = -2 / (far - near);
		orthographicMatrix.m[3][2] = (near + far) / (near - far);

		return orthographicMatrix;
	}

	public static Mat4 cameraLookAt(Vec3 cameraPos, Vec3 viewTarget, Vec3 up) {
		Vec3 viewDirection = GLM.normalize(GLM.subt(viewTarget, cameraPos));
		Vec3 upDirection = GLM.normalize(up);
		Vec3 rightDirection = GLM.normalize(GLM.cross(viewDirection, upDirection));
		upDirection = GLM.cross(rightDirection, viewDirection);

		Mat4 viewMatrix = new Mat4();

		viewMatrix.m[0][0] = rightDirection.x;
		viewMatrix.m[0][1] = upDirection.x;
		viewMatrix.m[0][2] = -viewDirection.x;

		viewMatrix.m[1][0] = rightDirection.y;
		viewMatrix.m[1][1] = upDirection.y;
		viewMatrix.m[1][2] = -viewDirection.y;

		viewMatrix.m[2][0] = rightDirection.z;
		viewMatrix.m[2][1] = upDirection.z;
		viewMatrix.m[2][2] = -viewDirection.z;

		viewMatrix.m[3][0] = -rightDirection.dot(cameraPos);
		viewMatrix.m[3][1] = -upDirection.dot(cameraPos);
		viewMatrix.m[3][2] = viewDirection.dot(cameraPos);

		return viewMatrix;
	}

	public static Mat2 mult(float s, Mat2 m) {
		return new Mat2(m).mult(s);
	}

	public static Mat3 mult(float s, Mat3 m) {
		return new Mat3(m).mult(s);
	}

	public static Mat4 mult(float s, Mat4 m) {
		return new Mat4(m).mult(s);
	}

	public static Mat2 mult(Mat2... mats) {
		Mat2 res = new Mat2();
		res.mult(mats);
		return res;
	}

	public static Mat3 mult(Mat3... mats) {
		Mat3 res = new Mat3();
		res.mult(mats);
		return res;
	}

	public static Mat4 mult(Mat4... mats) {
		Mat4 res = new Mat4();
		res.mult(mats);
		return res;
	}

	public static Mat2 sum(Mat2... mats) {
		Mat2 res = new Mat2();
		res.m[0][0] = 0;
		res.m[1][1] = 0;
		res.add(mats);
		return res;
	}

	public static Mat3 sum(Mat3... mats) {
		Mat3 res = new Mat3();
		res.m[0][0] = 0;
		res.m[1][1] = 0;
		res.m[2][2] = 0;
		res.add(mats);
		return res;
	}

	public static Mat4 sum(Mat4... mats) {
		Mat4 res = new Mat4();
		res.m[0][0] = 0;
		res.m[1][1] = 0;
		res.m[2][2] = 0;
		res.m[3][3] = 0;
		res.add(mats);
		return res;
	}

	public static Vec2 sum(Vec2... vecs) {
		return new Vec2(0, 0).add(vecs);
	}

	public static Vec3 sum(Vec3... vecs) {
		return new Vec3(0, 0, 0).add(vecs);
	}

	public static Vec4 sum(Vec4... vecs) {
	    return new Vec4(0, 0, 0 ,0).add(vecs);
	}

	public static Vec2 subt(Vec2 v1, Vec2 v2) {
		return new Vec2(v1).subt(v2);
	}

	public static Vec3 subt(Vec3 v1, Vec3 v2) {
		return new Vec3(v1).subt(v2);
	}

	public static Vec4 subt(Vec4 v1, Vec4 v2) {
		return new Vec4(v1).subt(v2);
	}

	public static Vec2 mult(float s, Vec2 v2) {
		return new Vec2(v2).mult(s);
	}

	public static Vec3 mult(float s, Vec3 v2) {
		return new Vec3(v2).mult(s);
	}

	public static Vec4 mult(float s, Vec4 v2) {
		return new Vec4(v2).mult(s);
	}

	public static Vec2 div(float s, Vec2 v2) {
		return new Vec2(v2).div(s);
	}

	public static Vec3 div(float s, Vec3 v2) {
		return new Vec3(v2).div(s);
	}

	public static Vec4 div(float s, Vec4 v2) {
		return new Vec4(v2).div(s);
	}

	public static Vec3 cross(Vec3 v1, Vec3 v2) {
		return new Vec3(v1).cross(v2);
	}

	public static Vec2 normalize(Vec2 v) {
		return new Vec2(v).normalize();
	}

	public static Vec3 normalize(Vec3 v) {
		return new Vec3(v).normalize();
	}

	public static Vec4 normalize(Vec4 v) {
		return new Vec4(v).normalize();
	}

	public static Quat normalize(Quat q) {
		return new Quat(q).normalize();
	}

	public static Quat conj(Quat q) {
		return new Quat(q).conj();
	}

	public static Quat mult(float s, Quat q) {
		return new Quat(q).mult(s);
	}

	public static Quat mult(Quat q1, Quat q2) {
		return new Quat(q1).mult(q2);
	}
}
