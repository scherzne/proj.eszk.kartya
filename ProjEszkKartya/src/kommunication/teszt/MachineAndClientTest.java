package kommunication.teszt;

import hu.elte.projeszk.client.ManualClient;
import hu.elte.projeszk.client_machine.ClientMachine;
import hu.elte.projeszk.server.CardServer;

public class MachineAndClientTest {
    
    public static void main(String[] args) {
        
final	String[] SimulationGamerName = {"GepiJatekos1","GepiJatekos2"};
		  
	
		 
		
        new Thread() {
            @Override
            public void run() {
                try {
                	  System.out.println("Indul a szerver!");
                    CardServer.main(null);
                } catch (java.io.IOException e) {
                    System.err.println("Szerver oldali hiba." +e);
                }
            }
        }.start();
		
		
		for (int i =0; i<2; i++){
		
			 new SimulateThread(i) {
			 
			 
			 
            @Override
            public void run() {
                try {    Thread.sleep(i*1000);
                          ClientMachine.main(new String[]{ SimulationGamerName[i]});
						 
					 
                } catch (java.io.IOException e) {
                    System.err.println("Kliens oldali hiba." +e);
                }catch (InterruptedException e){
					System.err.println("Kliens oldali hiba." +e);
				}
            }
        }.start();

		
		}
		
		 new Thread() {
	            @Override
	            public void run() {
	                try {
	                	  System.out.println("Indul emberi kliens!");
	                      ManualClient.main(null);
	                } catch (java.io.IOException e) {
	                    System.err.println("Szerver oldali hiba." +e);
	                }
	            }
	        }.start();		
   
   
   }
   
   	static class SimulateThread extends Thread {
		
		int i;
		
		SimulateThread(int i) {
			this.i = i;
				
		}
		
		
	}
    
}