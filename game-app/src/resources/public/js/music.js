var BG_MUSIC = new buzz.sound([
    "./music/ready.mp3" 
]);

var GROUP_SOUND = new buzz.group( [ BG_MUSIC ] );


function loadMusic() {
    GROUP_SOUND.load();
}

function bgSound() {
    BG_MUSIC.loop();
    BG_MUSIC.play();
}