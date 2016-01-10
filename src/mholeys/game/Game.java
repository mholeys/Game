package mholeys.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import mholeys.game.entity.mob.EnitityPlayer;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprites;
import mholeys.game.input.Keyboard;
import mholeys.game.input.Mouse;
import mholeys.game.level.Level;
import mholeys.game.level.Levels;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private static int width = 480;
	private static int height = width / 16 * 9;
	public static int pixelScale = 1;
	private boolean running = false;

	@SuppressWarnings("unused")
	private int x = 0, y = 0;

	private Screen screen;
	private Level level;
	private EnitityPlayer player;

	private Thread gameThread;
	private JFrame frame;
	private Keyboard key;

	private int refreashDelay = 30;
	private String levelName = "temp";

	private BufferStrategy bs = null;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * pixelScale, height * pixelScale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		level = new Level(20, 20);
		key = new Keyboard();

		player = new EnitityPlayer(level.spawnX, level.spawnY, key);
		level.add(player);

		Mouse mouse = new Mouse();
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "Display");
		gameThread.start();
	}

	public synchronized void stop() {
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final int MAXUPS = 60;// set for updates per second
		final double NS = 1000000000.0 / MAXUPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		boolean shouldRender = false;
		while (running) {
			shouldRender = true; // Change this to true make game run at max performance
			long now = System.nanoTime();
			delta += (now - lastTime) / NS;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
				shouldRender = true;
			}
			if (shouldRender) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("FPS: " + frames + ", Updates: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void update() {
		key.update();
		if (key.up)
			y--;
		if (key.down)
			y++;
		if (key.left)
			x--;
		if (key.right)
			x++;
		if (refreashDelay == 0) {
			if (key.F5) {
				level = Levels.getLevel(levelName);
				player.init(level);
				refreashDelay = 30;
			}
		} else {
			refreashDelay--;
		}
		level.update();
	}

	private void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		double xScroll = (player.getX()) - (screen.width / 2) + (Sprites.player.SIZE / 2);
		double yScroll = (player.getY()) - (screen.height / 2) + (Sprites.player.SIZE / 2);
		level.render((int) xScroll, (int) yScroll, screen);

		for (int iii = 0; iii < pixels.length; iii++) {
			pixels[iii] = screen.pixels[iii];
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.setFont(new Font("Ariel", 16, 20));
		g.drawString("Button: " + Mouse.getButton(), 80, 80);
		g.drawString("X: " + Mouse.getX() + ", Y: " + Mouse.getY(), 80, 100);
		
		g.dispose();

		bs.show();
	}

	public static void main(String[] args) {
		int firstArg = -1;
		int secondArg = -1;
		if (args.length > 1) {
			try {
				firstArg = Integer.parseInt(args[0]);
				secondArg = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Argument" + " must be an integer");
				System.exit(1);
			}
		} else {
			System.out.println("Number of arguments: " + args.length);
		}

		if (firstArg == -1)
			firstArg = 480;
		if (secondArg == -1)
			secondArg = 3;

		width = firstArg;
		height = width / 16 * 9;
		pixelScale = secondArg;

		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Game");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

	public static int getWindowWidth() {
		return width * pixelScale;
	}

	public static int getWindowHeight() {
		return height * pixelScale;
	}

	public static int getScreenWidth() {
		return width;
	}

	public static int getScreenHeight() {
		return height;
	}

}
