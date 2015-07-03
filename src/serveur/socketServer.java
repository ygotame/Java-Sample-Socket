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
            myServerSocket = new ServerSocket(1010); 
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not create server socket on port 1010. Quitting."); 
            System.exit(-1); 
        } 
 
        // Successfully created Server Socket. Now wait for connections. 
        while(ServerOn) 
        {                        
            try
            { 
                // Accept incoming connections. 
                Socket clientSocket = myServerSocket.accept(); 
 
                // accept() will block until a client connects to the server. 
                // If execution reaches this point, then it means that a client 
                // socket has been accepted. 
 
                // For each client, we will start a service thread to 
                // service the client requests. This is to demonstrate a 
                // Multi-Threaded server. Starting a thread also lets our 
                // MultiThreadedSocketServer accept multiple connections simultaneously. 
 
                // Start a Service thread 
 
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
            // Obtain the input stream and the output stream for the socket 
            // A good practice is to encapsulate them with a BufferedReader 
            // and a PrintWriter as shown below. 
            BufferedReader in = null; 
            PrintWriter out = null; 
 
            try
            {                                
                in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream())); 
 
                // At this point, we can read for input and reply with appropriate output. 
 
                // Run in a loop until m_bRunThread is set to false 
            
                while(m_bRunThread) 
                {     
                	//System.out.println("apres while");
                    // read incoming stream 
                    String clientCommand = in.readLine(); 
                    System.out.println("Client Info : " + myClientSocket.getInetAddress().getHostName() + ":" + myClientSocket.getPort() + " Message : " + clientCommand);
                    
                    //envoyé au serveur
                    clientCommand = clientCommand.toUpperCase();
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
