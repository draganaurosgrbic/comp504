var CLOCK = 0;
var GHOST_NUM = 4;

var flag = 0; // 0 means not start, 1 means start
var pause = 0; // 0 means not pause, 1 means pause

function initGame() {
    var canvas = document.getElementById('canvas-panel-title-pacman');
    canvas.setAttribute('width', '38');
    canvas.setAttribute('height', '32');
    if (canvas.getContext) { 
        ctx = canvas.getContext('2d');
    }
    
    var x = 15;
    var y = 16;
    
    ctx.fillStyle = "#fff200";
    ctx.beginPath();
    ctx.arc(x, y, 14, (0.35 - (3 * 0.05)) * Math.PI, (1.65 + (3 * 0.05)) * Math.PI, false);
    ctx.lineTo(x - 5, y);
    ctx.fill();
    ctx.closePath();
    
    x = 32;
    y = 16;
    
    ctx.fillStyle = "#dca5be";
    ctx.beginPath();
    ctx.arc(x, y, 4, 0, 2 * Math.PI, false);
    ctx.fill();
    ctx.closePath();

    // init board
    initBoard();
    getPacman();
    getGhosts();
    initClock();
    //
    // // init listeners
    brightnessListener();
    ghostNumListener();
    updateListener();
}

function initClock() {
    setInterval(() => {
        CLOCK++;
        update();
    }, 250);
}

function update() {
    if(flag===1) {
        updateDirection();
        getLives();
        getScore();
        getGameStatus();
    }
    drawBoard(CLOCK);
    drawPacman();
    renderGhosts();
    getGameLevel();
}

function brightnessListener() {
    document.getElementById('brightness').addEventListener('input', function(e) {
        const brightness = e.target.value;
        const mask = document.getElementById('mask');
        mask.style.opacity = brightness;
    }); 
}

function ghostNumListener() {
    document.getElementById('ghosts').addEventListener('input', function(e) {
        if(flag===1) {
            alert("You can't change the number of ghosts when the game is running!");
            return;
        }
        GHOST_NUM = e.target.value;

        // change the content of the div
        const ghostNum = document.getElementById('ghosts-num');
        ghostNum.textContent = GHOST_NUM;
        $.post("/setNumGhosts", {ghosts: GHOST_NUM}, function(data){

        });
    });
}

// keyboard listener for the pacman
function updateListener() {
    // 1 means right, 2 means down, 3 means left, 4 means up
    document.addEventListener('keydown', function(e) {
        if (e.keyCode === 37) {
            PACMAN_DIRECTION = 3;
        } else if (e.keyCode === 38) {
            PACMAN_DIRECTION = 4;
        } else if (e.keyCode === 39) {
            PACMAN_DIRECTION = 1;
        } else if (e.keyCode === 40) {
            PACMAN_DIRECTION = 2;
        } else if (e.keyCode === 80) { // p
            if(pause===0) {
                pauseGame();
                pause = 1;
            } else {
                resumeGame();
                pause = 0;
            }
        } else if (e.keyCode === 27) { // esc
            exitGame();
            const message = document.getElementById('message');
            message.textContent = "";
        }
    });
}

function updateDirection() {
    switch (PACMAN_DIRECTION) {
        case 1:
            $.post("/update", {direction: "right"});
            break;
        case 2:
            $.post("/update", {direction: "down"});
            break;
        case 3:
            $.post("/update", {direction: "left"});
            break;
        case 4:
            $.post("/update", {direction: "up"});
    }
}

function getLives() {
    $.get("/getLives", function(data){
        const lives = document.getElementById('lives');
        lives.textContent = JSON.parse(data).lives;
    });
}

function getScore() {
    $.get("/getScore", function(data){
        const score = document.getElementById('score');
        score.textContent = JSON.parse(data).score;
    });
}

function getGameLevel() {
    $.get("/getGameLevel", function(data){
        for(let i = 1; i <= 3; i++) {
            const gameLevel = document.getElementById('level'+i);
            gameLevel.style.color = "white";
        }
        const gameLevel = document.getElementById('level'+JSON.parse(data).gameLevel);
        gameLevel.style.color = "blue";
    });
}

function startGame() {
    console.log(flag);
    if(flag===1) {
        return;
    }
    $.post("/startGame", function(data){
        const ghostNum = document.getElementById('ghosts');
        ghostNum.disabled = true;
    });
    flag = 1;
}

function pauseGame() {
    $.post("/pauseGame", function(data){

    });
}

function exitGame() {
    $.post("/exitGame", function(data){
        flag = 0;
        PACMAN_DIRECTION = 1;
        CLOCK = 0;
        GHOST_NUM = 4;
        pause = 0;
        const systemInfo = document.getElementById('system-info');
        systemInfo.textContent = "";
        const ghostNum = document.getElementById('ghosts');
        ghostNum.disabled = false;
        ghostNum.value = 4;
        const ghostNumTxt = document.getElementById('ghosts-num');
        ghostNumTxt.textContent = GHOST_NUM;
    });
}

function setGameLevel(level) {
    if(flag===1) {
        alert("You can't change the level when the game is running!");
        return;
    }
    $.post("/setGameLevel", {level: level}, function(data){

    });
}

function resumeGame() {
    $.post("/resumeGame", function(data){

    });
}

function getGameStatus() {
    $.get("/getGameStatus", function(data){
        const gameStatus = document.getElementById('system-info');
        const message = document.getElementById('message');
        switch (JSON.parse(data).getGameStatus) {
            case 0: // playing
                gameStatus.textContent = "";
                message.textContent = "";
                break;
            case 1: // paused
                gameStatus.textContent = "Paused";
                break;
            case 2: // won
                gameStatus.textContent = "Won";
                message.textContent = "Press Esc to restart the game!";
                break;
            case 3: // lost
                gameStatus.textContent = "Game Over";
                message.textContent = "Press Esc to restart the game!";
                break;
        }
    });
}