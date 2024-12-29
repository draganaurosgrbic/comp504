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

    clearInterval(intervalID);
    intervalID = setInterval(updateLine, 200);

    $("#btn-sline").click(function () { createMovingLine("short"); });
    $("#btn-lline").click(function () { createMovingLine("long"); });
    $("#btn-switch").click(switchStrategy);
    $("#btn-reset").click(resetLine);
};

/**
 * Create a line at a location on the canvas
 */
function createMovingLine(kind) {
    $.get("/line/" + kind, function (data) {
       drawLineStore(data);
        if (intervalID < 0) {
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
        drawLineStore(data);
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
 * Draw lines in store
 */
function drawLineStore(data) {
    if (data.sml) {
        app.drawLine(data.sml.startLine.x, data.sml.startLine.y, data.sml.endLine.x, data.sml.endLine.y);
    }
    if (data.lml) {
        app.drawLine(data.lml.startLine.x, data.lml.startLine.y, data.lml.endLine.x, data.lml.endLine.y);
    }
}

/**
 * Clear the canvas
 */
function clear() {
    app.clear();
}
