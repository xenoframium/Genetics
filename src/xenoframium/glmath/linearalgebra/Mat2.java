package xenoframium.glmath.linearalgebra;

import java.nio.FloatBuffer;
import java.util.Arrays;

import xenoframium.glmath.util.GLMUtil;

public class Mat2 {

	private static final float[][] identity = { { 1, 0 }, { 0, 1 } };

	public float[][] m;

	public Mat2() {
		this.m = GLMUtil.deepCopy(identity);
	}

	public Mat2(Mat2 mat) {
		this.m = GLMUtil.deepCopy(mat.m);
	}

	public Mat2(float[][] m) {
		this.m = m;
	}

	public static Mat2 getIdentity() {
		return new Mat2(GLMUtil.deepCopy(identity));
	}
	
	public Mat2 mult(Mat2... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat2 mat : mats) {
			temp = MatrixMath.multiply(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public Mat2 mult(float scalar) {
		m = MatrixMath.multiply(scalar, m);
		return this;
	}

	public Mat2 add(Mat2... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat2 mat : mats) {
			temp = MatrixMath.add(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public Mat2 subt(Mat2... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat2 mat : mats) {
			temp = MatrixMath.add(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public float det() {
		return MatrixMath.determinant(m);
	}

	public Mat2 trans() {
		m = MatrixMath.transpose(m);
		return this;
	}

	public Mat2 inv() {
		m = MatrixMath.inverse(m);
		return this;
	}

	public Vec2 mult(Vec2 vec) {
		return new Vec2(vec.x * m[0][0] + vec.y * m[1][0], vec.x * m[0][1] + vec.y * m[1][1]);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Mat2 mat2 = (Mat2) o;

		return Arrays.deepEquals(m, mat2.m);
	}

	@Override
	public String toString() {
		return String.format("%f, %f,\n%f, %f", m[0][0], m[1][0], m[0][1], m[1][1]);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(m);
	}
}
