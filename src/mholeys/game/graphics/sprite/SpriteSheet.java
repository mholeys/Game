package mholeys.game.graphics.sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	public int transparent;
	private Sprite[] sprites;

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		this.WIDTH = size;
		this.HEIGHT = size;
		transparent = -1;
		pixels = new int[SIZE * SIZE];
		load();
	}

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		this.WIDTH = w;
		this.HEIGHT = h;

		if (w == h) {
			this.SIZE = w;
		} else {
			this.SIZE = -1;
		}
		pixels = new int[WIDTH * HEIGHT];
		for (int y0 = 0; y0 < HEIGHT; y0++) {
			for (int x0 = 0; x0 < WIDTH; x0++) {
				int xp = xx + x0;
				int yp = yy + y0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize, transparent);
				sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		this.WIDTH = width;
		this.HEIGHT = height;
		transparent = -1;
		pixels = new int[WIDTH * HEIGHT];
		load();
	}

	public SpriteSheet(String path, int width, int height, int transparent) {
		this.path = path;
		SIZE = -1;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.transparent = transparent;
		pixels = new int[WIDTH * HEIGHT];
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sprite[] getSprites() {
		return sprites;
	}

}
