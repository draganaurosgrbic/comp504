var red = new Image();
red.src = "./js/assets/red.png";
var yellow = new Image();
yellow.src = "./js/assets/yellow.png";
var blue = new Image();
blue.src = "./js/assets/blue.png";
var afraid = new Image();
afraid.src = "./js/assets/afraid.png";
var green = new Image();
green.src = "./js/assets/green.png";
var eyes = new Image();
eyes.src = "./js/assets/eyes.jpg";

var ghosts;

function getGhosts() {
	$.get("/getGhosts", function(data){
		ghosts = JSON.parse(data);
		GHOST_NUM = ghosts.length;
	});
}

function renderGhosts() {
	getGhosts();
	for(var i = 0; i < ghosts.length; i++) {
		drawGhost(ghosts[i])
	}
}
function drawGhost(ghost) { 

	var ctx = document.getElementById('canvas-board').getContext('2d');

    if(CLOCK%2===1 || ghost.color !== "afraid") {
		// use canvas.drawImage to draw the ghosts
		eval("ctx.drawImage("+ghost.color+", (ghost.y) * widthRaio, (ghost.x) * heightRaio,  widthRaio, heightRaio);");
	}
	
	ctx.closePath();
}