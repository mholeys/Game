package mholeys.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	private boolean[] keys = new boolean[600];
	public boolean up, down, left, right, ctrl, F5, shift, space;

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		ctrl = keys[KeyEvent.VK_CONTROL];
		F5 = keys[KeyEvent.VK_F5];
		shift = keys[KeyEvent.VK_SHIFT];
		space = keys[KeyEvent.VK_SPACE];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

}
