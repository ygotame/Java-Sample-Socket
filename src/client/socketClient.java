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
		InputStreamReader ecriture; // Le buffer d'écriture
	    BufferedReader lecture; // Le buffer de lecture

		
		Scanner entres = new Scanner(System.in);

		// Adresse et port du serveur a connecter
		adresseServeur = "localhost";
		portServeur = 1010;
		
		
		// Si l'usagé n'a pas annulé, connecte au serveur
		if (adresseServeur != null) {
			try {
				//creation du socket et des "buffer" de lecture et d'ecriture
				socket = new Socket(adresseServeur, portServeur);
				ecriture = new InputStreamReader(socket.getInputStream());
				lecture = new BufferedReader(ecriture);
				
			    PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);

				//affichage du port local
			    System.out.println("Port local : " + socket.getLocalPort());
				
				 while (socket != null && ecriture != null && lecture != null)
				 {
					 //le message a envoyer au serveur
					 System.out.print("Votre message : ");
					 envoi = entres.nextLine();
					 printwriter.println(envoi);
					 //on recoit et affiche la reponse du serveur
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
