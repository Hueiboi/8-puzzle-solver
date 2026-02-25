package com.eightpuzzle.demo.dto;

import java.util.List;

public record PuzzleRes(List<int[]> steps, int totalSteps) {}