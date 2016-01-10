package mholeys.game.entity.mob;


import mholeys.game.Game;
import mholeys.game.entity.projectile.Projectile;
import mholeys.game.entity.projectile.ProjectileLaser;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.AnimatedSprite;
import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.graphics.sprite.Sprites;
import mholeys.game.input.Keyboard;
import mholeys.game.input.Mouse;

public class EnitityPlayer extends EnitityMob {

	private Keyboard input;
	private int frame = 0;
	private Sprite sprite = Sprites.player;
	private double sDir = 0;
	private double speed;
	private double xA = 0, yA = 0;
	private boolean visible = true;
	private int visCooldown = 0;
	
	private AnimatedSprite animSprite = null;

	private int fireRate = 0;
	
	public EnitityPlayer(Keyboard input) {
		this.input = input;
		animSprite = Sprites.pDW;
	}

	public EnitityPlayer(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		fireRate = ProjectileLaser.RATE;
		animSprite = Sprites.pDW;
	}

	public void update() {
		speed = 1.0;
		if (moving) {
			animSprite.update();
		} else {
			animSprite.reset();
		}
		if (fireRate > 0) fireRate--;
		boolean cheat = false;
		xA = 0;
		yA = 0;
		if (input.space) {
			if (visible) {
				if (visCooldown == 0) {
					visible = false;
					visCooldown = 900;
				}
			} else {
				visible = true;
			}
			
		}
		if (input.shift)
			cheat = true;
		if (input.ctrl)
			speed = 10.0;
		if (input.up)
			yA -= speed;
		if (input.down)
			yA += speed;
		if (input.left)
			xA -= speed;
		if (input.right)
			xA += speed;
		if (visCooldown > 0) {
			visCooldown -= 1;
		}
		if (visCooldown == 300) {
			visible = true;
		}
		
		if (xA != 0 || yA != 0) {
			
			move(xA, yA, cheat);
			moving = true;
		} else {
			moving = false;
		}		
		
		if (frame < 6000)
			frame++;
		else
			frame = 0;
		
		clear();
		//updateShoot();
		
	}

	private void clear() {
		for (int iii = 0; iii < level.getProjectiles().size(); iii++) {
			Projectile p = level.getProjectiles().get(iii);
			if (p.isRemoved()) {
				level.getProjectiles().remove(iii);
			}
		}
	}
	
	private void updateShoot() {
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			int xM , yM;
			visible = true;
			xM = Mouse.getX() - Game.getWindowWidth() / 2;
			yM = Mouse.getY() - Game.getWindowHeight() / 2;
			
			sDir = Math.atan2(yM, xM);
			shoot((int)x, (int)y, sDir);
			
			fireRate = ProjectileLaser.RATE;
		}
	}

	public void render(Screen screen) {
		switch(dir) {
		case 0:
			animSprite = Sprites.pUP;
			break;
		case 1:
			animSprite = Sprites.pRI;
			break;
		case 2:
			animSprite = Sprites.pDW;
			break;
		case 3:
			animSprite = Sprites.pLE;
			break;
		case 4:
			animSprite = Sprites.pUP;
			break;
		}
		sprite = animSprite.getSprite();
		if (visible) {
			screen.renderMob((int) Math.floor(x), (int) Math.floor(y), sprite);
		}
	}

	public boolean getInvisible() {
		return !visible;
	}

}
