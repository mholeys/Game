package mholeys.game.entity.projectile;

import java.util.Random;

import mholeys.game.entity.Entity;
import mholeys.game.graphics.sprite.Sprite;

public abstract class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nX, nY;
	protected double speed, range, damage;

	protected final Random r = new Random();

	public Projectile(int x, int y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}

	public void update() {
		move();
	}

	protected void move() {
	}

	public Sprite getSprite() {
		return sprite;
	}
}
