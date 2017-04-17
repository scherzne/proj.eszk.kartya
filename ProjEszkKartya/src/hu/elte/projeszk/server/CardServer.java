package hu.elte.projeszk.server;

import hu.elte.projeszk.Consts;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * 
 * @author scherzne
 *
 */
public class CardServer {
	private static ServerSocket server=null;	

	public static void main(String[] args) throws IOException {
		server=new ServerSocket(Consts.PORT);
		server.setSoTimeout(Consts.START_GAME_TIMEOUT);
		
		//játékos gyűjtő
		ArrayList<Player> players=new ArrayList<>();
		//managereket is begyűjtjük, hogy ne lógjanak a levegőben
		ArrayList<PlayerManagerThread> managers=new ArrayList<>();
		PlayerManagerThread manager;
		//player egyedi azonosító számlálója
		int id=-1;//ez mindegy, csak a számláló növelése most az accept után van, ami blokkol
		int managerId=0;//ez nulláról kezdve is jó, mert nem fut át rajta a kód
		long lastConnected=System.currentTimeMillis();
		
		//TODO: leállás esetén a szálakat ki kell irtani amennyire csak lehet (amennyi hozzáférhető
		//és nem lóg a levegőben)
		//(előfordulhat hogy valaki beragad, azokat le kell időzíteni!)
		while(true){			
			//várakozási idő lejárt és már megvan a minimum játékos vagy megvan a max játékos szám
			if((System.currentTimeMillis()-lastConnected>Consts.START_GAME_TIMEOUT*1000 
					&& players.size()>=Consts.MIN_PLAYERS)
					|| players.size()>=Consts.MAX_PLAYERS
					){
				//létrejött egy játékos csoport
				manager=new PlayerManagerThread(managerId, players);
				managerId++;
				managers.add(manager);
				manager.start();//elindítjuk a manager-t, innen ő kezeli a játékos csoportot
			}
			try {
				//ők már várakoznak a partnerre, ne dobódjonak el
				if(!players.isEmpty()){
					for(Player player:players)
						player.updateLastAction();
				}
				
				//valaki jött, eddig blokkol
				Socket socket=server.accept();
				//innen a kód csak akkor fut le, ha nem időzített le!
				id++;
				lastConnected=System.currentTimeMillis();
				
				Player player=new Player(socket, id);				
				players.add(player);//átmeneti gyűjtőbe tesszük

			}catch (SocketTimeoutException e) {
				
			}
		}
	}
}
