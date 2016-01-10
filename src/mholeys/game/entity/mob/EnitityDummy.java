package mholeys.game.entity.mob;

import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.AnimatedSprite;
import mholeys.game.graphics.sprite.Sprites;

public class EnitityDummy extends EnitityMob {

	private AnimatedSprite animSprite = null;

	private int speed = 1;
	private int xA = 0, yA = 0;
	private int time = 0;

	public EnitityDummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprites.dummy;
		animSprite = Sprites.dummyDW;
	}

	public void update() {
		time++;
		if (time % (random.nextInt(15) + 25) == 0) {
			xA = random.nextInt(3) - 1;
			yA = random.nextInt(3) - 1;
			if (random.nextInt(3) != 0) {
				xA = 0;
				yA = 0;
			}
		}
		if (moving) {
			animSprite.update();
		} else {
			animSprite.reset();
		}
		getDirection(xA, yA);
		switch (dir) {
		case 0:
			yA = -speed;
			break;
		case 1:
			xA = speed;
			break;
		case 2:
			yA = speed;
			break;
		case 3:
			xA = -speed;
			break;
		}
		if (xA != 0 || yA != 0) {
			move(xA, yA, false);
			moving = true;
		} else {
			moving = false;

		}

	}

	public void render(Screen screen) {
		switch (dir) {
		case 0:
			animSprite = Sprites.dummyUP;
			break;
		case 1:
			animSprite = Sprites.dummyRI;
			break;
		case 2:
			animSprite = Sprites.dummyDW;
			break;
		case 3:
			animSprite = Sprites.dummyLE;
			break;
		case 4:
			animSprite = Sprites.dummyUP;
			break;
		}
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, this);
	}

}
