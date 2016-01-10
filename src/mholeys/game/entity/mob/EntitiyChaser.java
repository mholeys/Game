package mholeys.game.entity.mob;

import java.util.List;

import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.AnimatedSprite;
import mholeys.game.graphics.sprite.Sprites;

public class EntitiyChaser extends EnitityMob {

	private AnimatedSprite animSprite = null;

	private double speed = 0.5;
	private double xA = 0, yA = 0;
	private int range = 16 * 10; // 16 is tile size and ten is the number of tiles

	public EntitiyChaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprites.dummy;
		animSprite = Sprites.dummyDW;
	}

	public void update() {
		move();
		if (moving) {
			animSprite.update();
		} else {
			animSprite.reset();
		}
		getDirection(xA, yA);
		switch (dir) {
		case 0:
			yA -= speed;
			break;
		case 1:
			xA += speed;
			break;
		case 2:
			yA += speed;
			break;
		case 3:
			xA -= speed;
			break;
		}
	}

	private void move() {
		xA = 0;
		yA = 0;
		List<EnitityPlayer> players = level.getPlayers(this, range);
		if (players.size() > 0) {
			EnitityPlayer player = players.get(0);
			if (!player.getInvisible()) {
				if (x < (int) player.getX())
					xA += speed;
				if (x > (int) player.getX())
					xA -= speed;
				if (y < (int) player.getY())
					yA += speed;
				if (y > (int) player.getY())
					yA -= speed;
			}
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
