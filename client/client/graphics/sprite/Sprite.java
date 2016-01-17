package client.graphics.sprite;

public class Sprite {

	public final int SIZE;
	
	private int width, height;
	private int x, y;
	
	public int[] pixels;
	
	public SpriteSheet sheet;
	
	public int transparent;

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
		SIZE = width;
		this.width = width;
		this.height = height;
		this.x = x * width;
		this.y = y * height;
		this.transparent = sheet.transparent;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int colour) {
		SIZE = width;
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
		this.x = x * width;
		this.y = y * height;
		setColour(colour);
	}
	
	public Sprite(int size, int colour, boolean transparent) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[width *  height];
		setColour(colour);
		if (transparent) {
			this.transparent = colour;
		}
	}
	
	public Sprite(int size, String path) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		x = 0;
		y = 0;
		sheet = new SpriteSheet(path, size);
		load();
	}
	
	public Sprite(int[] spritePixels, int width, int height, int transparent) {
		this.width = width;
		this.height = height;
		SIZE = width;
		this.transparent = transparent;
		this.pixels = spritePixels;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	public void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
	
}

