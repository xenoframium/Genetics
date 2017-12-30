package xenoframium.glmath.linearalgebra;

public class Triangle {
	public Vec3 a;
	public Vec3 b;
	public Vec3 c;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Triangle triangle = (Triangle) o;

		if (a != null ? !a.equals(triangle.a) : triangle.a != null) return false;
		if (b != null ? !b.equals(triangle.b) : triangle.b != null) return false;
		return c != null ? c.equals(triangle.c) : triangle.c == null;
	}

	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (b != null ? b.hashCode() : 0);
		result = 31 * result + (c != null ? c.hashCode() : 0);
		return result;
	}

	public Triangle(Vec3 vert1, Vec3 vert2, Vec3 vert3) {
		a = new Vec3(vert1);
		b = new Vec3(vert2);
		c = new Vec3(vert3);
	}

	public Triangle transform(Mat4 transformationMatrix) {
		Vec4 newA = transformationMatrix.mult(new Vec4(a.x, a.y, a.z, 1));
		Vec4 newB = transformationMatrix.mult(new Vec4(b.x, b.y, b.z, 1));
		Vec4 newC = transformationMatrix.mult(new Vec4(c.x, c.y, c.z, 1));
		return new Triangle(new Vec3(newA.x, newA.y, newA.z), new Vec3(newB.x, newB.y, newB.z),
				new Vec3(newC.x, newC.y, newC.z));
	}
}
