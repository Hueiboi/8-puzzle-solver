package com.eightpuzzle.demo.controller;

import com.eightpuzzle.demo.dto.PuzzleReq;
import com.eightpuzzle.demo.dto.PuzzleRes;
import com.eightpuzzle.demo.model.EightPuzzle;
import com.eightpuzzle.demo.model.FifteenPuzzle;
import com.eightpuzzle.demo.model.Puzzle;
import com.eightpuzzle.demo.model.PuzzleSolver;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puzzle")
@CrossOrigin(origins = "*")
public class PuzzleController {

    @PostMapping("/solve")
    public PuzzleRes solve(@RequestBody PuzzleReq req) {
        Puzzle puzzle;
        
        if ("15".equals(req.puzzleType())) {
            puzzle = new FifteenPuzzle(req.initial());
        } else {
            // Mặc định là 8 puzzle
            puzzle = new EightPuzzle(req.initial());
        }

        PuzzleSolver solver = new PuzzleSolver();
        List<int[][]> solution = solver.solve(puzzle);

        if (solution != null) {
            return new PuzzleRes(solution, solution.size() - 1);
        } else {
            return new PuzzleRes(null, 0);
        }
    }
}
