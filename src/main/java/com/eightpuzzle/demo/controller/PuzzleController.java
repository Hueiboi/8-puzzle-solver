package com.eightpuzzle.demo.controller;

import com.eightpuzzle.demo.dto.PuzzleReq;
import com.eightpuzzle.demo.dto.PuzzleRes;
import com.eightpuzzle.demo.model.MyNode;
import com.eightpuzzle.demo.model.PuzzleSolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/puzzle")

public class PuzzleController {
    @PostMapping("/solve")
    public ResponseEntity<?> solve(@RequestBody PuzzleReq req) {
        if(req.initial() == null || req.goal() == null) {
            return ResponseEntity.badRequest().body("Dữ liệu không được để trống");
        }

        if (req.initial().length != 9 || req.goal().length != 9) {
            return ResponseEntity.badRequest().body("Mảng phải có đúng 9 phần tử!");
        }

        if (!isValidPuzzle(req.initial())) {
            return ResponseEntity.badRequest().body("Dữ liệu phải từ 0-8");
        }

        PuzzleSolver solver = new PuzzleSolver();

        List<MyNode> path = solver.solve(req.initial(), req.goal());

        if(path == null) return null;

        List<int[]> steps = path.stream().map(node -> node.board).toList();
        return ResponseEntity.ok(new PuzzleRes(steps, steps.size() - 1));
    }

    private boolean isValidPuzzle(int[] board) {
        if (board.length != 9) return false;
        boolean[] seen = new boolean[9]; 
        for (int val : board) {
            if (val < 0 || val > 8 || seen[val]) {
                return false;
            }
            seen[val] = true;
        }
        return true;
    }
}
