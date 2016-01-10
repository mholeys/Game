package mholeys.game.level;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mholeys.game.entity.DepthComparator;
import mholeys.game.entity.Entity;
import mholeys.game.entity.mob.EnitityPlayer;
import mholeys.game.entity.particle.Particle;
import mholeys.game.entity.projectile.Projectile;
import mholeys.game.entity.tile.EntityTree;
import mholeys.game.graphics.Screen;
import mholeys.game.graphics.sprite.Sprites;
import mholeys.game.level.tile.Tile;
import mholeys.game.level.tile.Tiles;
import mholeys.game.lib.ID;

public class Level {
	protected int width;
	protected int height;
	public int spawnX;
	public int spawnY;
	protected String path;
	protected int[] tilesInt;
	protected Tile[] tiles;
	protected boolean hasTiles = false;
	private static Random random = new Random();
	private boolean save = false;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	private List<EnitityPlayer> players = new ArrayList<EnitityPlayer>();

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		tiles = new Tile[width * height];
		save = false;
		generateLevel();
	}

	public Level(int width, int height, String path) {
		this.width = width;
		this.height = height;
		this.path = path;
		tilesInt = new int[width * height];
		tiles = new Tile[width * height];
		save = true;
		generateLevel();
	}

	public Level(String path) {
		this.path = path;
		save = false;
		loadLevel(path);
	}

	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int temp = random.nextInt(20); // 20 can be
												// replaced
												// with any
												// value up
												// to 1024
				switch (temp) {
				case 19:
				case 18:
				case 17:
				case 16:
				case 15:
				case 14:
				case 13:
				case 12:
				case 11:
				case 10:
					temp = 0;
					break;
				case 9:
				case 8:
					temp = 7;
					break;
				}
				tilesInt[x + y * width] = temp;
				tiles[x + y * width] = getTile(x, y);
			}
		}
		hasTiles = true;
		spawnX = random.nextInt(width) * 16;
		spawnY = random.nextInt(height) * 16;
		tilesInt[(spawnX / 16) + (spawnY / 16) * width] = 0;
		tiles[(spawnX / 16) + (spawnY / 16) * width] = Tiles.grass;
		hasTiles = true;
		if (save) {
			saveLevel(path);
		}
	}

	public List<EnitityPlayer> getPlayers() {
		return players;
	}

	public EnitityPlayer getPlayerAt(int index) {
		return players.get(index);
	}

	public EnitityPlayer getClientPlayer() {
		return players.get(0);
	}

	protected void loadLevel(String path) {
		Path levelPath = null;
		ClassLoader cl = this.getClass().getClassLoader();
		try {
			URL temp = cl.getResource(path);
			System.out.println(temp.toString());
			levelPath = Paths.get(temp.toURI());
			System.out.println(levelPath.toString());
			Scanner scanner;
			try {
				scanner = new Scanner(levelPath.toFile());
				int iii = 0;

				if (scanner.hasNextInt()) {
					width = scanner.nextInt();
				}
				if (scanner.hasNextInt()) {
					height = scanner.nextInt();
				}
				if (scanner.hasNextInt()) {
					spawnX = scanner.nextInt();
				}
				if (scanner.hasNextInt()) {
					spawnY = scanner.nextInt();
				}

				int[] levelDataRaw = new int[width * height];
				tilesInt = new int[width * height];
				tiles = new Tile[width * height];

				while (scanner.hasNextInt()) {
					levelDataRaw[iii] = scanner.nextInt();
					iii++;
				}

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						tilesInt[x + y * width] = levelDataRaw[x + y * width];
					}
				}
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						tiles[x + y * width] = getTile(x, y);
					}
				}
				tilesInt = null;
				hasTiles = true;
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		//Replace with Entity loading/loader
		/*for (int iii = 0; iii < 2; iii++) {
			add(new EnitityDummy(5, 5));
			add(new EntitiyChaser(5, 5));
		}*/
		add(new EntityTree(getTile(2, 2), 2, 2, Sprites.tree));
		for (Entity e : entities) {
			if (e instanceof EntityTree) {
				System.out.println(((EntityTree) e).tile);
			}
		}
		System.out.println(getTile(2, 2));
	}

	private void saveLevel(String path) {
		Path levelPath = null;
		Charset charset = Charset.forName("UTF-8");
		ClassLoader cl = this.getClass().getClassLoader();
		try {
			levelPath = Paths.get(new URL(cl.getResource(path), "").toURI());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		int lines = 0;
		int chars = 0;
		try (BufferedWriter writer = Files.newBufferedWriter(levelPath, charset)) {
			writer.write(width + " " + height + " " + spawnX + " " + spawnY);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if ((x + y * width) % width == 0) {
						System.out.println("writing new line");
						writer.write("\n");
						lines += 1;
						chars = 0;
					}
					// adding 1 to x so that it starts the check from 1 and not from 0
					if (x + 1 < width) {
						System.out.println("writing part of a line");
						writer.write(tilesInt[x + y * width] + " ");
						chars += 2;
					}
					// adding 1 to x so that it starts the check from 1 and not from 0
					if (x + 1 == width) {
						System.out.println("writing end of a line");
						writer.write(tilesInt[x + y * width] + "");
						chars += 1;
					}
					System.out.println(lines + ", " + chars);
				}
			}
			writer.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	public void update() {
		for (int iii = 0; iii < entities.size(); iii++) {
			entities.get(iii).update();
		}
		for (int iii = 0; iii < projectiles.size(); iii++) {
			projectiles.get(iii).update();
		}
		for (int iii = 0; iii < particles.size(); iii++) {
			particles.get(iii).update();
		}
		for (int iii = 0; iii < players.size(); iii++) {
			players.get(iii).update();
		}
		remove();
	}

	private void remove() {
		for (int iii = 0; iii < entities.size(); iii++) {
			if (entities.get(iii).isRemoved())
				entities.remove(iii);
		}
		for (int iii = 0; iii < projectiles.size(); iii++) {
			if (projectiles.get(iii).isRemoved())
				projectiles.remove(iii);
		}
		for (int iii = 0; iii < particles.size(); iii++) {
			if (particles.get(iii).isRemoved())
				particles.remove(iii);
		}
		for (int iii = 0; iii < players.size(); iii++) {
			if (players.get(iii).isRemoved())
				players.remove(iii);
		}
	}

	@SuppressWarnings("unused")
	private void time() {
		// do time based code
	}

	public boolean tileCollision(int x, int y, int xScale, int yScale, int xOffset, int yOffset) {
		boolean solid = false;
		for (int corner = 0; corner < 4; corner++) {
			int xT = (x + xScale*(corner % 2) + xOffset) >> 4;
			int yT = (y + yScale*(corner / 2) + yOffset) >> 4;
			if (getTile(xT, yT).isSolid())
				solid = true;
		}
		return solid;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int y0 = yScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (int iii = 0; iii < entities.size(); iii++) {
			entities.get(iii).render(screen);
		}
		for (int iii = 0; iii < projectiles.size(); iii++) {
			projectiles.get(iii).render(screen);
		}
		for (int iii = 0; iii < particles.size(); iii++) {
			particles.get(iii).render(screen);
		}
		for (int iii = 0; iii < players.size(); iii++) {
			players.get(iii).render(screen);
		}
		
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof EnitityPlayer) {
			players.add((EnitityPlayer) e);
		} else {
			entities.add(e);
			entities.sort(new DepthComparator());
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int Ex = (int) e.getX();
		int Ey = (int) e.getY();
		for (int iii = 0; iii < entities.size(); iii++) {
			Entity entity = entities.get(iii);
			int x = (int) entity.getX();
			int y = (int) entity.getY();
			int dX = Math.abs(Ex - x);
			int dY = Math.abs(Ey - y);
			double distance = Math.sqrt((dX * dX) + (dY * dY));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}
	
	public List<Entity> getEntities(int Ex, int Ey, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		for (int iii = 0; iii < entities.size(); iii++) {
			Entity entity = entities.get(iii);
			int x = (int) entity.getX();
			int y = (int) entity.getY();
			int dX = Math.abs(Ex - x);
			int dY = Math.abs(Ey - y);
			double distance = Math.sqrt((dX * dX) + (dY * dY));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}

	public List<EnitityPlayer> getPlayers(Entity e, int radius) {
		List<EnitityPlayer> result = new ArrayList<EnitityPlayer>();
		int Ex = (int) e.getX();
		int Ey = (int) e.getY();
		for (int iii = 0; iii < players.size(); iii++) {
			EnitityPlayer player = players.get(iii);
			int x = (int) player.getX();
			int y = (int) player.getY();
			int dX = Math.abs(Ex - x);
			int dY = Math.abs(Ey - y);
			double distance = Math.sqrt((dX * dX) + (dY * dY));
			if (distance <= radius)
				result.add(player);
		}
		return result;
	}

	public Tile getTile(int x, int y) {
		if (x <= 0 || x >= width || y <= 0 || y >= height) {
			return Tiles.voidTile;
		}
		if (hasTiles) {
			return tiles[x + y * width];
		} else {
			switch (tilesInt[x + y * width]) {
			case ID.grass:
				return Tiles.grass;
			case ID.grassFlower:
				return Tiles.grassFlower;
			case ID.grassRock:
				return Tiles.grassRock;
			case ID.grassTall1:
				return Tiles.grassTall1;
			case ID.grassTall2:
				return Tiles.grassTall2;
			case ID.grassDirt:
				return Tiles.grassDirt;
			case ID.brick:
				return Tiles.brick;
			case ID.grassPuddle:
				return Tiles.grassPuddle;
			case ID.tree:
				return Tiles.tree;
			default:
				return Tiles.voidTile;
			}
		}
	}
}
