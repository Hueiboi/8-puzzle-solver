package com.eightpuzzle.demo.model;

import java.util.List;

public interface Puzzle {
    int[][] getInitialState();
    int[][] getGoalState();
    List<int[][]> getPossibleMoves(int[][] currentState);
    boolean isGoal(int[][] currentState);
    int getDimension();
}
