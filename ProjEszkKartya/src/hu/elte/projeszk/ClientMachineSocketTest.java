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
	
	public void setIncomingMessage(String message) throws UnsupportedEncodingException{
		
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
	      
	      setIncomingMessage("S");
	
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
	      
	      setIncomingMessage("I,K3");
	      assertEquals("When inputstrewam is 'I,K3', output should be K ",null, clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
	  
	      setIncomingMessage("I,SH");
	      assertEquals("When inputstrewam is 'I,SH', output should be K ",null, clientMachineTester.switchAtInputCharacter(br)); // message sent and got a response
		  
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		
	}

	

}
