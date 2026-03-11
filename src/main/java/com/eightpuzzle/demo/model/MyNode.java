package com.eightpuzzle.demo.model;

import java.util.Arrays;

public class MyNode implements Comparable<MyNode> {
    public int[][] board;
    int f, g, h;
    int zeroRow, zeroCol; // Updated to 2D coordinates
    MyNode parent;
    private int dimension; // Added dimension

    public MyNode(int[][] board, int[][] goal, int g, MyNode parent, int dimension) {
        this.dimension = dimension;
        this.board = copy(board); // Deep copy
        this.g = g;
        this.parent = parent;

        // Find the empty tile (0) position
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    this.zeroRow = i;
                    this.zeroCol = j;
                    break;
                }
            }
        }

        // Manhattan distance heuristic
        this.h = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int value = this.board[i][j];
                if (value != 0) {
                    int[] targetPos = findPosition(goal, value); // Returns {row, col}
                    if (targetPos != null) {
                        int targetX = targetPos[0];
                        int targetY = targetPos[1];
                        this.h += Math.abs(i - targetX) + Math.abs(j - targetY);
                    }
                }
            }
        }
        this.f = this.h + this.g;
    }

    private int[] findPosition(int[][] goal, int value) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (goal[i][j] == value) {
                    return new int[]{i, j};
                }
            }
        }
        return null; // Should not happen in a valid puzzle
    }

    @Override
    public int compareTo(MyNode other) {
        return Integer.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        MyNode myNode = (MyNode) other;
        return Arrays.deepEquals(board, myNode.board); // Use deepEquals for 2D array
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board); // Use deepHashCode for 2D array
    }

    // Helper method to create a deep copy of the puzzle state
    private int[][] copy(int[][] state) {
        int[][] newGrid = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(state[i], 0, newGrid[i], 0, dimension);
        }
        return newGrid;
    }
}
