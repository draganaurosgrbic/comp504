var BOARD_CANVAS_CONTEXT = null;
var ghosts = new Array();
var height = 31;
var width = 28;
var widthRaio;
var heightRaio;

var fruit = new Image();
fruit.src = "./js/assets/fruit.png";

var map;

function initBoard() { 
	var canvas = document.getElementById('canvas-board');
	canvas.setAttribute('width', '476');
	canvas.setAttribute('height', '527');
	
	widthRaio = canvas.height/height;
	heightRaio = canvas.width/width;

	if (canvas.getContext) { 
		BOARD_CANVAS_CONTEXT = canvas.getContext('2d');
	}
}

function getBoardCanevasContext() { 
	return BOARD_CANVAS_CONTEXT;
}

function drawBoard(clock){
	var context = getBoardCanevasContext();
	var canvas = document.getElementById('canvas-board');

	context.fillStyle='#000000';
	context.fillRect(0,0,canvas.width, canvas.height);

	$.get("/getBoard", function(data){
		// parse the string data to json array
		map = JSON.parse(data);
	});

	//draw the level
	for(var y = 0; y < height; y++){
		for(var x= 0; x < width; x++){
			// 1 means wall, 2 means small dot, 3 means passage, 4 means fruit and 5 means blinding dots
			if(map[y][x]===1){
				context.fillStyle='#0000FF';
				context.fillRect(x*(heightRaio),y*(widthRaio),(heightRaio),(widthRaio));
			}
			else if(map[y][x]===2) {
				context.fillStyle='#FFFF00';
				context.fillRect(x*(heightRaio)+2*((heightRaio)/5),y*(heightRaio)+2*((heightRaio)/5),(heightRaio)/5,(heightRaio)/5);
			}
			else if(map[y][x]===3) {

			}
			else if(map[y][x]===4) {
				context.drawImage(fruit, x*(heightRaio),y*(widthRaio),(heightRaio),(widthRaio));
			}
			else if(map[y][x]===5 && clock%2===0) {
				context.fillStyle='#FFFF00';
				context.fillRect(x*(heightRaio)+((heightRaio)/4),y*(heightRaio)+((heightRaio)/4),(heightRaio)/2,(heightRaio)/2);
			}
		}
	}

}