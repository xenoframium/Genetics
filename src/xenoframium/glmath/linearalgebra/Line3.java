package xenoframium.glmath.linearalgebra;

import xenoframium.glmath.GLM;

//z = r + at
public class Line3 {
	public Vec3 r0;
	public Vec3 a;
	
	public Line3(Vec3 r, Vec3 a) {
		this.r0 = new Vec3(r);
		this.a = new Vec3(a);
	}

	public Line3(Line3 lin) {
		this.r0 = new Vec3(lin.r0);
		this.a = new Vec3(lin.a);
	}

	public Line3(float x0, float a, float y0, float b, float z0, float c) {
		r0 = new Vec3(x0, y0, z0);
		this.a = new Vec3(a, b, c);
	}
	
	public Line3 transform(Mat4 transformationMatrix) {
		this.r0 = new Vec3(transformationMatrix.mult(new Vec4(r0.x, r0.y, r0.z, 1)));
		this.a = new Vec3(transformationMatrix.mult(new Vec4(a.x, a.y, a.z, 0)));
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Line3 line3 = (Line3) o;

		if (r0 != null ? !r0.equals(line3.r0) : line3.r0 != null) return false;
		return a != null ? a.equals(line3.a) : line3.a == null;
	}

	@Override
	public int hashCode() {
		int result = r0 != null ? r0.hashCode() : 0;
		result = 31 * result + (a != null ? a.hashCode() : 0);
		return result;
	}

	public Vec3 getPointAtT(float t) {
		return new Vec3(a).mult(t).add(r0);
	}
}
