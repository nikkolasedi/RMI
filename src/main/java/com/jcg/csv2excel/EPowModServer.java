package com.jcg.csv2excel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

//import fileStreamServer.FileStreamImplementation;



public class EPowModServer {
	static int portnumber;

	public static void main(String[] args) throws IOException {
		
		System.out.println("Enter Port Number :");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferedReader.readLine();
        portnumber = Integer.parseInt(input);
      
		try {
			
			Registry reg = LocateRegistry.createRegistry(portnumber);//Creates and exports a Registry instance on the local host that accepts requests 
			//on the specified port.
			
			EPowModImplementation imp =  new EPowModImplementation("config/bus.csv");
			//reg.rebind("myepowmod", c );
			reg.bind("remoteObject", imp);
			
			System.out.println("server is ready...");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}