package hu.elte.projeszk;

import static org.junit.Assert.assertEquals;


import org.mockito.Mockito;

import hu.elte.projeszk.Card.CardColor;
import hu.elte.projeszk.Card.CardValue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Random;

import org.junit.Test;

public class ClientMachineSocketTest {

	 ClientMachine clientMachineTester;
	 InputStream inputStream; //inputstreamre elhelyezzük a várt bejövő 
     BufferedReader br; 
     String inCommingMessage;
     String outGoingMessage;
	 String encoding;
	
	public void setSocketInputStreamMocking(String message) throws UnsupportedEncodingException{
		
		inCommingMessage = message;
		 br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inCommingMessage.getBytes(encoding))));
	     
		
	}
	
	@Test
	public void testingColorChoose() {
	// Szinválasztás tesztelése
		
	    encoding = "UTF-8";
	    
	    try {
	      Socket socket = Mockito.mock(Socket.class);

	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
   
	      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
     
	      
	      clientMachineTester = new ClientMachine("JatekosNev"){
	    	  
	    	  @Override
	    	  protected CardColor randomDeclareColor(){
	    		  
	    		  return CardColor.KEK;
	    		  
	    	  }
	    	  
	      };
	      clientMachineTester.setSocket(socket);
	      
	      setSocketInputStreamMocking("S");
	
	      assertEquals("When inputstrewam is 'S', outputstream should be K ","K", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
 
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}
	
	@Test
	public void testingInformation() {
	// Szinválasztás tesztelése
		
	    encoding = "UTF-8";
	    
	    try {
	      Socket socket = Mockito.mock(Socket.class);

	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
   
	      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
     
	      
	      clientMachineTester = new ClientMachine("JatekosNev");
	      clientMachineTester.setSocket(socket);
	      
	      setSocketInputStreamMocking("I,K3");
	      assertEquals("When inputstrewam is 'I,K3', output should be null ",null, clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setSocketInputStreamMocking("I,SH");
	      assertEquals("When inputstrewam is 'I,SH', output should be null ",null, clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}

	@Test
	public void testingCardChoosingCase1() {
	//nincs színkötelezettség, előző ember nem húzott
		
	    encoding = "UTF-8";
	    
	    try {
	      Socket socket = Mockito.mock(Socket.class);

	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
   
	      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
     
	      
	      clientMachineTester = new ClientMachine("JatekosNev");
	      clientMachineTester.setSocket(socket);
	      
	      clientMachineTester.addCardToHand( new Card(CardColor.KEK,CardValue.HAT));
	      clientMachineTester.addCardToHand( new Card(CardColor.KEK,CardValue.KIMARADSZ));
	      clientMachineTester.addCardToHand( new Card(CardColor.ZOLD,CardValue.KILENC));
	      clientMachineTester.addCardToHand( new Card(CardColor.PIROS,CardValue.HUZZKETTOT));
	      clientMachineTester.addCardToHand( new Card(CardColor.PIROS,CardValue.NULLA));
	      
	      setSocketInputStreamMocking("L,S7,N,F");
	      assertEquals("When inputstream is 'L,S7,N,F', the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setSocketInputStreamMocking("L,Z5,N,F");
	      assertEquals("When inputstream is 'L,Z5,N,F' the message should be ZOLD KILENC ","A,Z9", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,S6,N,F");
	      assertEquals("When inputstream is 'L,S6,N,F', the message should be KEK HAT ","A,K6", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,S6,N,F");
	      assertEquals("When inputstream is 'L,S6,N,F', for the second time the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,SH,N,F");
	      assertEquals("When inputstream is 'L,S6,N,F' the message should be PIROS HUZZKETTO ","A,PH", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,SK,N,F");
	      assertEquals("When inputstream is 'L,SK,N,F' the message should be KEK KIMARADSZ ","A,KK", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,F4,N,F");
	      assertEquals("When inputstream is 'L,SK,N,F' the message should be null ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	     
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}
	
	@Test
	public void testingCardChoosingCase2() {
	//nincs színkötelezettség, előző játékos húzott
	    encoding = "UTF-8";
	    
	    try {
	      Socket socket = Mockito.mock(Socket.class);

	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
   
	      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
     
	      
	      clientMachineTester = new ClientMachine("JatekosNev");
	      clientMachineTester.setSocket(socket);
	      
	      clientMachineTester.addCardToHand( new Card(CardColor.KEK,CardValue.HAT));
	      clientMachineTester.addCardToHand( new Card(CardColor.KEK,CardValue.KIMARADSZ));
	      clientMachineTester.addCardToHand( new Card(CardColor.ZOLD,CardValue.KILENC));
	      clientMachineTester.addCardToHand( new Card(CardColor.PIROS,CardValue.HUZZKETTOT));
	      clientMachineTester.addCardToHand( new Card(CardColor.PIROS,CardValue.NULLA));
	      
	      setSocketInputStreamMocking("L,S7,I,F");
	      assertEquals("When inputstream is 'L,S7,I,F', the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setSocketInputStreamMocking("L,Z5,I,F");
	      assertEquals("When inputstream is 'L,Z5,I,F' the message should be ZOLD KILENC ","A,Z9", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,S6,I,F");
	      assertEquals("When inputstream is 'L,S6,I,F', the message should be KEK HAT ","A,K6", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,S6,I,F");
	      assertEquals("When inputstream is 'L,S6,I,F', for the second time the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,SH,I,F");
	      assertEquals("When inputstream is 'L,S6,I,F' the message should be PIROS HUZZKETTO ","A,PH", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,SK,I,F");
	      assertEquals("When inputstream is 'L,SK,I,F' the message should be KEK KIMARADSZ ","A,KK", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,F4,I,F");
	      assertEquals("When inputstream is 'L,SK,I,F' the message should be null ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	     
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}

	@Test
	public void testingCardChoosingCase3() {
	//van színkötelezettség, előző ember húzott
		
	    encoding = "UTF-8";
	    
	    try {
	      Socket socket = Mockito.mock(Socket.class);

	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
   
	      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
     
	      
	      clientMachineTester = new ClientMachine("JatekosNev");
	      clientMachineTester.setSocket(socket);
	      
	      clientMachineTester.addCardToHand( new Card(CardColor.KEK,CardValue.HAT));
	      clientMachineTester.addCardToHand( new Card(CardColor.KEK,CardValue.KIMARADSZ));
	      clientMachineTester.addCardToHand( new Card(CardColor.ZOLD,CardValue.KILENC));
	      clientMachineTester.addCardToHand( new Card(CardColor.PIROS,CardValue.HUZZKETTOT));
	      clientMachineTester.addCardToHand( new Card(CardColor.PIROS,CardValue.NULLA));
	      
	      setSocketInputStreamMocking("L,S7,I,S");
	      assertEquals("When inputstream is 'L,S7,I,S', the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setSocketInputStreamMocking("L,PH,I,P");
	      assertEquals("When inputstream is 'L,PH,I,P' the message should be PIROS NULLA ","A,P0", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,PH,I,P");
	      assertEquals("When inputstream is 'L,S6,I,F', the mmessage should be PIROS HUZZKETTOT ","A,PH", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,PK,I,P");
	      assertEquals("When inputstream is 'L,S6,I,F', (PIROS KIMARADSZ)  the mmessage should be ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      clientMachineTester.addCardToHand( new Card(CardColor.FEKETE,CardValue.SZINKEREO));
	      setSocketInputStreamMocking("L,Z5,I,P");
	      assertEquals("When inputstream is 'L,Z5,I,F', (ZOLD OT)  the mmessage should be FEKETE SZINKERO ","A,FS", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	     
	      clientMachineTester.addCardToHand( new Card(CardColor.FEKETE,CardValue.SZINKEREO));
	      clientMachineTester.addCardToHand( new Card(CardColor.FEKETE,CardValue.HUZZNEGYET));
	      setSocketInputStreamMocking("L,Z5,I,P");
	      assertEquals("When inputstream is 'L,Z5,I,F', (ZOLD OT)  the mmessage should be FEKETE SZINKERO ","A,FS", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      setSocketInputStreamMocking("L,Z5,I,P");
	      assertEquals("When inputstream is 'L,Z5,I,F', (ZOLD OT)  the mmessage should be FEKETE HUZZNEGYET ","A,FN", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}
	
}
