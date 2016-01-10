package mholeys.game.entity.particle;

import mholeys.game.entity.Entity;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.graphics.sprite.Sprites;

public class Particle extends Entity {

	protected double xA, yA, zA;
	protected double xX, yY, zZ;

	private int life;
	private int time = 0;
	private Sprite sprite;

	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xX = x;
		this.yY = y;
		this.life = life + (random.nextInt(life + 10) + 10);
		sprite = Sprites.particleLaser;

		this.xA = random.nextGaussian();
		this.yA = random.nextGaussian();
		this.zZ = random.nextFloat() + 2.0;
	}

	public void update() {
		time++;
		if (time > 6060) {
			life -= time;
			time = 0;
		}
		if (time > life)
			remove();
		zA -= 0.1;
		if (zZ < 0) {
			zZ = 0;
			zA *= -0.5;
			xA *= 0.4;
			yA *= 0.2;
		}
		move(xX + xA, (yY + yA) + (zZ + zA));

	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			xA *= -0.5;
			yA *= -0.5;
			zA *= -0.5;
		}
		xX += xA;
		yY += yA;
		zZ += zA;
	}

	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int corner = 0; corner < 4; corner++) {
			double xT = (x + (corner % 2)) / 16;
			double yT = (y + (corner / 2)) / 16;

			/*int iX = (int) Math.ceil(xT);
			int iY = (int) Math.ceil(yT);
			if (corner % 2 == 0)
				iX = (int) Math.floor(xT);
			if (corner / 2 == 0)
				iY = (int) Math.floor(yT);*/

			int iX = (int) xT;
			int iY = (int) yT;
			if (level.getTile(iX, iY).isSolid())
				solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xX, (int) yY - (int) zZ, sprite, true);
	}

}

