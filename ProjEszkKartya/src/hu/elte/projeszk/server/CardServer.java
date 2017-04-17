package hu.elte.projeszk.server;

import hu.elte.projeszk.Consts;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class CardServer {
	private static ServerSocket server=null;	

	public static void main(String[] args) throws IOException {
		server=new ServerSocket(Consts.PORT);
		
		//játékos gyűjtő
		ArrayList<Player> players=new ArrayList<>();
		//managereket is begyűjtjük, hogy ne lógjanak a levegőben
		ArrayList<PlayerManagerThread> managers=new ArrayList<>();

	}
}
