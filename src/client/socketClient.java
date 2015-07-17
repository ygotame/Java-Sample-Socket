package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class socketClient {

	public static void main(String[] args) throws IOException {
		String QUITTER = "QUIT";
		String adresseServeur;
		int portServeur;
		String reponse; // Réponse du serveur
		String envoi; // Message à envoyé au serveur
		Socket socket; // Le socket de connexion
		InputStreamReader ecriture; // Le buffer d'écriture
	    BufferedReader lecture; // Le buffer de lecture

		Scanner entres = new Scanner(System.in);
		
		System.out.print("Entrez l'adresse : ");
		adresseServeur = entres.nextLine();
		System.out.print("Entrez le port : ");
		portServeur = entres.nextInt();
					
		
		// Si l'adresse est bien valide et non-null, on connecte au serveur
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
					 envoi = entres.next();
					 printwriter.println(envoi);
					 //on recoit et affiche la reponse du serveur
					 reponse = lecture.readLine();
					 System.out.println("Réponse serveur : " + reponse);
					 if (reponse.equals(QUITTER))
					 {
						 ecriture.close();
					     lecture.close();
					 }
					 
				 }
				 printwriter.close();
			     socket.close();
				 entres.close();
				
			} catch (ConnectException ex) {
				
				System.out.println("Erreur de création de socket");
				System.exit(-1);	
				
			}
			
		}
		
		
	}

	}
