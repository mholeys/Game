package mholeys.game.entity.projectile;

import mholeys.game.entity.spawner.SpawnerParticle;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprites;

public class ProjectileLaser extends Projectile {

	public static final int RATE = 5;

	private double[][] corners = new double[4][2];
	
	public ProjectileLaser(int x, int y, double dir) {
		super(x, y, dir);
		range = 200;
		damage = 20;

		speed = 1;
		nX = (Math.cos(angle) * speed);
		nY = (Math.sin(angle) * speed);
		sprite = Sprites.projectileLaser;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
		screen.renderMob((int) corners[0][0], (int) corners[0][1], Sprites.debugSprite0);
		screen.renderMob((int) corners[1][0], (int) corners[1][1], Sprites.debugSprite1);
		screen.renderMob((int) corners[2][0], (int) corners[2][1], Sprites.debugSprite2);
		screen.renderMob((int) corners[3][0], (int) corners[3][1], Sprites.debugSprite3);
		screen.renderMob((int) (x + 7.5 - Math.signum(nX)), (int) (y + 7.5 - Math.signum(nY)), Sprites.debugSprite4);
	}

	protected void move() {
		x += nX;
		y += nY;
		if (distance() > range) {
			remove();
		}
	}

	public void update() {
		
		//if (level.tileCollision((int) x + (int) nX, (int) y + (int) nY, 1, 6, 6)) {
		if (collision(x + nX, y + nY)) {
			remove();
			level.add(new SpawnerParticle((int) (x + 7.5), (int) (y + 8.5), 15, 50, level));
		} else {
			move();			
		}
	}
	
	private boolean collision(double xA, double yA) {
		return level.tileCollision((int) (x + nX), (int) (y + nY), 5, 6, 5, 5);
		
		/*boolean solid = false;
		for (int corner = 0; corner < 4; corner++) {
			double xT = (x + 5*(corner % 2) + 5) / 16;
			double yT = (y + 6*(corner / 2) + 5) / 16;
			
			corners[corner][0] = xT * 16;
			corners[corner][1] = yT * 16;
			
			if (level.getTile((int)xT, (int)yT).isSolid()) {
				solid = true;
				break;
			}
		}
		return solid;*/
	}

	public void remove() {
		removed = true;
	}

	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}

}
