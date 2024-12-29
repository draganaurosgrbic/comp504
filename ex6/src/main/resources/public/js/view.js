'use strict';

//app to draw polymorphic shapes on canvas
var app;

function createApp(canvas) {
    let c = canvas.getContext("2d");

    let drawBall = function(x, y, radius, color) {
        c.fillStyle = color;
        c.beginPath();
        c.arc(x, y, radius, 0, 2 * Math.PI, false);
        c.closePath();
        c.fill();
    };

    let drawWall = function(startX, startY, length) {
        c.beginPath();
        c.moveTo(startX, startY);
        c.lineTo(startX, startY + length);
        c.stroke();
    };

    let clear = function() {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        drawBall,
        drawWall,
        clear,
        dims: {height: canvas.height, width: canvas.width}
    }
}


/**
 * Entry point into app
 */
window.onload = function() {
    app = createApp(document.querySelector("canvas"));

    canvasDims();
    setInterval(updatePaintObjWorld, 100);

    $("#btn-load-ball").click(loadBall);
    $("#btn-load-wall").click(loadWall);
    $("#btn-clear").click(clear);
};

/**
 * load a ball at a location on the canvas
 */
function loadBall() {

    $.get("/load/ball", function (data) {
        app.drawBall(data.loc.x, data.loc.y, data.radius, data.color);
    }, "json");
}

/**
 * load a wall at a location on the canvas
 */
function loadWall() {

    $.get("/load/wall", function (data) {
        app.drawWall(data.loc.x, data.loc.y, data.len);
    }, "json");
}

/**
 *   update the ball and inner walls
 */
function updatePaintObjWorld() {
    $.get("/update", function(data) {
        app.clear();

        data.forEach(function(po) {
            if (po.type === "ball") {
                console.log("x location is ", po.loc.x, ", x velocity is ", po.vel.x);
                app.drawBall(po.loc.x, po.loc.y, po.radius, po.color);
            }
        });
        data.forEach(function(po) {
            if (po.type === "wall")
                app.drawWall(po.loc.x, po.loc.y, po.len);
        });
    }, "json");
}


/**
 * Pass along the canvas dimensions
 */
function canvasDims() {
    $.post("/canvas/dims", {height: app.dims.height, width: app.dims.width});
}

/**
 * Clear the canvas
 */
function clear() {
    $.get("/clear");
    app.clear();
}
