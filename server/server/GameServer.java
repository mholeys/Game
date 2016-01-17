package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import client.Settings;
import packets.PacketType;
import packets.PlayerData;
import server.entity.Entity;
import server.entity.Player;
import server.world.World;

public class GameServer extends Thread {

	public List<Player> players = new ArrayList<Player>();
	
	private DatagramSocket socket;
	private ServerListener listener;
	public static World world;
	private boolean running = false;
	
	public static void main(String[] args) {
		new GameServer(1561);
	}
	
	public GameServer(int port) {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		world = new World(this);
		running = true;
		this.start();
		listener = new ServerListener(socket, this);
		listener.start();
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			world.update();
			ListIterator<Player> playerIterator = players.listIterator();
			while (playerIterator.hasNext()) {
				Player p = playerIterator.next();
				if (p.shouldRemove()) {
					playerIterator.remove();
					System.out.println("Removed player " + p.getUsername());
					byte[] data = new byte[Settings.PACKET_LENGTH];
					byte[] disconnectData = (PacketType.DISCONNECT.getIDString() + p.getUsername()).getBytes();
					System.arraycopy(disconnectData, 0, data, 0, disconnectData.length);
					DatagramPacket disconnect = new DatagramPacket(data, Settings.PACKET_LENGTH);
					sendToAll(disconnect);
				}
				if (p.shouldConfirm()) {
					byte[] data = new byte[Settings.PACKET_LENGTH];
					byte[] confirmData = (PacketType.CONFIRM_ONLINE.getIDString() + p.getUsername()).getBytes();
					System.arraycopy(confirmData, 0, data, 0, confirmData.length);
					DatagramPacket packet = new DatagramPacket(data, data.length);
					sendTo(packet, p);
				}
			}
		}
	}
	
	public void handlePacket(int type, DatagramPacket packet) {
		byte[] data = packet.getData(); 
		PlayerData pd = new PlayerData(data);
		Player p = new Player(pd.getUsername(), pd.getX(), pd.getY(), packet.getAddress(), packet.getPort());
		int index = players.indexOf(p);;
		switch(PacketType.valueOf(type)) {
		case CONNECT:
			if (players.contains(p)) {
				try {
					byte[] disData = new byte[Settings.PACKET_LENGTH];
					byte[] tempData = (PacketType.DISCONNECT.getIDString() + pd.getUsername()).getBytes();
					System.arraycopy(tempData, 0, disData, 0, tempData.length);
					DatagramPacket disconnect = new DatagramPacket(disData, Settings.PACKET_LENGTH, packet.getAddress(), packet.getPort());
					socket.send(disconnect);
					System.out.println("Denied Connection to player with same name - " + pd.getUsername());
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			players.add(p);
			sendToAll(packet);
			System.out.println("Sending entities");
			sendEntities(p);
			System.out.println("Sending players");
			sendPlayers(p);
			break;
		case DISCONNECT:
			sendToAll(packet);			
			players.remove(p);
			break;
		case INVALID:
			System.out.println("Error - Packet invalid");
			break;
		case MOVE:
			players.get(index).move(pd.getX(), pd.getY());
			Player newP = players.get(index);
			byte[] newData = (newP.getUsername() + ";" + newP.getX() + ";" + newP.getY()).getBytes();
			System.arraycopy(newData, 0, data, 4, newData.length);
			sendToAll(packet);
			break;
		case CONFIRM_ONLINE:
			if (index == -1) {
				break;
			} else {
				players.get(index).hasConfirmed();
			}
			break;
		default:
			break;
		}
	}
	
	private void sendEntities(Player player) {
		List<Entity> entities = world.getEntities(); 
		byte[][] data = new byte[entities.size()][Settings.PACKET_LENGTH];
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			byte[] entityData = e.getData();
			System.arraycopy(PacketType.ENTITY_UPDATE.getIDString().getBytes(), 0, data[i], 0, 4);
			System.arraycopy(entityData, 0, data[i], 4, entityData.length);
		}
		for (int i = 0; i < entities.size(); i++) {
			DatagramPacket packet = new DatagramPacket(data[i], 0, Settings.PACKET_LENGTH);
			sendTo(packet, player);
		}
	}
	
	private void sendToAll(DatagramPacket packet) {
		ListIterator<Player> playerIterator = players.listIterator();
		while (playerIterator.hasNext()) {
			Player p = playerIterator.next();
			packet.setAddress(p.getAddress());
			packet.setPort(p.getPort());
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendPlayers(Player player) { 
		byte[][] data = new byte[players.size()][Settings.PACKET_LENGTH];
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p != null) {
				byte[] playerData = p.getData();
				System.arraycopy(PacketType.PLAYER.getIDString().getBytes(), 0, data[i], 0, 4);
				System.arraycopy(playerData, 0, data[i], 4, playerData.length);
			}
		}
		for (int i = 0; i < players.size(); i++) {
			DatagramPacket packet = new DatagramPacket(data[i], 0, Settings.PACKET_LENGTH);
			sendTo(packet, player);
		}
	}
	
	private void sendTo(DatagramPacket packet, Player player) {
		packet.setAddress(player.getAddress());
		packet.setPort(player.getPort());
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
