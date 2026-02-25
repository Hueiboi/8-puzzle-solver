package com.eightpuzzle.demo.model;
import java.util.*;

public class PuzzleSolver {
    public List<MyNode> getNeighbors(MyNode current, int[] goal) {
        List<MyNode> neighbors = new ArrayList<>();

        int[] moves = {-3, 3 ,-1, 1};

        for(int move: moves) {
            int nextPos = current.zeroPos + move;

            if(isValid(current.zeroPos, nextPos)) {
                int[] newBoard = current.board.clone();

                newBoard[current.zeroPos] = current.board[nextPos];
                newBoard[nextPos] = 0;

                neighbors.add(new MyNode(newBoard, goal, current.g + 1, current));
            }
        }

        return neighbors;
    }

    private boolean isValid(int current, int next) {
        if(next < 0 || next >= 9) return false;
        int currRow = current / 3, currCol = current % 3;
        int nextRow = next / 3, nextCol = next % 3;
        return (Math.abs(currRow - nextRow) + Math.abs(currCol - nextCol) == 1);
    }

    public List<MyNode> solve(int[] initial, int[] goal) {
        PriorityQueue<MyNode> openList = new PriorityQueue<>();

        Set<String> closedList = new HashSet<>();

        MyNode startNode = new MyNode(initial, goal, 0, null);
        openList.add(startNode);

        while(!openList.isEmpty()) {
            MyNode current = openList.poll();

            if(current.h == 0) return getPath(current);

            closedList.add(Arrays.toString(current.board));

            for(MyNode neighbor : getNeighbors(current, goal)) {
                if(!closedList.contains(Arrays.toString(neighbor.board))) {
                    openList.add(neighbor);
                }
            }
        }
        return null;
    }

    private List<MyNode> getPath(MyNode current) {
        List<MyNode> path = new ArrayList<>();
        while(current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
