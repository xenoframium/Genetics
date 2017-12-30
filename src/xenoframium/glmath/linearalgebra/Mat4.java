package xenoframium.glmath.linearalgebra;

import java.nio.FloatBuffer;
import java.util.Arrays;

import xenoframium.glmath.quaternion.Quat;
import xenoframium.glmath.util.GLMUtil;

public class Mat4 {

	private static final float[][] identity = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
	public float[][] m;

	public Mat4() {
		this.m = GLMUtil.deepCopy(identity);
	}

	public Mat4(Mat4 mat) {
		this.m = GLMUtil.deepCopy(mat.m);
	}

	public Mat4(float[][] m) {
		this.m = m;
	}

	public static Mat4 getIdentity() {
		return new Mat4(GLMUtil.deepCopy(identity));
	}

	public Mat4 mult(Mat4... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat4 mat : mats) {
			temp = MatrixMath.multiply(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public Mat4 mult(float scalar) {
		m = MatrixMath.multiply(scalar, m);
		return this;
	}

	public Mat4 add(Mat4... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat4 mat : mats) {
			temp = MatrixMath.add(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public Mat4 subt(Mat4... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat4 mat : mats) {
			temp = MatrixMath.subtract(temp, mat.m);
		}
		m = temp;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Mat4 mat4 = (Mat4) o;

		return Arrays.deepEquals(m, mat4.m);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(m);
	}

	public float det() {
		return MatrixMath.determinant(m);
	}

	public Mat4 trans() {
		m = MatrixMath.transpose(m);
		return this;
	}

	public Mat4 inv() {
		m = MatrixMath.inverse(m);
		return this;
	}

	public Mat4 translate(Vec3 vec) {
		m = new Mat4(new float[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { vec.x, vec.y, vec.z, 1 } }).mult(this).m;
		return this;
	}

	public Mat4 scale(Vec3 vec) {
		m = new Mat4(new float[][] { { vec.x, 0, 0, 0 }, { 0, vec.y, 0, 0 }, { 0, 0, vec.z, 0 }, { 0, 0, 0, 1 } }).mult(this).m;
		return this;
	}

	public Mat4 rotate(Quat quat) {
		m = quat.toRotMat().mult(this).m;
		return this;
	}

	public Mat4 rotate(Vec3 axis, float angle) {
		rotate(new Quat(axis, angle));
		return this;
	}

	public Vec4 mult(Vec4 vec) {
		float[] result = MatrixMath.multiply(m, vec.asArr());

		return new Vec4(result[0], result[1], result[2], result[3]);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				b.append(m[i][j]);
				if (i!=3 || j!=3) {
					b.append(',');
				}
				if (i != 3) {
					b.append(' ');
				}
			}
			if (j != 3) {
				b.append('\n');
			}
		}
		return b.toString();
	}

	public float[] asArr() {
		float[] res = new float[m.length * m.length];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				res[i*4 + j] = m[i][j];
			}
		}
		return res;
	}
}
