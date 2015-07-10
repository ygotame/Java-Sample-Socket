package serveur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class socketServer {
	

//}

 
 
//public class MultiThreadedSocketServer {
 
    ServerSocket myServerSocket;
    boolean ServerOn = true;
 
 
    public socketServer() 
    { 
        try
        {
            // On crée le socket
            myServerSocket = new ServerSocket(1010); 
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not create server socket on port 1010. Quitting."); 
            System.exit(-1); 
        } 
 

        while(ServerOn) 
        {                        
            try
            { 
                // On accepte les connexion entrante
                Socket clientSocket = myServerSocket.accept(); 

                //On cree un "thread" pour gerer la connexion avec le client.
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start(); 
 
            } 
            catch(IOException ioe) 
            { 
                System.out.println("Exception encountered on accept. Ignoring. Stack Trace :"); 
                ioe.printStackTrace(); 
            } 
 
        }
 
        try
        { 
            myServerSocket.close(); 
            System.out.println("Server Stopped"); 
        } 
        catch(Exception ioe) 
        { 
            System.out.println("Problem stopping server socket"); 
            System.exit(-1); 
        } 
 
 
 
    } 
 
    public static void main (String[] args) 
    { 
        new socketServer();        
    } 
 
 
    class ClientServiceThread extends Thread 
    { 
        Socket myClientSocket;
        boolean m_bRunThread = true; 
 
        public ClientServiceThread() 
        { 
            super(); 
        } 
 
        ClientServiceThread(Socket s) 
        { 
            myClientSocket = s; 
 
        } 
 
        public void run() 
        {
            BufferedReader in = null; 
            PrintWriter out = null; 
 
            try
            {
                // On cree les buffers de lecture et d'ecriture
                in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream())); 
 

                // Tant que le thread roule, on repond au client
                while(m_bRunThread) 
                {     
                	//lecture de l'envoi du client
                    String clientCommand = in.readLine();
                    // Affichage  de l'adresse du client, de son port et du message envoyé
                    System.out.println("Client Info : " + myClientSocket.getInetAddress().getHostName() + ":" + myClientSocket.getPort() + " Message : " + clientCommand);
                    
                    //on met le message en majuscule
                    clientCommand = clientCommand.toUpperCase();
                    // On renvoit le texte en majuscule
                    out.println(clientCommand); 
                    out.flush(); 
                    
                } 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            } 
            finally
            { 
                // Clean up 
                try
                {
                    // À la fermeture on ferme le socket et les buffers
                    in.close(); 
                    out.close(); 
                    myClientSocket.close(); 
                    System.out.println("...Stopped"); 
                } 
                catch(IOException ioe) 
                { 
                    ioe.printStackTrace(); 
                } 
            } 
        } 
 
 
    } 
}
