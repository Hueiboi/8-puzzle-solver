package com.eightpuzzle.demo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FifteenPuzzle implements Puzzle {

    private static final int DIMENSION = 4; // For 15-puzzle, it's a 4x4 grid
    private int[][] initialState;
    private int[][] goalState;

    public FifteenPuzzle(int[][] initialState) {
        this.initialState = initialState;
        this.goalState = generateGoalState();
    }

    private int[][] generateGoalState() {
        int[][] goal = new int[DIMENSION][DIMENSION];
        int counter = 1;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                goal[i][j] = counter % (DIMENSION * DIMENSION); // 0 will be the last tile
                counter++;
            }
        }
        return goal;
    }

    @Override
    public int[][] getInitialState() {
        return copy(initialState);
    }

    @Override
    public int[][] getGoalState() {
        return copy(goalState);
    }

    @Override
    public List<int[][]> getPossibleMoves(int[][] currentState) {
        List<int[][]> moves = new ArrayList<>();
        int emptyRow = -1;
        int emptyCol = -1;

        // Find the empty tile (0)
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (currentState[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
            if (emptyRow != -1) break;
        }

        // Define possible directions: up, down, left, right
        int[] dr = {-1, 1, 0, 0}; // delta row
        int[] dc = {0, 0, -1, 1}; // delta col

        for (int i = 0; i < 4; i++) {
            int newRow = emptyRow + dr[i];
            int newCol = emptyCol + dc[i];

            if (isValid(newRow, newCol)) {
                int[][] newState = copy(currentState);
                // Swap empty tile with the adjacent tile
                newState[emptyRow][emptyCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                moves.add(newState);
            }
        }
        return moves;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < DIMENSION && col >= 0 && col < DIMENSION;
    }

    @Override
    public boolean isGoal(int[][] currentState) {
        return Arrays.deepEquals(currentState, goalState);
    }

    @Override
    public int getDimension() {
        return DIMENSION;
    }

    // Helper method to create a deep copy of the puzzle state
    private int[][] copy(int[][] state) {
        int[][] newGrid = new int[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            System.arraycopy(state[i], 0, newGrid[i], 0, DIMENSION);
        }
        return newGrid;
    }
}
