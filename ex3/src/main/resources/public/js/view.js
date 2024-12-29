'use strict';

//app to draw polymorphic shapes on canvas
let app;

//id of current interval
let intervalID = -1;

/**
 * Draw and clear line on a the canvas
 * @param canvas  The canvas used to draw a line
 * @returns {{drawLine: drawLine, clear: clear}}
 */
function createApp(canvas) {
    let c = canvas.getContext("2d");

    let drawLine = function(startX, startY, endX, endY) {
        c.beginPath();
        c.moveTo(startX, startY);
        c.lineTo(endX, endY);
        c.stroke();
    };

    let clear = function() {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        drawLine: drawLine,
        clear: clear,
        lineDrawn: false,
        lineVelocity: null,
        intervalId: null
    }
}

/**
 * Setup event handling for buttons
 */
window.onload = function() {
    app = createApp(document.querySelector("canvas"));

    $("#btn-line").click(createLine);
    $("#btn-move").click(setUpdateFreq);
    $("#btn-reset").click(reset);
};

/**
 * Create a line at a location on the canvas
 */
function createLine() {
    if (app.lineDrawn){
        return
    }
    $.get("/line", function (data) {
        console.log(data)
        app.drawLine(data.startX, data.startY, data.endX, data.endY)
        app.lineDrawn = true
        app.lineVelocity = data.velX
    }, "json");
}

/**
 * Determine how often line updates occur
 */
function setUpdateFreq() {
    if (!app.lineDrawn){
        alert('You have to first draw the line!');
        return
    }
    if (app.intervalId !== null){
        return
    }
    app.intervalId = setInterval(() => {
        if (app.intervalId === null){
            return;
        }
        updateLine()
        console.log('moving')
    }, 0.2 * 1000)
}

/**
 * Update a line on the canvas
 */
function updateLine() {
    $.get("/update", function (data) {
        app.clear()
        app.drawLine(data.startX, data.startY, data.endX, data.endY)
    }, "json");
}

/**
 * Reset canvas
 */
function reset() {
    $.get("/reset", function (data) {
        app.clear()
        clearInterval(app.intervalId)
        app.intervalId = null
        app.drawLine(data.startX, data.startY, data.endX, data.endY)
    }, "json");
}

/**
 * Clear the canvas
 */
function clear() {
    app.clear();
}