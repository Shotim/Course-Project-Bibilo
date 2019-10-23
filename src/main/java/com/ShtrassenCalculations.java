package com;

public class ShtrassenCalculations {

    //correct
    public int[][] multiplierOf2x2Matrix(int[][] a, int[][] b) {
        int[][] resultMatrix = new int[2][2];

        int d1 = (a[0][0] + a[1][1]) * (b[0][0] + b[1][1]);
        int d2 = (a[1][0] + a[1][1]) * b[0][0];
        int d3 = a[0][0] * (b[0][1] - b[1][1]);
        int d4 = a[1][1] * (-b[0][0] + b[1][0]);
        int d5 = (a[0][0] + a[0][1]) * b[1][1];
        int d6 = (-a[0][0] + a[1][0]) * (b[0][0] + b[0][1]);
        int d7 = (a[0][1] - a[1][1]) * (b[1][0] + b[1][1]);

        resultMatrix[0][0] = d1 + d4 - d5 + d7;
        resultMatrix[1][0] = d2 + d4;
        resultMatrix[0][1] = d3 + d5;
        resultMatrix[1][1] = d1 + d3 - d2 + d6;
        return resultMatrix;
    }

    //correct
    public int[][] multiplierOf4x4Matrix(int[][] a, int[][] b) {

        int[][][] partitionedMatrixA = partitionOfMatrix(a);
        int[][][] partitionedMatrixB = partitionOfMatrix(b);

        int[][][] dComponents = dComponents2x2(partitionedMatrixA, partitionedMatrixB);

        int[][][] subMatrixes = subMatrixes(dComponents);

        return resultMatrix(subMatrixes);
    }

    public int[][] multiplierOf8x8Matrix(int[][] a, int[][] b) {

        int[][][] partitionedMatrixA = partitionOfMatrix(a);
        int[][][] partitionedMatrixB = partitionOfMatrix(b);

        int[][][] dComponents = dComponents4x4(partitionedMatrixA, partitionedMatrixB);

        int[][][] subMatrixes = subMatrixes(dComponents);

        return resultMatrix(subMatrixes);
    }

    //correct
    private int[][][] subMatrixes(int[][][] dComponents) {

        final int FOUR = 4;
        final int RADIX = dComponents[0].length;

        int[][][] subMatrixes = new int[FOUR][RADIX][RADIX];

        subMatrixes[0] = sum(sum(dComponents[0], dComponents[3]), sum(inv(dComponents[4]), dComponents[6]));
        subMatrixes[2] = sum(dComponents[1], dComponents[3]);
        subMatrixes[1] = sum(dComponents[2], dComponents[4]);
        subMatrixes[3] = sum(sum(dComponents[0], dComponents[2]), sum(inv(dComponents[1]), dComponents[5]));

        return subMatrixes;
    }

    //correct
    private int[][][] dComponents2x2(int[][][] partitionedMatrixA, int[][][] partitionedMatrixB) {

        final int RADIX = partitionedMatrixA.length;
        final int SEVEN = 7;

        int[][][] dComponents = new int[SEVEN][RADIX][RADIX];

        dComponents[0] = multiplierOf2x2Matrix(sum(partitionedMatrixA[0], partitionedMatrixA[3]),
                sum(partitionedMatrixB[0], partitionedMatrixB[3]));

        dComponents[1] = multiplierOf2x2Matrix(sum(partitionedMatrixA[2], partitionedMatrixA[3]), partitionedMatrixB[0]);

        dComponents[2] = multiplierOf2x2Matrix(partitionedMatrixA[0], sum(partitionedMatrixB[1], inv(partitionedMatrixB[3])));

        dComponents[3] = multiplierOf2x2Matrix(partitionedMatrixA[3], sum(inv(partitionedMatrixB[0]), partitionedMatrixB[2]));

        dComponents[4] = multiplierOf2x2Matrix(sum(partitionedMatrixA[0], partitionedMatrixA[1]), partitionedMatrixB[3]);

        dComponents[5] = multiplierOf2x2Matrix(sum(inv(partitionedMatrixA[0]), partitionedMatrixA[2]),
                sum(partitionedMatrixB[0], partitionedMatrixB[1]));

        dComponents[6] = multiplierOf2x2Matrix(sum(partitionedMatrixA[1], inv(partitionedMatrixA[3])),
                sum(partitionedMatrixB[2], partitionedMatrixB[3]));

        return dComponents;
    }

    private int[][][] dComponents4x4(int[][][] partitionedMatrixA, int[][][] partitionedMatrixB) {

        final int RADIX = partitionedMatrixA.length;
        final int SEVEN = 7;

        int[][][] dComponents = new int[SEVEN][RADIX][RADIX];

        dComponents[0] = multiplierOf4x4Matrix(sum(partitionedMatrixA[0], partitionedMatrixA[3]),
                sum(partitionedMatrixB[0], partitionedMatrixB[3]));

        dComponents[1] = multiplierOf4x4Matrix(sum(partitionedMatrixA[2], partitionedMatrixA[3]), partitionedMatrixB[0]);

        dComponents[2] = multiplierOf4x4Matrix(partitionedMatrixA[0], sum(partitionedMatrixB[1], inv(partitionedMatrixB[3])));

        dComponents[3] = multiplierOf4x4Matrix(partitionedMatrixA[3], sum(inv(partitionedMatrixB[0]), partitionedMatrixB[2]));

        dComponents[4] = multiplierOf4x4Matrix(sum(partitionedMatrixA[0], partitionedMatrixA[1]), partitionedMatrixB[3]);

        dComponents[5] = multiplierOf4x4Matrix(sum(inv(partitionedMatrixA[0]), partitionedMatrixA[2]),
                sum(partitionedMatrixB[0], partitionedMatrixB[1]));

        dComponents[6] = multiplierOf4x4Matrix(sum(partitionedMatrixA[1], inv(partitionedMatrixA[3])),
                sum(partitionedMatrixB[2], partitionedMatrixB[3]));

        return dComponents;
    }

    //correct
    private int[][] resultMatrix(int[][][] subMatrixes) {

        final int TWO = 2;
        final int RADIX = subMatrixes[0].length;

        int[][] resultMatrix = new int[TWO * RADIX][TWO * RADIX];

        for (int i = 0; i < TWO; i++) {
            for (int j = 0; j < TWO; j++) {
                resultMatrix[i][j] = subMatrixes[0][i][j];
                resultMatrix[i][j + TWO] = subMatrixes[1][i][j];
                resultMatrix[i + TWO][j] = subMatrixes[2][i][j];
                resultMatrix[i + TWO][j + TWO] = subMatrixes[3][i][j];
            }
        }
        return resultMatrix;
    }

    //correct
    private int[][][] partitionOfMatrix(int[][] matrix) {

        final int RADIX = matrix.length;
        final int HALF_RADIX = RADIX / 2;
        final int FOUR = 4;

        int[][][] partitionedMatrix = new int[FOUR][HALF_RADIX][HALF_RADIX];
        for (int i = 0; i < FOUR; i++) {
            for (int j = 0; j < HALF_RADIX; j++) {
                for (int k = 0; k < HALF_RADIX; k++) {
                    partitionedMatrix[i][j][k] = matrix[j + HALF_RADIX * (i / 2)][k + HALF_RADIX * (i % 2)];
                }
            }
        }

        return partitionedMatrix;
    }

    //correct
    private int[][] sum(int[][] a, int[][] b) {

        final int ZERO = 0;

        int[][] resultMatrix = new int[a.length][a.length];
        for (int i = ZERO; i < resultMatrix.length; i++) {
            for (int j = ZERO; j < resultMatrix[i].length; j++) {
                resultMatrix[i][j] = a[i][j] + b[i][j];
            }
        }
        return resultMatrix;
    }

    //correct
    private int[][] inv(int[][] a) {

        final int ZERO = 0;

        int[][] result = new int[a.length][a.length];
        for (int i = ZERO; i < a.length; i++) {
            for (int j = ZERO; j < a.length; j++) {
                result[i][j] = -a[i][j];
            }
        }
        return result;
    }

    //correct
    public int[][] expansionedMatrix(int[][] initialMatrix) {

        final int ZERO = 0;

        int previousRadix = initialMatrix.length;
        int newRadix = newRadix(previousRadix);
        int[][] expansionedMatrix = new int[newRadix][newRadix];

        for (int i = ZERO; i < newRadix; i++) {
            for (int j = ZERO; j < newRadix; j++) {
                if (i < previousRadix && j < previousRadix) {
                    expansionedMatrix[i][j] = initialMatrix[i][j];
                } else {
                    expansionedMatrix[i][j] = ZERO;
                }
            }
        }
        return expansionedMatrix;
    }

    //correct
    private int newRadix(int previousRadix) {
        int newRadix = 1;

        do {
            newRadix *= 2;
        } while (newRadix < previousRadix);
        return newRadix;
    }

}
