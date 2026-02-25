package com.eightpuzzle.demo.model;

import java.util.Arrays;

public class MyNode implements Comparable<MyNode> {
    public int[] board;
    int f,g,h;
    int zeroPos;
    MyNode parent; // Lưu lại bước đi trước

    public MyNode(int[] board, int[] goal, int g, MyNode parent) {
        this.board = board.clone();
        this.g = g;
        this.parent = parent;

        // Vị trí = 0 sẽ là ô trống
        for(int i = 0; i < 9; i++) {
            if(board[i] == 0) {
                this.zeroPos = i;
                break;
            }
        }

        // Manhattan distance (tổng của hiệu vị trí hiện tại và vị trí đích)
        // Nếu vị trí đích cố định, có thể sử dụng lookup table
        this.h = 0;
        for(int i = 0; i < 9; i++) {
            int value = this.board[i];
            if(value != 0) {
                int targetIdx = findIndex(goal, value);

                int currX = i / 3, currY = i % 3;
                int targetX = targetIdx / 3, targetY = targetIdx % 3;
                this.h += Math.abs(currX - targetX) + Math.abs(currY - targetY);
            }

        }
        this.f = this.h + this.g;
    }

    private int findIndex(int[] goal, int value) {
        for(int i = 0; i < 9; i++) {
            if(goal[i] == value) return i;
        }
        return -1;
    }

    // So sánh để chọn f nhỏ nhất
    @Override
    public int compareTo(MyNode other) {
        return Integer.compare(this.f, other.f);
    }

    // So sánh trạng thái board
    @Override
    public boolean equals(Object other) {
        return Arrays.equals(this.board, ((MyNode)other).board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.board);
    }
}
