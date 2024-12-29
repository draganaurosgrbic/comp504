'use strict';

let app;
function createApp(canvas) {
    const c = canvas.getContext("2d");
    const drawCircle = (x, y, radius, color) => {
        c.fillStyle = color;
        c.beginPath();
        c.arc(x, y, radius, 0, 2 * Math.PI, false);

        c.closePath();
        c.fill();
    };

    const clear = () => {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        drawCircle,
        clear,
        dims: {height: canvas.height, width: canvas.width}
    }
}

window.onload = () => {
    app = createApp(document.querySelector("canvas"));

    $("#btn-load").click(loadBall);
    $("#btn-remove").click(removeBalls)
    $("#btn-clear").click(clear);

    canvasDims();
    setInterval(updateBallWorld, 100);
};
function loadBall() {
    const strategy = $("#select-strategy").val();

    $.post("/load", { strategy: strategy}, ball => {
        app.drawCircle(ball.loc.x, ball.loc.y, ball.radius, ball.color);
    }, "json");
}

function removeBalls() {
    const strategy = $("#select-strategy").val();

    $.get(`/remove?strategy=${strategy}`, balls => {
        app.clear();
        for (const ball of balls) {
            app.drawCircle(ball.loc.x, ball.loc.y, ball.radius, ball.color);
        }
    }, "json");
}

function clear() {
    $.get("/clear", () => app.clear());
}

function canvasDims() {
    $.post("/canvas/dims", {height: app.dims.height, width: app.dims.width});
}

function updateBallWorld() {
    $.get("/update", balls => {
        app.clear();
        for (const ball of balls) {
            app.drawCircle(ball.loc.x, ball.loc.y, ball.radius, ball.color);
        }
    }, "json");
}
