package hu.elte.projeszk.server;

import hu.elte.projeszk.Card;

import java.util.ArrayList;

public class PlayerManagerThread extends Thread {
	private ArrayList<Card> cardPack=new ArrayList<>();
	private int id=-1;	
	
	public PlayerManagerThread(int id) {
		super("manager "+id);
		this.id=id;
		
		generatePack();
	}



	private void generatePack(){
		
	}
}
