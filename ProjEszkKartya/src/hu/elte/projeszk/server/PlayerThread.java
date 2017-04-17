package hu.elte.projeszk.server;

/**
 * 
 * @author scherzne
 *
 */
public class PlayerThread extends Thread {
	private Player player;
	private PlayerManagerThread manager;

	public PlayerThread(Player player, PlayerManagerThread manager) {
		super("player-"+player.getId());
		this.player = player;
		this.manager = manager;
	}

	@Override
	public void run() {
		System.out.println("playerthread started:"+player.getId());
		String row;
		
		while(true){
			row=player.read();
			if(manager.readRow(player,row)){
				player.updateLastAction();
			}else break;
		}
		player.die();
	}

	public Player getPlayer() {
		return player;
	}

	
	
}
