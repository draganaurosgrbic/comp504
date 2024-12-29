var PACMAN_SIZE = 10;
var PACMAN_DIRECTION = 1;
var PACMAN_MOUNTH_STATE = 0;

var pacman;


function getPacman() {
	$.get("/getPacman", function(data){
		pacman = JSON.parse(data);
	});
	$.get("/getPacmanDirection", function(data){
		data = JSON.parse(data);
		// data = {x: -1 or 0 or 1, y: -1 or 0 or 1}
		if (data.x === 0 && data.y === 1) {
			PACMAN_DIRECTION = 1;
		} else if (data.x === 1 && data.y === 0) {
			PACMAN_DIRECTION = 2;
		} else if (data.x === 0 && data.y === -1) {
			PACMAN_DIRECTION = 3;
		} else if (data.x === -1 && data.y === 0) {
			PACMAN_DIRECTION = 4;
		}
	});
}

function drawPacman() {

	var ctx = document.getElementById('canvas-board').getContext('2d');

    getPacman();
	
	ctx.fillStyle = "#fff200";
	ctx.beginPath();

	// adjust the mouth according to the clock
	PACMAN_MOUNTH_STATE = (PACMAN_MOUNTH_STATE + 5.5) % 11;

	
	var startAngle = 0;
	var endAngle = 2 * Math.PI;
	var lineToX = (pacman.y+1) * widthRaio-8.5;
	var lineToY = (pacman.x+1) * heightRaio-8.5;
	if (PACMAN_DIRECTION === 1) { 
		startAngle = (0.35 - (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		endAngle = (1.65 + (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		lineToX -= 5;
	} else if (PACMAN_DIRECTION === 2) { 
		startAngle = (0.85 - (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		endAngle = (0.15 + (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		lineToY -= 5;
	} else if (PACMAN_DIRECTION === 3) { 
		startAngle = (1.35 - (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		endAngle = (0.65 + (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		lineToX += 5;
	} else if (PACMAN_DIRECTION === 4) { 
		startAngle = (1.85 - (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		endAngle = (1.15 + (PACMAN_MOUNTH_STATE * 0.05)) * Math.PI;
		lineToY += 5;
	}
	ctx.arc((pacman.y+1) * widthRaio-8.5, (pacman.x+1) * heightRaio-8.5, PACMAN_SIZE, startAngle, endAngle, false);
	ctx.lineTo(lineToX, lineToY);
	ctx.fill();
	ctx.closePath();
}