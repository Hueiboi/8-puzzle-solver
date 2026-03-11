package com.eightpuzzle.demo.model;

import java.util.*;

public class PuzzleSolver {

    public List<int[][]> solve(Puzzle puzzle) {
        PriorityQueue<MyNode> openList = new PriorityQueue<>();
        Set<String> closedList = new HashSet<>();

        MyNode startNode = new MyNode(puzzle.getInitialState(), puzzle.getGoalState(), 0, null, puzzle.getDimension());
        openList.add(startNode);

        while (!openList.isEmpty()) {
            MyNode current = openList.poll();

            if (puzzle.isGoal(current.board)) {
                return getPath(current);
            }

            closedList.add(Arrays.deepToString(current.board));

            for (int[][] neighborBoard : puzzle.getPossibleMoves(current.board)) {
                if (!closedList.contains(Arrays.deepToString(neighborBoard))) {
                    MyNode neighborNode = new MyNode(neighborBoard, puzzle.getGoalState(), current.g + 1, current, puzzle.getDimension());
                    openList.add(neighborNode);
                }
            }
        }
        return null; // No solution found
    }

    private List<int[][]> getPath(MyNode current) {
        List<int[][]> path = new ArrayList<>();
        while (current != null) {
            path.add(current.board);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
