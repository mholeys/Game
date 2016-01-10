package mholeys.game.graphics.sprite;

import java.awt.Image;

public class Sprite {
	public final int SIZE;
	private int width, height;
	private int x, y;
	public int[] pixels;
	public Image image;
	public SpriteSheet sheet;
	public int transparent;

	protected Sprite(SpriteSheet sheet, int width, int height) {
		if (width == height) {
			this.SIZE = width;
		} else {
			this.SIZE = -1;
		}
		this.sheet = sheet;
		this.width = width;
		this.height = height;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		this.x = x * width;
		this.y = y * height;

		this.transparent = sheet.transparent;
		this.sheet = sheet;
		load();
	}

	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		SIZE = -1;
		pixels = new int[width * height];
		this.width = width;
		this.height = height;
		this.x = x * width;
		this.y = y * height;
		this.transparent = sheet.transparent;
		this.sheet = sheet;
		load();
	}

	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x * width;
		this.y = y * height;
		setColour(colour);
	}

	public Sprite(int size, int colour) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		setColour(colour);
	}
	
	public Sprite(int size, int colour, boolean transparent) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		setColour(colour);
		if (transparent)
			this.transparent = colour;
	}

	public Sprite(int size, String path) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		x = 0 * width;
		y = 0 * height;
		sheet = new SpriteSheet(path, size);
	}

	public Sprite(int[] spritePixels, int width, int height, int transparent) {
		this.width = width;
		this.height = height;
		if (width == height) {
			this.SIZE = width;
		} else {
			this.SIZE = -1;
		}
		this.transparent = transparent;
		this.pixels = spritePixels;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void setColour(int colour) {
		for (int iii = 0; iii < width * height; iii++) {
			pixels[iii] = colour;
		}
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
	
}
