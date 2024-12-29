'use strict';


let app;//app to draw polymorphic shapes on canvas
let intervalID = -1;//id of current interval

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
        clear: clear
    }
}

/**
 * Setup event handling for buttons
 */
window.onload = function() {
    app = createApp(document.querySelector("canvas"));

    $("#btn-line").click(createMovingLine);
    $("#btn-switch").click(switchStrategy);
    $("#btn-reset").click(resetLine);
};

/**
 * Create a line at a location on the canvas
 */
function createMovingLine() {
    $.get("/line", function (data) {
        if (intervalID > 0) {
            alert("You can only draw the line once, hit reset before redrawing");
        }
        else {
            app.drawLine(data.startLine.x, data.startLine.y, data.endLine.x, data.endLine.y);
            intervalID = setInterval(updateLine, 200);
        }
    }, "json");

}

/**
 * Move a line on the canvas
 */
function switchStrategy() {
    $.get("/switch", function (data) {
    }, "json");
}

/**
 * Update a line on the canvas
 */
function updateLine() {
    $.get("/update", function (data) {
        clear();
        app.drawLine(data.startLine.x, data.startLine.y, data.endLine.x, data.endLine.y);
    }, "json");

}

/**
 * Reset canvas
 */
function resetLine() {
    $.get("/reset", function (data) {
        clear();
        clearInterval(intervalID);
        intervalID = -1;
    }, "json");


}
/**
 * Clear the canvas
 */
function clear() {
    app.clear();
}