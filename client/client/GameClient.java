package client;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import packets.PacketType;
import server.GameServer;
import client.entity.Player;
import client.graphics.Screen;
import client.graphics.sprite.Sprites;
import client.input.Keyboard;
import client.input.Mouse;
import client.world.World;


public class GameClient extends Canvas implements Runnable {

	public DatagramSocket socket;
	
	private static int width = 200;
	private static int height = width / 16 * 9;
	public static int pixelScale = 2;
	
	private boolean running = false; 
	
	private Screen screen;
	private JFrame frame;
	public Thread gameThread;
	private Keyboard keyboard;
	private Mouse mouse;
	
	private BufferStrategy bs = null;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); 
	
	public static final String ip = "127.0.0.1";//"mholeys.no-ip.org";
	public static final int port = 1561;
	
	public List<Player> players = new ArrayList<Player>();
	public Player client;
	public World world;
	
	private ClientListener listener;
	
	public static void main(String[] args) {
		GameClient game = new GameClient();
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
	
	public GameClient() {
		Dimension size = new Dimension(width * pixelScale, height * pixelScale);
		this.setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		world = new World(this);
		keyboard = new Keyboard();
		mouse = new Mouse();
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		setupNetwork();
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				
			} 
			
		});
	}
	
	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "Graphics");
		gameThread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final int MAX_UPDATES = 60;
		final double NS = 1000000000.0 / MAX_UPDATES;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		boolean alwaysRender = true;
		boolean shouldRender = false;
		while (running) {
			shouldRender = false;
			long now = System.nanoTime();
			delta += (now - lastTime) / NS;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
				shouldRender = true;
			}
			if (shouldRender || alwaysRender) {
				render();
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("FPS: " + frames + ", UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private int[][] corners = new int[4][2];
	public void update() {
		int x = client.getX();
		int y = client.getY();
		keyboard.update();
		if (keyboard.quit) {
			disconnect();
		}
		if (keyboard.up) {
			y--;
		}
		if (keyboard.down) {
			y++;
		}
		if (keyboard.left) {
			x--;
		}
		if (keyboard.right) {
			x++;
		}
		for (int corner = 0; corner < 4; corner++) {
			int xTile = (x + 15*(corner % 2)) / 16;
			int yTile = (y + 15*(corner / 2)) / 16;
			corners[corner][0] = xTile << 4;
			corners[corner][1] = yTile << 4;
		}
		if ((x != client.getX()) || (y != client.getY())) {
			moveClient(x, y);
		}
		world.update();
	}
	
	public void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		double xScroll = (client.getX() - (screen.width/2));
		double yScroll = (client.getY() - (screen.height/2));
		world.render((int) xScroll, (int) yScroll, screen);
		
		screen.renderSprite(corners[0][0], corners[0][1], Sprites.debug1, true);
		screen.renderSprite(corners[1][0], corners[1][1], Sprites.debug1, true);
		screen.renderSprite(corners[2][0], corners[2][1], Sprites.debug1, true);
		screen.renderSprite(corners[3][0], corners[3][1], Sprites.debug1, true);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	
		g.dispose();
		bs.show();
	}
	
	public void setupNetwork() {
		String username = JOptionPane.showInputDialog("Enter a username");
		try {
			socket = new DatagramSocket();
			listener = new ClientListener(socket, this);
			Random r = new Random();
			client = new Player(username, r.nextInt(10 << 4) + 32, r.nextInt(10 << 4) + 32);
			byte[] connect = (PacketType.CONNECT.getIDString() + client.getUsername() + ";" + client.getX() + ";" + client.getY()).getBytes();
			byte[] data = new byte[Settings.PACKET_LENGTH];
			System.arraycopy(connect, 0, data, 0, connect.length);
			socket.send(new DatagramPacket(data, data.length, InetAddress.getByName(ip), port));
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void moveClient(int x, int y) {
		//client.move(x, y);
		byte[] data = new byte[Settings.PACKET_LENGTH];
		byte[] moveData = (PacketType.MOVE.getIDString() + client.getUsername() + ";" + x + ";" + y).getBytes();
		System.arraycopy(moveData, 0, data, 0, moveData.length);
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
			socket.send(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void confirmStillConnected() {
		byte[] data = new byte[Settings.PACKET_LENGTH];
		byte[] confirmData = (PacketType.CONFIRM_ONLINE.getIDString() + client.getUsername()).getBytes();
		System.arraycopy(confirmData, 0, data, 0, confirmData.length);
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
			socket.send(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		byte[] data = new byte[Settings.PACKET_LENGTH];
		byte[] disconnectData = (PacketType.DISCONNECT.getIDString() + client.getUsername()).getBytes();
		System.arraycopy(disconnectData, 0, data, 0, disconnectData.length);
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
			socket.send(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		listener.stopRunning();
		stop();
	}
	
}
