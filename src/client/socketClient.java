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
		String envoi="bob\r\n";
		Socket socket; // Le socket de connexion
		//DataOutputStream  ecriture; // Le buffer d'écriture
		//DataInputStream  lecture; // Le buffer de lecture
		InputStreamReader ecriture; // Le buffer d'écriture
	    BufferedReader lecture;
		
		//boolean adresseValide = false;
		
		Scanner entres = new Scanner(System.in);
		
		//System.out.print("Entrez l'adresse");
		//adresseServeur = entres.nextLine();
		//System.out.println();
		//System.out.print("You entered string "+adresseServeur);
		adresseServeur = "localhost";
		portServeur = 1010;
		//System.out.println("Entrez le port");
	    //  portServeur = entres.nextInt();
	    //  System.out.println("You entered integer "+portServeur);
		
		
		// Si l'usagé n'a pas annulé, connecte au serveur
		if (adresseServeur != null) {
			try {
				
				socket = new Socket(adresseServeur, portServeur);
				ecriture = new InputStreamReader(socket.getInputStream());
				lecture = new BufferedReader(ecriture);
				
			    PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);
			    
			    System.out.println("Port local : " + socket.getLocalPort());
				
				 while (socket != null && ecriture != null && lecture != null)
				 {
					 System.out.print("Votre message : ");
					 envoi = entres.nextLine();
					 printwriter.println(envoi);
					 texte = lecture.readLine();
					 System.out.println(texte);
				 }
				 printwriter.println("quit");
				 printwriter.close();
				 ecriture.close();
			     lecture.close();
			     socket.close();
				 entres.close();
				
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
