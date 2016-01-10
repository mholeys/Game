package mholeys.game.entity.mob;

import mholeys.game.entity.Entity;
import mholeys.game.entity.projectile.Projectile;
import mholeys.game.entity.projectile.ProjectileLaser;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprite;

public abstract class EnitityMob extends Entity {

	protected int dir = 0;
	protected boolean moving = false;
	
	public void move(double xA, double yA, boolean cheat) {
		getDirection(xA, yA);
		if (xA != 0 && yA != 0) {
			move(xA, 0, cheat);
			move(0, yA, cheat);
			return;
		}

		if (!cheat) {
			while (xA != 0.0) {
				if (Math.abs(xA) > 1.0) {
					if (!collision(sign(xA), 0.0)) {
						this.x += sign(xA);
					}
					xA -= sign(xA);
				} else {
					if (!collision(xA, 0.0)) {
						this.x += xA;
					}
					xA = 0.0;
				}
			}
			while (yA != 0.0) {
				if (Math.abs(yA) > 1.0) {
					if (!collision(0.0, sign(yA))) {
						this.y += sign(yA);
					}
					yA -= sign(yA);
				} else {
					if (!collision(0.0, yA)) {
						this.y += yA;
					}
					yA = 0.0;
				}
			}
		} else {
			x += xA;
			y += yA;
		}
	}

	private int sign(double value) {
		if (value < 0.0)
			return -1;
		return 1;
	}

	public abstract void update();

	protected void shoot(int x, int y, double dir) {
		Projectile pro = new ProjectileLaser(x, y, dir);
		level.add(pro);
	}

	public abstract void render(Screen screen);

	public void getDirection(double xA, double yA) {
		if (yA < 0.0)
			dir = 0; // up
		if (xA > 0.0)
			dir = 1; // right
		if (yA > 0.0)
			dir = 2; // down
		if (xA < 0.0)
			dir = 3; // left
		if (xA == 0.0 && yA == 0.0)
			dir = 4; // reset
	}

	private boolean collision(double xA, double yA) {
		boolean solid = false;
		for (int corner = 0; corner < 4; corner++) {
			double xT = ((x + xA) + 15*(corner % 2)) / 16; // the 15 in brackets is the offset of where the bounding collision box is can use the same laws as graph
			double yT = ((y + yA) + 15*(corner / 2)) / 16;
			
			/*int iX = (int) Math.ceil(xT);
			int iY = (int) Math.ceil(yT);
			if (corner % 2 == 0)
				iX = (int) Math.floor(xT);
			if (corner / 2 == 0)
				iY = (int) Math.floor(yT);*/

			int iX = (int)xT;
			int iY = (int)yT;
			
			if (level.getTile(iX, iY).isSolid())
				solid = true;
		}
		return solid;
	}

	public Sprite getSprite() {
		return sprite;
	}
}
