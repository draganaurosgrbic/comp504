const UPDATE_STRATEGY_MAP = {
    horizontal: 'horizontal line',
    vertical: 'vertical line',
    diagonal: 'diagonal line',
    rotation: 'rotating circle',
    stairs: 'stairs',
    waterfall: 'waterfall',
    pulse: 'pulse',
    zig_zag: 'zig zag',
    inverted_squares: 'inverted squares',
    inverted_trapezoids: 'inverted trapezoids',
    square: 'square movement',
    rhombus: 'rhombus movement'
}


const drawBall = (c, ball) => {
    c.fillStyle = ball.color;
    c.globalAlpha = ball.opacity;

    c.beginPath();
    c.arc(ball.location.x, ball.location.y, ball.size, 0, 2 * Math.PI, false);

    c.closePath();
    c.fill();

    if (ball.hasCross) {
        c.strokeStyle = 'black';
        c.strokeWidth = 2;

        c.beginPath();

        c.moveTo(ball.location.x - ball.size, ball.location.y);
        c.lineTo(ball.location.x + ball.size, ball.location.y);
        c.moveTo(ball.location.x, ball.location.y - ball.size);
        c.lineTo(ball.location.x, ball.location.y + ball.size);

        c.stroke();
    }
}

const drawFish = (c, fish) => {
    const image = fish.rotation || Math.abs(fish.angle) === 90 || fish.velocity.x > 0
        ? document.getElementById('fish-right')
        : document.getElementById('fish-left');

    c.save();

    c.translate(fish.location.x, fish.location.y);
    c.rotate(fish.rotation ? fish.angle + 90 : fish.angle*Math.PI/180);
    c.drawImage(image, -fish.size, -fish.size, fish.size * 2, fish.size * 2);

    c.restore();
}

const drawing = {
    'ball': drawBall,
    'fish': drawFish
}