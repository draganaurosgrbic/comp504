"use strict";

var app;

function createApp(canvas) {
    const c = canvas.getContext("2d");

    const drawCircle = (x, y, radius, color) => {
        c.fillStyle = color;
        c.beginPath();
        c.arc(x, y, radius, 0, 2 * Math.PI, false);
        c.closePath();
        c.fill();
    };

    const drawTriangle = (x, y, size, color) => {
        c.fillStyle = color;
        c.beginPath();
        c.moveTo (x + size * Math.cos(0), y + size * Math.sin(0));

        for (let i = 1; i <= 3; ++i) {
            c.lineTo (x + size * Math.cos(i * 2 * Math.PI / 3), y + size * Math.sin(i * 2 * Math.PI / 3));
        }

        c.closePath();
        c.fill();
    };

    const drawSquare = (x, y, size, color) => {
        c.fillStyle = color;
        c.fillRect(x, y, size, size);
    }

    const drawRectangle =  (x, y, sizeX, sizeY, color) => {
        c.fillStyle = color;
        c.fillRect(x, y, sizeX, sizeY);
    }

    const drawShape = (items) => {
        for (const item of items) {
            const name = item.name.toLowerCase();
            switch (name) {
                case "circle":
                    app.drawCircle(item.loc.x, item.loc.y, item.radius, item.color)
                    break;
                case "triangle":
                    app.drawTriangle(item.loc.x, item.loc.y, item.size, item.color)
                    break;
                case "square":
                    app.drawSquare(item.loc.x, item.loc.y, item.size, item.color)
                    break;
                case "rectangle":
                    app.drawRectangle(item.loc.x, item.loc.y, item.sizeX, item.sizeY, item.color)
                    break;
                case "complex":
                    app.drawShape(item.children)
                    break;
            }
        }
    }
    
    const clear = () => {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    const setShapes = (shapes) => {
        app.shapes = shapes;
        app.drawShape(shapes);
    }

    return {
        drawCircle,
        drawTriangle,
        drawSquare,
        drawRectangle,
        drawShape,
        clear,
        dims: {height: canvas.height, width: canvas.width},
        shapes: [],
        setShapes
    }
}

window.onload = () => {
    const canvas = document.querySelector("canvas");
    canvas.height = window.innerHeight - 80;
    canvas.width = window.innerWidth - 20;
    app = createApp(canvas);

    $("#btn-circle").click(createCircle);
    $("#btn-triangle").click(createTriangle);
    $("#btn-square").click(createSquare);
    $("#btn-rectangle").click(createRectangle);
    $("#btn-complex").click(createComplex);
    $("#btn-clear").click(clear);

    canvasDims();
    fetchShapes();
};

window.onresize = () => {
    const canvas = document.querySelector("canvas");
    canvas.height = window.innerHeight - 80;
    canvas.width = window.innerWidth - 20;

    app.dims.height = canvas.height;
    app.dims.width = canvas.width;
    app.drawShape(app.shapes);
    canvasDims();
};

function canvasDims() {
    $.post("/canvas/dims", app.dims);
}

function fetchShapes(){
    $.get("/shapes", shapes => app.setShapes(shapes), "json");
}

function createCircle() {
    $.get("/shape/circle", shapes => app.setShapes(shapes), "json");
}

function createTriangle() {
    $.get("/shape/triangle", shapes => app.setShapes(shapes), "json");
}

function createSquare() {
    $.get("/shape/square", shapes => app.setShapes(shapes), "json");
}

function createRectangle() {
    $.get("/shape/rectangle", shapes => app.setShapes(shapes), "json");
}

function createComplex() {
    $.get("/shape/complex", shapes => app.setShapes(shapes), "json");
}

function clear() {
    $.get("/shapes/clear", () => {}, "json");
    app.clear();
}
