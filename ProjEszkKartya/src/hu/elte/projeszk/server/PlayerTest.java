package hu.elte.projeszk.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {	
	private Socket socket;
	private Player player;
	
	@Before
	public void setUp() throws Exception {
		socket=new FakeSocket("bla".getBytes());
		player=new Player(socket, 10);
		player.setName("Jenő");
	}

	@After
	public void tearDown() throws Exception {
		socket.close();
	}	
	
	@Test
	public void testGetLastAction() throws InterruptedException {		
		long tempTime=System.currentTimeMillis();
		Thread.sleep(100);
		player.read();
		if(!(player.getLastAction()>tempTime))
			fail("lastAction nem aktualizálódik "+tempTime+" "+player.getLastAction());
	}

	@Test
	public void testRead(){
		assertEquals("bla", player.read());
	}
	
	@Test
	public void testGetId() {
		assertEquals(10, player.getId());
	}

	@Test
	public void testGetName() {
		assertEquals("Jenő", player.getName());
	}

	@Test
	public void testGetCardCount() {
		assertEquals(0, player.getCardCount());
		player.increaseCardCount(-1);
		assertEquals(0, player.getCardCount());
		player.increaseCardCount(0);
		assertEquals(0, player.getCardCount());
		player.decreaseCardCount();
		assertEquals(0, player.getCardCount());
		player.increaseCardCount(3);
		assertEquals(3, player.getCardCount());
		player.decreaseCardCount();
		assertEquals(2, player.getCardCount());
		player.increaseCardCount(3);
		assertEquals(5, player.getCardCount());
	}

}
