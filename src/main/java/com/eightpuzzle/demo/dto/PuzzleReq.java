package com.eightpuzzle.demo.dto;

public record PuzzleReq(String puzzleType, int[][] initial, int[][] goal) {}
