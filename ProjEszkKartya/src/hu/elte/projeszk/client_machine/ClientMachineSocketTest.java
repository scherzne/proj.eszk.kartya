package hu.elte.projeszk.client_machine;

import static org.junit.Assert.assertEquals;


import org.mockito.Mockito;

import hu.elte.projeszk.Card;
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
	public void testingAddingCards()  {
	
		System.out.println("Kártyát kapunk:");
		 
		  encoding = "UTF-8";
		  
		  try {
			  		Socket socket = Mockito.mock(Socket.class);

			      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
		   
			      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
		     
			      
			      clientMachineTester = new ClientMachine("JatekosNev");
			      clientMachineTester.setSocket(socket);
		
			      
			  
	      
	     setSocketInputStreamMocking("A4,P4,K1,S2,FN");
	     
	 
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      assertEquals("hand size must be 4 after adding 4 card ", 4, clientMachineTester.handGetSize());
	        
	      assertEquals("first card of hand must be PIROS HAT", CardColor.PIROS, clientMachineTester.hand.get(0).getCardColor());
	      assertEquals("first card of hand must be PIROS HAT", CardValue.NEGY, clientMachineTester.hand.get(0).getCardValue());
	      
	      assertEquals("second card of hand must be KEK EGY", CardColor.KEK, clientMachineTester.hand.get(1).getCardColor());
	      assertEquals("second card of hand must be KEK EGY", CardValue.EGY, clientMachineTester.hand.get(1).getCardValue());
	     
	      assertEquals("third card of hand must be SARGA KETTO", CardColor.SARGA, clientMachineTester.hand.get(2).getCardColor());
	      assertEquals("third card of hand must be SARGA KETTO", CardValue.KETTO, clientMachineTester.hand.get(2).getCardValue());
	     
	      assertEquals("fourth card of hand must be FEKETE HUZZNEGYET", CardColor.FEKETE, clientMachineTester.hand.get(3).getCardColor());
	      assertEquals("fourth card of hand must be FEKETE HUZZNEGYET", CardValue.HUZZNEGYET, clientMachineTester.hand.get(3).getCardValue());
	     
	      setSocketInputStreamMocking("A2,Z6,KK");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      assertEquals("hand size must be 6 after adding 2 card ",6, clientMachineTester.handGetSize());
	      
	      assertEquals("fifth card of hand must be ZOLD HAT", CardColor.ZOLD, clientMachineTester.hand.get(4).getCardColor());
	      assertEquals("fifth card of hand must be ZOLD HAT", CardValue.HAT, clientMachineTester.hand.get(4).getCardValue());
	     
	      assertEquals("sixth card of hand must be KEK KIMARADSZ", CardColor.KEK, clientMachineTester.hand.get(5).getCardColor());
	      assertEquals("sixth card of hand must be KEK KIMARADSZ", CardValue.KIMARADSZ, clientMachineTester.hand.get(5).getCardValue());
	     
	     
	      
	      
	      } catch (IOException e) {
				// TODO Auto-generated catch block
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
	      
	  
	      setSocketInputStreamMocking("A5,K6,KK,Z9,PH,P0");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      
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
	      
	      
	      setSocketInputStreamMocking("A5,K6,KK,Z9,PH,P0");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      
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
	      
	  
	      setSocketInputStreamMocking("A5,K6,KK,Z9,PH,P0");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      setSocketInputStreamMocking("L,S7,I,S");
	      assertEquals("When inputstream is 'L,S7,I,S', the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setSocketInputStreamMocking("L,PH,I,P");
	      assertEquals("When inputstream is 'L,PH,I,P' the message should be PIROS NULLA ","A,P0", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,PH,I,P");
	      assertEquals("When inputstream is 'L,S6,I,P', the mmessage should be PIROS HUZZKETTOT ","A,PH", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,PK,I,P");
	      assertEquals("When inputstream is 'L,S6,I,P', (PIROS KIMARADSZ)  the mmessage should be ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	   
	      setSocketInputStreamMocking("A1,FS");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      setSocketInputStreamMocking("L,Z5,I,P");
	      assertEquals("When inputstream is 'L,Z5,I,P', (ZOLD OT)  the mmessage should be FEKETE SZINKERO ","A,FS", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      setSocketInputStreamMocking("A2,FS,FN");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      
	      setSocketInputStreamMocking("L,Z5,I,P");
	      assertEquals("When inputstream is 'L,Z5,I,P', (ZOLD OT)  the mmessage should be FEKETE SZINKERO ","A,FS", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      setSocketInputStreamMocking("L,Z5,I,P");
	      assertEquals("When inputstream is 'L,Z5,I,P', (ZOLD OT)  the mmessage should be FEKETE HUZZNEGYET ","A,FN", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}
	
	@Test
	public void testingCardChoosingCase4() {
	//van színkötelezettség, előző ember nem húzott
		
	    encoding = "UTF-8";
	    
	    try {
	      Socket socket = Mockito.mock(Socket.class);

	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
   
	      Mockito.when(socket.getInputStream()).thenReturn(inputStream);
     
	      
	      clientMachineTester = new ClientMachine("JatekosNev");
	      clientMachineTester.setSocket(socket);
	      
	   
	      setSocketInputStreamMocking("A5,K6,KK,Z9,PH,P0");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      
	      setSocketInputStreamMocking("L,FS,N,S");
	      assertEquals("When inputstream is 'L,FS,N,S', (FEKETE SZINKERO )the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setSocketInputStreamMocking("L,FS,N,S");
	      assertEquals("When inputstream is 'L,FS,N,S', the machine cant give back card ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      
	      setSocketInputStreamMocking("L,FS,N,P");
	      assertEquals("When inputstream is 'L,FS,N,P' the message should be PIROS NULLA ","A,P0", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	      setSocketInputStreamMocking("L,FS,N,P");
	      assertEquals("When inputstream is 'L,FS,N,P', the message should be PIROS HUZZKETTOT ","A,PH", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      setSocketInputStreamMocking("L,FN,N,P");
	      assertEquals("When inputstream is 'L,S6,N,P', (FEKETE HUZZNEGYET)  the mmessage should be ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		    
	      setSocketInputStreamMocking("A1,FS");
	      clientMachineTester.switchAtInputCharacter(br);
	      
	      
	      setSocketInputStreamMocking("L,FN,N,K");
	      assertEquals("When inputstream is 'L,FN,N,K',  the message should be null ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	    
	      setSocketInputStreamMocking("L,FS,N,S");
	      assertEquals("When inputstream is 'L,FS,N,S',  the message should be FEKETE SZINKERO ","A,FS", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      
	      setSocketInputStreamMocking("A1,FN");
	      clientMachineTester.switchAtInputCharacter(br);
	     
	      
	      setSocketInputStreamMocking("L,FN,N,K");
	      assertEquals("When inputstream is 'L,FN,N,K',   the mmessage should be FEKETE HUZZNEGYET ","A,FN", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      setSocketInputStreamMocking("L,FN,N,P");
	      assertEquals("When inputstream is 'L,FN,N,P',  the mmessage should be null ","N", clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}
	
}
