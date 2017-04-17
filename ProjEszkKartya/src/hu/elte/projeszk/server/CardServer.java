package hu.elte.projeszk.server;

import hu.elte.projeszk.Consts;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class CardServer {
	private static ServerSocket server=null;	

	public static void main(String[] args) throws IOException {
		server=new ServerSocket(Consts.PORT);
		
		//játékos gyűjtő
		ArrayList<Player> players=new ArrayList<>();
		//managereket is begyűjtjük, hogy ne lógjanak a levegőben
		ArrayList<PlayerManagerThread> managers=new ArrayList<>();
		PlayerManagerThread manager;
		//player egyedi azonosító számlálója
		int id=-1;
		
		while(true){
			try {
				//ők már várakoznak a partnerre, ne dobódjonak el
				if(!players.isEmpty()){
					for(Player player:players)
						player.updateLastAction();
				}
				
				//valaki jött
				Socket socket=server.accept();
				id++;
				Player player=new Player(socket, id);				
				players.add(player);//átmeneti gyűjtőbe tesszük

			}catch (SocketTimeoutException e) {
				
			}
		}
	}
}
