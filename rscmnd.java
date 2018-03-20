import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class rscmnd implements Runnable 
{
   Socket childSocket;
   
   rscmnd(Socket clientSocket) 
   {
      this.childSocket = clientSocket;
   }

   public static void main(String args[]) throws Exception 
   {
	   if(args[0].equals("-h help"))
		{
		   //Checking for Help command
		   echo("Execution commands: java rscmnd port_number help");
           echo("where:" + "\n" + "a. 'java rscmnd' is for running the executable of server source code");
           echo("b. 'port-number' is the port number at which the server will listen to for connections (use port number greater than 10000 to be in safer side)");
           echo("c. 'help' will print out how to run the executable");
           System.exit(-1);
		}
	   
	   //Taking the input argument 
	   int port_number = Integer.parseInt(args[1]);
	   	   
	   @SuppressWarnings("resource")
	   ServerSocket serverSocket = new ServerSocket(port_number);  
	       
       // Successfully created Server Socket. Await Connection
	   echo("Server socket created. Waiting for incoming data...");
	   
       while(true) 
       {                        
           try 
           { 
               // Accept incoming connections. 
               Socket sock = serverSocket.accept(); 

               // Start a Service thread 
               new Thread(new rscmnd(sock)).start(); 
           } 
           catch(IOException ioe) 
           { 
               System.err.println("Exception encountered on accept.Stack Trace:"); 
               ioe.printStackTrace(); 
           } 
       }
   }
   
   public void run() 
   {            
	  boolean ServerOn = true;
	  boolean RunThread = true;
      BufferedReader inFromClient = null; 
      DataOutputStream outToClient = null; 
      Process p;
      String s = null;
      
       try 
       {   
    	   // Print out details of this connection 
    	   echo("\n"+ "Accepted Client: " + childSocket.getInetAddress().getHostAddress()); 
    	   
       	   //creation of input and output buffer
           inFromClient = new BufferedReader(new InputStreamReader(childSocket.getInputStream())); 
           outToClient = new DataOutputStream(childSocket.getOutputStream()); 

           //Run in a loop until RunThread is set to false 
           while(RunThread) 
           {                    
               // read incoming stream 
               String dataFromClient = inFromClient.readLine(); 
               String[] stringArray = dataFromClient.split(",");
               
               int executionCount = Integer.parseInt(stringArray[1]);
			   int timeDelay = Integer.parseInt(stringArray[2]);
      		 
			   String timeStampServer = new SimpleDateFormat("E yyyy.MM.dd 'at' HH.mm.ss").format(new java.util.Date());
				
			   echo("\n" + "Current Time:"+timeStampServer);
			   echo("Source Client IP " + childSocket.getLocalAddress().getHostAddress());
			   echo("Command: " + stringArray[0]);
			   echo("Status: Connected" + "\n");
			   echo("Command received from Client executed:" + "\n" );

               if(!ServerOn) 
               { 
                   // Special command. Quit this thread 
                   echo("Server has already stopped"); 
                   outToClient.writeBytes("Server has already stopped");  
                   outToClient.flush(); 
                   RunThread = false;   
               } 
               else 
               {
               	for(int i=1; i<=executionCount; i++)
   				{
               		StringBuilder stringS = new StringBuilder();
               		
               		//Execute the command received 
           			p = Runtime.getRuntime().exec(stringArray[0]);
           			BufferedReader cmdbuffer = new BufferedReader(new InputStreamReader(p.getInputStream()));

          		 	String timeStamp = new SimpleDateFormat("E yyyy.MM.dd 'at' HH.mm.ss").format(new java.util.Date());
					stringS.append("Time Stamp: " + timeStamp).append('\n');
					
          		 	while ((s = cmdbuffer.readLine()) != null) 
               		{	
              		 		//echo the incoming command 
              		 		echo(s);
              		 		stringS.append(s).append("\n");
               		}
      
          		 	echo("\n");
   					stringS.append("\n");
   					
   				    //Sending data back to Client
   					String toSendToClient = new String();
   	               	toSendToClient = stringS.toString();
   	               	
   	    			outToClient.write(toSendToClient.getBytes());  
   	               	outToClient.flush();
   	                   
   	                stringS = new StringBuilder();
   	                
   					try 
   					{
   						Thread.sleep(timeDelay*1000);
   					} 
   					catch (InterruptedException e) 
   					{
   						// TODO Auto-generated catch block
   						e.printStackTrace();
   					} 
   				}
               }
              RunThread = false; 
           } 
       }
       catch(Exception e) 
       { 
           e.printStackTrace(); 
       }
       //Clean up and closing buffers and connection
       finally 
       { 
           try 
           {                    
               inFromClient.close(); 
               outToClient.close(); 
               childSocket.close(); 
               echo("Status : The Client is Closed"); 
           } 
           catch(IOException ioe) 
           { 
               ioe.printStackTrace(); 
           } 
       } 
   }
   //simple function to echo data to terminal
   public static void echo(String message)
   {
	   System.out.println(message);	
   }
}
