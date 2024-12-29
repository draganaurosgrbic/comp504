'use strict';

let app;
function createApp(canvas) {
    canvas.width = window.innerWidth - 20;
    canvas.height = window.innerHeight - 140;

    const c = canvas.getContext('2d');

    const drawObjects = objects => {
        c.clearRect(0,0, canvas.width, canvas.height);

        for (const object of objects.sort((a, b) => a.type.localeCompare(b.type))) {
            if (object.type in drawing) {
                drawing[object.type](c, object);
            }
        }
    }

    return {
        drawObjects,
        dims: {height: canvas.height, width: canvas.width}
    }
}

const initSelectStrategy = domId => {
    for (const strategy in UPDATE_STRATEGY_MAP) {
        $(domId).append(`<option value=${strategy}>${UPDATE_STRATEGY_MAP[strategy]}</option>`)
    }
}

const showSelectCollisionStrategy = () => {
    $("#select-collision-strategy").parent().show();
}

const hideSelectCollisionStrategy = () => {
    $("#select-collision-strategy").parent().hide();
}

const registerSelectCollisionStrategyToggles = () => {
    $("#select-object-type").change(() => {
       if ($("#select-object-type").val() === 'ball') {
           showSelectCollisionStrategy();
       } else {
           hideSelectCollisionStrategy();
       }
    });
}

window.onload = () => {
    app = createApp(document.querySelector('canvas'));

    $('#btn-load').click(loadObject);
    $('#btn-remove').click(removeObjects)
    $('#btn-switch').click(switchStrategies)
    $('#btn-clear').click(clearPaintWorld);

    canvasDimensions();
    setInterval(updatePaintWorld, 100);

    showSelectCollisionStrategy();
    registerSelectCollisionStrategyToggles();
    initSelectStrategy('#select-update-strategy');
    initSelectStrategy('#select-remove-strategy');
    initSelectStrategy('#select-switch-strategy-from');
    initSelectStrategy('#select-switch-strategy-to');
};

function canvasDimensions() {
    $.post('/canvas/dims', app.dims);
}

function loadObject() {
    const type = $('#select-object-type').val();

    const body = {
        updateStrategy: $('#select-update-strategy').val(),
        collisionStrategy: $('#select-collision-strategy').val(),
        strategySwitchable: $("#checkbox-switch-strategy").prop("checked")
    }

    $.post(`/load/${type}`, JSON.stringify(body));
}

function removeObjects() {
    const id = $('#select-remove-strategy').val()
    $.get(`/remove/${id}`);
}

function switchStrategies() {
    const fromStrategy = $('#select-switch-strategy-from').val();
    const toStrategy = $('#select-switch-strategy-to').val();

    if (fromStrategy === toStrategy) {
        alert('You are switching to the same strategy! Choose a different destination strategy.');
        return;
    }

    $.post('/switch', JSON.stringify({fromStrategy, toStrategy}));
}

function updatePaintWorld() {
    $.get('/update', objects => app.drawObjects(objects), 'json');
}

function clearPaintWorld() {
    $.get('/clear');
}
