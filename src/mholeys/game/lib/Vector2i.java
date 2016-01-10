package mholeys.game.lib;

public class Vector2i {

	private int x, y;

	public Vector2i() {
		set(0, 0);
	}

	public Vector2i(Vector2i v) {
		set(v.x, v.y);
	}

	public Vector2i(int x, int y) {
		set(x, y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}

	public Vector2i add(Vector2i v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	public Vector2i subtract(Vector2i v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

}
