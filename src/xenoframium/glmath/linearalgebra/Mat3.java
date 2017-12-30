package xenoframium.glmath.linearalgebra;

import java.nio.FloatBuffer;
import java.util.Arrays;

import xenoframium.glmath.util.GLMUtil;

public class Mat3 {

	private static final float[][] identity = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	public float[][] m;

	public Mat3() {
		this.m = GLMUtil.deepCopy(identity);
	}

	public Mat3(Mat3 mat) {
		this.m = GLMUtil.deepCopy(mat.m);
	}

	public Mat3(float[][] m) {
		this.m = m;
	}

	public static Mat3 getIdentity() {
		return new Mat3(GLMUtil.deepCopy(identity));
	}

	public Mat3 mult(Mat3... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat3 mat : mats) {
			temp = MatrixMath.multiply(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public Mat3 mult(float scalar) {
		m = MatrixMath.multiply(scalar, m);
		return this;
	}

	public Mat3 add(Mat3... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat3 mat : mats) {
			temp = MatrixMath.add(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public Mat3 subt(Mat3... mats) {
		float[][] temp = GLMUtil.deepCopy(m);
		for (Mat3 mat : mats) {
			temp = MatrixMath.subtract(temp, mat.m);
		}
		m = temp;
		return this;
	}

	public float det() {
		return MatrixMath.determinant(m);
	}

	public Mat3 trans() {
		m = MatrixMath.transpose(m);
		return this;
	}

	public Mat3 inv() {
		m = MatrixMath.inverse(m);
		return this;
	}
	
	public Vec3 mult(Vec3 vec) {
		float[] result = MatrixMath.multiply(m, vec.asArr());
		
		return new Vec3(result[0], result[1], result[2]);
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

		Mat3 mat3 = (Mat3) o;

		return Arrays.deepEquals(m, mat3.m);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				b.append(m[i][j]);
				if (i!=3 || j!=3) {
					b.append(',');
				}
				if (i != 2) {
					b.append(' ');
				}
			}
			if (j != 2) {
				b.append('\n');
			}
		}
		return b.toString();
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(m);
	}
}
