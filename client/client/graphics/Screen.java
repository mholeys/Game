package client.graphics;

import client.graphics.sprite.Sprite;

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
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSprite(int xPos, int yPos, Sprite sprite, boolean fixed) {
		int colour, transparent;
		transparent = sprite.transparent;
		if (fixed) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yActual = y + yPos;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xActual = x + xPos;
				if (xActual < -sprite.getWidth() || xActual >= width || yActual < 0 || yActual >= height) {
					break;
				}
				if (xActual < 0) {
					xActual = 0;
				}
				if (transparent != -1) {
					colour = sprite.pixels[x + y * sprite.getWidth()];
					if (colour != transparent) {
						pixels[xActual + yActual * width] = sprite.pixels[x + y * sprite.getWidth()];
					}
				} else {
					pixels[xActual + yActual * width] = sprite.pixels[x + y * sprite.getWidth()];
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
