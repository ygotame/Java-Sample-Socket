package serveur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class socketServer {
	

    ServerSocket unServeurSocket;
    String QUITTER = "QUIT";
    boolean serveurOn = true;
     
    public static void main (String[] args) 
    { 
    	int portServeur;
    	Scanner entres = new Scanner(System.in);
		
		System.out.print("Entrez le port : ");
		portServeur = entres.nextInt();
    	
    	new socketServer(portServeur);
    	
    	entres.close();
    	
    	
    }
    
    public socketServer(int portServeur) 
    { 
        try
        {
            // On crée le socket
            unServeurSocket = new ServerSocket(portServeur); 
            System.out.println("Serveur en mode écoute sur le port : " + portServeur);
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Incapable de rouler le serveur sur le port " + portServeur + ". \n Veuillez essayer un autre port. \n Merci."); 
            System.exit(-1); 
        } 
 

        while(serveurOn) 
        {                        
            try
            { 
                // On accepte les connexion entrantes
                Socket clientSocket = unServeurSocket.accept(); 

                //On cree un "thread" pour gerer la connexion avec le client.
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start(); 
 
            } 
            catch(IOException ioe) 
            { 
                System.out.println("Exception rencontrée en acceptant le socket client. Stack Trace :"); 
                ioe.printStackTrace(); 
            } 
 
        }
 
        try
        { 
            unServeurSocket.close(); 
            System.out.println("Arrêt du serveu socketr"); 
        } 
        catch(Exception ioe) 
        { 
            System.out.println("Incapable d'arrêtrer le serveur"); 
            System.exit(-1); 
        } 
 
    } 
 
 
    class ClientServiceThread extends Thread 
    { 
        Socket unSocketClient;
        boolean threadClientActif = true; 
 
        public ClientServiceThread() 
        { 
            super(); 
        } 
 
        ClientServiceThread(Socket socket) 
        { 
            unSocketClient = socket; 
 
        } 
 
        public void run() 
        {
            BufferedReader in = null; 
            PrintWriter out = null; 
 
            try
            {
                // On cree les buffers de lecture et d'ecriture
                in = new BufferedReader(new InputStreamReader(unSocketClient.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(unSocketClient.getOutputStream())); 
 

                // Tant que le thread roule, on repond au client
                do
                {     
                	//lecture de l'envoi du client
                    String clientCommand = in.readLine();
                    // Affichage  du message recu, de l'adresse et le port du client 
                    System.out.println(clientCommand + " " + unSocketClient.getInetAddress().getHostAddress() + ":" + unSocketClient.getPort());
                    
                    //on met le message en majuscule
                    clientCommand = clientCommand.toUpperCase();
                    // On renvoit le texte en majuscule
                    out.println(clientCommand); 
                    out.flush();
                    if (clientCommand == QUITTER)
					 {
						threadClientActif = false;
					 }
                    
                } while (threadClientActif);
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            } 
            finally
            { 
                try
                {
                    // À la fermeture on ferme le socket et les buffers
                    in.close(); 
                    out.close(); 
                    unSocketClient.close(); 
                    System.out.println("Arrêt socket"); 
                } 
                catch(IOException ioe) 
                { 
                    ioe.printStackTrace(); 
                } 
            } 
        } 
 
    }
}
