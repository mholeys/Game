package mholeys.game.graphics;

import mholeys.game.entity.mob.EnitityMob;
import mholeys.game.entity.projectile.Projectile;
import mholeys.game.graphics.sprite.Sprite;
import mholeys.game.graphics.sprite.SpriteSheet;

public class Screen {
	
	public int width;
	public int height;
	public int[] pixels;
	private int xOffset;
	private int yOffset;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
	}

	public void clear() {
		for (int iii = 0; iii < pixels.length; iii++) {
			pixels[iii] = 0;
		}
	}

	public void renderSpriteSheet(int xP, int yP, SpriteSheet sheet, boolean fixed) {
		int colour, trans;
		trans = sheet.transparent;
		if (fixed) {
			xP -= xOffset;
			yP -= yOffset;
		}
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int yA = y + yP;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xA = x + xP;
				if (xA < -sheet.WIDTH || xA >= width || yA < 0 || yA >= height)
					break;
				if (xA < 0)
					xA = 0;
				if (trans != -1) {
					colour = sheet.pixels[x + y * sheet.WIDTH];
					if (colour != trans) {
						pixels[xA + yA * width] = sheet.pixels[x + y * sheet.WIDTH];
					}
				} else {
					pixels[xA + yA * width] = sheet.pixels[x + y * sheet.WIDTH];
				}
			}
		}
	}

	public void renderSprite(int xP, int yP, Sprite sprite, boolean fixed) {
		int colour, trans;
		trans = sprite.transparent;
		if (fixed) {
			xP -= xOffset;
			yP -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yA = y + yP;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xA = x + xP;
				if (xA < -sprite.getWidth() || xA >= width || yA < 0 || yA >= height)
					break;
				if (xA < 0)
					xA = 0;
				if (trans != -1) {
					colour = sprite.pixels[x + y * sprite.getWidth()];
					if (colour != trans) {
						pixels[xA + yA * width] = sprite.pixels[x + y * sprite.getWidth()];
					}
				} else {
					pixels[xA + yA * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	public void renderProjectile(int xP, int yP, Projectile p) {
		int colour, trans;
		trans = p.getSprite().transparent;
		xP -= xOffset;
		yP -= yOffset;
		for (int y = 0; y < p.getSprite().getHeight(); y++) {
			int yA = y + yP;
			for (int x = 0; x < p.getSprite().getWidth(); x++) {
				int xA = x + xP;
				if (xA < -p.getSprite().getWidth() || xA >= width || yA < 0 || yA >= height)
					break;
				if (xA < 0)
					xA = 0;
				if (trans != -1) {
					colour = p.getSprite().pixels[x + y * p.getSprite().getWidth()];
					if (colour != trans) {
						pixels[xA + yA * width] = p.getSprite().pixels[x + y * p.getSprite().getWidth()];
					}
				} else {
					pixels[xA + yA * width] = p.getSprite().pixels[x + y * p.getSprite().getWidth()];
				}
			}
		}
	}

	public void renderMob(int xP, int yP, Sprite sprite) {
		int colour;
		int xS, yS;
		xP -= xOffset;
		yP -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yA = y + yP;
			yS = y;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xA = x + xP;
				xS = x;
				if (xA < -sprite.getWidth() || xA >= width || yA < 0 || yA >= height)
					break;
				if (xA < 0)
					xA = 0;
				colour = sprite.pixels[xS + yS * sprite.getWidth()];
				if (colour != 0xffff00ff) {
					pixels[xA + yA * width] = sprite.pixels[xS + yS * sprite.getWidth()];
				}
			}
		}
	}

	public void renderMob(int xP, int yP, EnitityMob mob) {
		int colour;
		int xS, yS;
		xP -= xOffset;
		yP -= yOffset;
		for (int y = 0; y < mob.getSprite().getHeight(); y++) {
			int yA = y + yP;
			yS = y;
			for (int x = 0; x < mob.getSprite().getWidth(); x++) {
				int xA = x + xP;
				xS = x;
				if (xA < -mob.getSprite().getWidth() || xA >= width || yA < 0 || yA >= height)
					break;
				if (xA < 0)
					xA = 0;
				colour = mob.getSprite().pixels[xS + yS * mob.getSprite().getWidth()];
				if (colour != 0xffff00ff) {
					pixels[xA + yA * width] = mob.getSprite().pixels[xS + yS * mob.getSprite().getWidth()];
				}
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}

}