"use strict";

let app;

const createApp = (canvas) => {
    const c = canvas.getContext("2d");

    const drawSimple = shape => {
        const initialX = shape.loc.x;
        const initialY = shape.loc.y;
        
        for (let i = 0; i < shape.matrix ** 2; ++i) {
            const column = i % shape.matrix;
            const row = Math.floor(i / shape.matrix);

            if (shape.frame && (column > 0 && column < shape.matrix - 1) && (row > 0 && row < shape.matrix - 1)) {
                continue;
            }

            shape.loc.x = initialX + column * 2 * shape.size;
            shape.loc.y = initialY + row * 2 * shape.size;

            drawing[shape.name](c, shape);
        }
    }

    const drawComplex = shape => {
        for (const item of shape.children) {
            drawing[item.name](c, item)
        }
    }

    const drawShape = shape => {
        if ('children' in shape) {
            drawComplex(shape);
        } else {
            drawSimple(shape);
        }
    }

    const drawShapes = (shapes) => {
        if (shapes) {
            app.shapes = shapes;
        }
        for (const shape of app.shapes) {
            app.drawShape(shape);
        }
    }

    const clear = () => {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        dims: {height: canvas.height, width: canvas.width},
        shapes: [],
        drawShape,
        drawShapes,
        clear
    }
}

const resetCanvasDims = () => {
    const canvas = document.querySelector("canvas");
    canvas.height = window.innerHeight - 180;
    canvas.width = window.innerWidth - 20;
    return canvas;
}

const showMatrixInputs = () => {
    $("#input-size").parent().show();
    $("#checkbox-color").parent().show();
}

const hideMatrixInputs = () => {
    $("#input-size").parent().hide();
    $("#checkbox-color").parent().hide();
}

window.onload = () => {
    const canvas = resetCanvasDims();
    app = createApp(canvas);

    sendCanvasDims();
    fetchShapes();

    $("#btn-draw").click(createShape)
    $("#btn-clear").click(clearShapes);
    hideMatrixInputs();

    const selectType = $("#select-type");
    selectType.on("change", () => {
       const value = selectType.val();
       if (value !== "single") {
           showMatrixInputs();
       } else {
           hideMatrixInputs();
       }
    });

    $("#input-size").on("keypress", e => e.preventDefault());
};

window.onresize = () => {
    const canvas = resetCanvasDims();
    app.dims.height = canvas.height;
    app.dims.width = canvas.width;

    sendCanvasDims();
    app.drawShapes();
};

function sendCanvasDims() {
    $.post("/canvas/dims", app.dims);
}

function fetchShapes(){
    $.get("/shapes", shapes => app.drawShapes(shapes), "json");
}

function createShape(){
    const selectedShape = $("#select-shapes").val();
    const selectedType = $("#select-type").val();
    const selectedSize = $("#input-size").val();
    const randomColors = !$("#checkbox-color").prop("checked");

    let spec = selectedShape;

    if (selectedType !== "single") {
        spec += `?matrix=${selectedSize}`;
        if (selectedType === "frame") {
            spec += "&frame=true";
        }
        if (randomColors) {
            spec += "&random_colors=true";
        }
    }

    $.get(`/shape/${spec}`, shapes => app.drawShapes(shapes), "json");
}

function clearShapes() {
    $.get("/shapes/clear", () => app.clear(), "json");
}
