package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class socketClient {

	public static void main(String[] args) throws IOException {
		String adresseServeur;
		int portServeur;
		String texte;
		String envoi="bob";
		Socket socket; // Le socket de connexion
		//DataOutputStream  ecriture; // Le buffer d'écriture
		//DataInputStream  lecture; // Le buffer de lecture
		OutputStreamWriter ecriture; // Le buffer d'écriture
	    BufferedReader lecture;
		
		//boolean adresseValide = false;
		
		//Scanner entres = new Scanner(System.in);
		
		//System.out.println("Entrez l'adresse");
		//adresseServeur = entres.nextLine();
		//System.out.println("You entered string "+adresseServeur);
		adresseServeur = "localhost";
		portServeur = 1010;
		//System.out.println("Entrez le port");
	    //  portServeur = entres.nextInt();
	    //  System.out.println("You entered integer "+portServeur);
		//entres.close();
		
		// Si l'usagé n'a pas annulé, connecte au serveur
		if (adresseServeur != null) {
			try {
				
				//socket = new Socket(AdresseServeur.getHost(), 
					//	AdresseServeur.getPort());
				socket = new Socket(InetAddress.getByName(adresseServeur), portServeur);
				//ecriture = new DataOutputStream(socket.getOutputStream());
				//lecture = new DataInputStream(socket.getInputStream());
				//ecriture = new PrintWriter(socket.getOutputStream(), true);
				ecriture = new OutputStreamWriter(socket.getOutputStream());
				lecture = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				 while (socket != null && ecriture != null && lecture != null)
				 {
					 System.out.println("envoi vers serveur");
					 ecriture.write(envoi);
					// ecriture.flush();
					 //trace(envoi);
					// trace(texte = lecture.readLine());
					 texte = lecture.readLine();
					 System.out.println(texte);
				
				 }
				
			} catch (ConnectException ex) {
				
				System.out.println("Erreur 1");
				//System.out.println(socket.getInetAddress() + " port : " + socket.getPort() );
				
				
				
			} catch (UnknownHostException ex) {
				
				System.out.println("Erreur");
				//System.out.println(socket.getInetAddress() + " port : " + socket.getPort() );
				
			}
			
		}
		
		
	}

	}
