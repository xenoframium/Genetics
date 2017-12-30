package xenoframium.glmath.linearalgebra;

import java.nio.FloatBuffer;

import xenoframium.glmath.util.GLMUtil;

public class Vec4 {
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4(Vec4 vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = vec.w;
	}
	
	public Vec4(Vec3 vec, float w) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = w;
	}
	
	public float dot(Vec4 vec) {
		return x * vec.x + y * vec.y + z * vec.z + w * vec.w;
	}

	public Vec4 add(Vec4... vecs) {
		float xx=x, yy=y, zz=z, ww=w;
		for (Vec4 vec : vecs) {
			xx+=vec.x;
			yy+=vec.y;
			zz+=vec.z;
			ww+=vec.w;
		}
		this.x = xx;
		this.y = yy;
		this.z = zz;
		this.w = ww;
		return this;
	}
	
	public Vec4 subt(Vec4 vec) {
		x-=vec.x;
		y-=vec.y;
		z-=vec.z;
		w-=vec.w;
		return this;
	}
	
	public float mag() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public float magSq() {
		return x * x + y * y  + z * z + w * w;
	}
	
	public Vec4 mult(float scalar) {
		return new Vec4(x * scalar, y * scalar, z * scalar, w * scalar);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vec4 vec4 = (Vec4) o;

		if (Float.compare(vec4.x, x) != 0) return false;
		if (Float.compare(vec4.y, y) != 0) return false;
		if (Float.compare(vec4.z, z) != 0) return false;
		return Float.compare(vec4.w, w) == 0;
	}

	@Override
	public int hashCode() {
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
		return result;
	}

	public Vec4 div(float scalar) {
		float inverseDenom = 1 / scalar;
		x*=inverseDenom;
		y*=inverseDenom;
		z*=inverseDenom;
		w*=inverseDenom;
		return this;
	}
	
	public Vec4 normalize() {
	    div(mag());
	    return this;
	}

	@Override
	public String toString() {
		return String.format("x: %f, y: %f, z: %f, w: %f", x, y, z, w);
	}

	public float[] asArr() {
		return new float[]{x, y, z, w};
	}
}
