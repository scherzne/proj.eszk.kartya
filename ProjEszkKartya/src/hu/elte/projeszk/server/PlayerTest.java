package hu.elte.projeszk.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {	
	private Socket socket;
	
	@Before
	public void setUp() throws Exception {
		socket=new FakeSocket("bla".getBytes());
	}

	@After
	public void tearDown() throws Exception {
		socket.close();
	}
	
	
	@Test
	public void testGetLastAction() {
		try {			
			Player player=new Player(socket, 0);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Nem jött létre a Player osztály");
		}
		
	}

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCardCount() {
		fail("Not yet implemented");
	}

}
