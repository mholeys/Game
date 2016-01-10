package mholeys.game.entity;

import java.util.Random;

import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.level.Level;

public abstract class Entity {

	protected double x, y;
	protected boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;
	protected int maxHealth = 100;
	protected int health = maxHealth;
	protected int depth = 0;

	public void update() {
		checkHealth();
	}

	public int health() {
		return health / maxHealth;
	}
	
	public void damage(int dam) {
		if (health > dam) {
			health -= dam;
		} else {
			health = 0;
		}
	}
	
	public void checkHealth() {
		if (health > 0) {
			remove();
		}
	}
	
	public void render(Screen screen) {

	}

	public Sprite getSprite() {
		return sprite;

	}

	public void remove() {
		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void init(Level level) {
		this.level = level;
	}

}
