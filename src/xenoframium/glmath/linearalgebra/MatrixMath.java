package xenoframium.glmath.linearalgebra;

class MatrixMath {
	static float[][] multiply(float[][] mat1, float[][] mat2) {
		int size = mat1.length;
		float[][] outputArray = new float[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					outputArray[i][j] += mat1[k][j] * mat2[i][k];
				}
			}
		}
		return outputArray;
	}

	static float[][] multiply(float scalar, float[][] mat) {
		int size = mat.length;
		float[][] outputArray = new float[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				outputArray[i][j] = mat[i][j] *= scalar;
			}
		}
		return outputArray;
	}

	static float[][] add(float[][] mat1, float[][] mat2) {
		int size = mat1.length;
		float[][] outputArray = new float[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				outputArray[i][j] = mat1[i][j] + mat2[i][j];
			}
		}
		return outputArray;
	}

	static float[][] subtract(float[][] mat1, float[][] mat2) {
		int size = mat1.length;
		float[][] outputArray = new float[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				outputArray[i][j] = mat1[i][j] - mat2[i][j];
			}
		}
		return outputArray;
	}

	static float determinant(float[][] mat) {
		int size = mat.length;

		// The augmented matrix
		float[][] temp = new float[size][size];

		// Transpose matrix
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				temp[j][i] = mat[i][j];
			}
		}

		// Make pivots non-zero
		for (int pivot = 0; pivot < size; pivot++) {
			if (temp[pivot][pivot] == 0) {
				for (int row = 0; row < size; row++) {
					if (temp[row][pivot] != 0) {
						for (int column = 0; column < size; column++) {
							temp[pivot][column] += temp[row][column];
						}

						break;
					}
				}
			}
		}

		// To reduced row echelon form using Gaussian elimination with pivoting
		for (int pivot = 0; pivot < size; pivot++) {
			for (int row = pivot + 1; row < size; row++) {
				float multiple = temp[row][pivot] / temp[pivot][pivot];

				for (int column = pivot + 1; column < size; column++) {
					temp[row][column] -= temp[pivot][column] * multiple;
				}

				temp[row][pivot] = 0;
			}
		}

		float determinant = 1;
		for (int i = 0; i < size; i++) {
			determinant *= temp[i][i];
		}

		return determinant;
	}

	static float[][] transpose(float[][] mat) {
		int size = mat.length;
		float[][] outputArray = new float[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				outputArray[i][j] = mat[j][i];
			}
		}

		return outputArray;
	}

	static float[][] inverse(float[][] mat) {
		int size = mat.length;

		// The augmented matrix
		float[][] augment = new float[size][size * 2];

		// Transpose matrix
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				augment[j][i] = mat[i][j];
			}
		}

		// Make right hand matrix identity
		for (int i = 0; i < size; i++) {
			augment[i][i + size] = 1;
		}

		// Make pivots non-zero
		for (int pivot = 0; pivot < size; pivot++) {
			if (augment[pivot][pivot] == 0) {
				for (int row = 0; row < size; row++) {
					if (augment[row][pivot] != 0) {
						for (int column = 0; column < size * 2; column++) {
							augment[pivot][column] += augment[row][column];
						}

						break;
					}
				}
			}
		}

		// To row echelon form using Gaussian elimination with pivoting
		for (int pivot = 0; pivot < size; pivot++) {
			float pivotInverse = 1 / augment[pivot][pivot];
			augment[pivot][pivot] = 1;

			for (int column = pivot + 1; column < size * 2; column++) {
				augment[pivot][column] *= pivotInverse;
			}

			for (int row = pivot + 1; row < size; row++) {
				for (int column = pivot + 1; column < size * 2; column++) {
					augment[row][column] -= augment[pivot][column] * augment[row][pivot];
				}

				augment[row][pivot] = 0;
			}
		}

		// Continue to reduced row echelon form using Gauss-Jordan elimination
		// with pivoting
		for (int pivot = size - 1; pivot >= 0; pivot--) {
			for (int row = 0; row < pivot; row++) {
				for (int column = size; column < size * 2; column++) {
					augment[row][column] -= augment[pivot][column] * augment[row][pivot];
				}

				augment[row][pivot] = 0;
			}
		}

		float[][] inverse = new float[size][size];
		
		// Transpose right hand matrix and place into inverse
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				inverse[j][i] = augment[i][j + size];
			}
		}

		return inverse;
	}

	static float[] multiply(float[][] mat, float[] vec) {
		float[] temp = new float[mat.length];

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				temp[j] += mat[i][j] * vec[i];
			}
		}

		return temp;
	}
}
