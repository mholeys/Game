package client.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import client.GameClient;
import client.Settings;
import client.entity.Entity;
import client.entity.Player;
import client.graphics.Screen;
import client.world.tile.Tile;
import client.world.tile.Tiles;

public class World {

	private Tile[] tiles;
	public List<Entity> entities = new ArrayList<Entity>();
	private GameClient client;
	private int width, height;
	
	public World(GameClient client) {
		this.client = client;
		loadWorld();
	}
	
	public void loadWorld() {
		int width = 0;
		int height = 0;
		ClassLoader classLoader = this.getClass().getClassLoader();
		File worldTiles = new File(classLoader.getResource(Settings.WORLD_PATH).getPath());
		System.out.println("Loading world....");
		try {
			//Load the tiles of the world
			Scanner worldLoader = new Scanner(worldTiles);
			if (worldLoader.hasNextInt()) {
				width = worldLoader.nextInt();
			} else {
				System.err.println("World is corrupt");
				System.exit(2);
			}
			if (worldLoader.hasNextInt()) {
				height = worldLoader.nextInt();
			} else {
				System.err.println("World is corrupt");
				System.exit(2);
			}
			
			if ((width == 0) || (height == 0)) {
				System.err.println("World is corrupt");
				System.exit(2);
			}
			this.width = width;
			this.height = height;
			tiles = new Tile[width * height];
			
			int tile = 0;
			while (worldLoader.hasNextInt()) {
				tiles[tile] = Tiles.getTile(worldLoader.nextInt());
				tile++;
			}
			worldLoader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not find the world to load");
		}
		System.out.println("Loaded world");
	}	
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public Tile getTile(int x, int y) {
		if (x <= 0 || x >= width || y <= 0 || y >= height) {
			return Tiles.VOID_TILE;
		}
		return tiles[x + y * width];
	}
	
	public void update() {
		//TODO add all update for entities
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
		for (Entity e : entities) {
			e.render(screen);
		}
		for (Player p : client.players) {
			p.render(screen);
		}
		client.client.render(screen);
	}
	
}
