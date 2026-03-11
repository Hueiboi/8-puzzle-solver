    let timer;
    let isSolving = false;
    let solveStartTime = 0; // Lưu thời gian bắt đầu gọi API

    // Hàm đổi loại Puzzle (3x3 hoặc 4x4)
    function changeType() {
        const dim = getDimension();
        document.documentElement.style.setProperty('--grid-cols', dim);
        
        const goal = [];
        for(let i=1; i < dim*dim; i++) goal.push(i);
        goal.push(0);
        
        document.getElementById("inputInitial").value = goal.join(", ");
        document.getElementById("inputGoal").value = goal.join(", ");
        
        resetPuzzle();
    }

    function getDimension() {
        return document.getElementById("puzzleType").value === "15" ? 4 : 3;
    }

    // Logic xáo trộn bảng
    function shuffleBoard() {
        if (isSolving) return;
        let dim = getDimension();
        let board = getBoard2D("inputGoal");
        let steps = dim === 4 ? 30 : 20; 
        
        for (let i = 0; i < steps; i++) {
            let emptyPos = findZero(board);
            let neighbors = getValidNeighbors(emptyPos, dim);
            let randomNeighbor = neighbors[Math.floor(Math.random() * neighbors.length)];
            board[emptyPos.r][emptyPos.c] = board[randomNeighbor.r][randomNeighbor.c];
            board[randomNeighbor.r][randomNeighbor.c] = 0;
        }
        
        document.getElementById("inputInitial").value = board.flat().join(", ");
        render(board);
        document.getElementById('status').innerText = "Đã xáo trộn " + steps + " bước";
    }

    function findZero(board) {
        for (let r = 0; r < board.length; r++) {
            for (let c = 0; c < board[r].length; c++) {
                if (board[r][c] === 0) return {r, c};
            }
        }
    }

    function getValidNeighbors(pos, dim) {
        const moves = [{r: -1, c: 0}, {r: 1, c: 0}, {r: 0, c: -1}, {r: 0, c: 1}];
        return moves.map(m => ({r: pos.r + m.r, c: pos.c + m.c}))
                    .filter(m => m.r >= 0 && m.r < dim && m.c >= 0 && m.c < dim);
    }

    function getBoard2D(inputId) {
        const el = document.getElementById(inputId);
        const flatArr = el.value.split(',').map(num => parseInt(num.trim()));
        const dim = getDimension();
        const board2D = [];
        for (let i = 0; i < dim; i++) board2D.push(flatArr.slice(i * dim, (i + 1) * dim));
        return board2D;
    }

    function render(board) {
        const container = document.getElementById('board');
        container.innerHTML = '';
        const flatBoard = board.flat();
        flatBoard.forEach(val => {
            const div = document.createElement('div');
            div.className = 'cell' + (val === 0 ? ' empty' : '');
            div.innerText = val === 0 ? '' : val;
            container.appendChild(div);
        });
    }

    // Khóa các nút khi đang giải
    function lockBoard() {
        isSolving = true;
        document.querySelectorAll('button').forEach(btn => btn.disabled = true);
        document.getElementById('solveBtn').innerText = "Đang giải...";
    }

    // Mở khóa các nút
    function unlockBoard() {
        isSolving = false;
        document.querySelectorAll('button').forEach(btn => btn.disabled = false);
        document.getElementById('solveBtn').innerText = "Bắt đầu giải";
    }

    // GỌI API GIẢI PUZZLE
    async function solvePuzzle() {
        if (isSolving) return;

        const initialArr = getBoard2D('inputInitial');
        const goalArr = getBoard2D('inputGoal');
        const puzzleType = document.getElementById("puzzleType").value;
        
        lockBoard();
        
        // --- ĐẾM THỜI GIAN (Bắt đầu) ---
        // Sử dụng performance.now() để lấy thời gian cực kỳ chính xác (tính bằng mili giây)
        const startTime = performance.now();

        try {
            const response = await fetch('/api/puzzle/solve', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ puzzleType, initial: initialArr, goal: goalArr })
            });

            const data = await response.json();
            
            // --- ĐẾM THỜI GIAN (Kết thúc) ---
            const endTime = performance.now();
            const executionTime = ((endTime - startTime) / 1000).toFixed(3); // Thời gian phản hồi từ server

            if (data && data.steps) {
                // Truyền thời gian và số bước vào hàm animate để hiển thị modal lúc cuối
                animate(data.steps, executionTime);
            } else {
                alert("Không tìm thấy lời giải!");
                unlockBoard();
            }
        } catch (error) {
            console.error(error);
            alert("Lỗi kết nối server!");
            unlockBoard();
        }
    }

    // Hàm diễn hoạt (Animate) các bước đi
    function animate(steps, executionTime) {
        let i = 0;
        if(timer) clearInterval(timer);

        timer = setInterval(() => {
            if (i < steps.length) {
                render(steps[i]);
                document.getElementById('status').innerText = `Bước: ${i} / ${steps.length - 1}`;
                i++;
            } else {
                clearInterval(timer);
                document.getElementById('status').innerText = "Hoàn thành!";
                unlockBoard();
                
                // HIỂN THỊ MODAL KẾT QUẢ
                showResult(steps.length - 1, executionTime);
            }
        }, 150); // Tốc độ chạy 150ms mỗi bước
    }

    // Hiển thị Modal
    function showResult(steps, time) {
        document.getElementById('resSteps').innerText = steps;
        document.getElementById('resTime').innerText = time;
        document.getElementById('modalOverlay').style.display = 'flex';
        document.body.classList.add('modal-active'); // Kích hoạt làm mờ nền
    }

    // Đóng Modal
    function closeModal() {
        document.getElementById('modalOverlay').style.display = 'none';
        document.body.classList.remove('modal-active'); // Hết làm mờ nền
    }

    function resetPuzzle() {
        if(timer) clearInterval(timer);
        render(getBoard2D("inputInitial"));
        document.getElementById('status').innerText = "Sẵn sàng";
        unlockBoard();
    }

    window.onload = () => changeType();