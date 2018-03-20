import java.io.*;
import java.net.*;
import java.util.Date;

public class rccmnd
{
 public static void main(String argv[]) throws IOException
 {
	//Checking for Help
	 if(argv[0].equals("-h help"))
	  {
		  echo("Execution commands: java rccmnd server_name port_number command num_exec delay help");
         echo("where:" + "\n" + "a. 'java rccmnd' is for running the executable of client source code");
         echo("b. server_name is the domain name of the server");
         echo("c. port_number is the port number at which server is listening");
         echo("d. delay is the delay in consecutive execution");
         echo("e. num_exec is number of times the command to be executed");
         echo("f. command is the command to be executed by the server (Enter the command in quotes)");
         echo("g. help will print out how to run the executable");
         System.exit(-1);
	  }
	 
	 Socket ClientSocket = null;
	
	 //taking inputs from Command line
	 String server_name = argv[1];
     int port_number = Integer.parseInt(argv[3]);
     String command = argv[5];
     Integer ExecutionCount = Integer.parseInt(argv[7]);
     Integer TimeDelay = Integer.parseInt(argv[9]);
	 
	 try
	 {
		 ClientSocket = new Socket(server_name, port_number);
		 
		 while(true)
		 {
             
             StringBuilder sA = new StringBuilder();
             
             sA.append(command).append(",").append(ExecutionCount.toString()).append(",").append(TimeDelay.toString());
		     String CombinedString = sA.toString();
             
		     //Start the timer for RTT
		     long lStartTime = new Date().getTime(); 
		     
		     //Sending data to server
		     DataOutputStream outToServer = new DataOutputStream(ClientSocket.getOutputStream());
			 
		     outToServer.writeBytes(CombinedString + '\n');
		     
		     String FromServerSentence;
		    	 
		     //Data received from Server
			 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
		    	 
			 long lEndTime = new Date().getTime(); 
		     long difference = lEndTime - lStartTime;
		     
		     //Calculating RTT
		     echo("\n" + "Elapsed Time(ms) to execute the command: " + difference);
		    	 
		     echo("\n" + "Data Received From Server:" + "\n" + "\n");
			     
			    while ((FromServerSentence = inFromServer.readLine()) != null)
		        { 
		    		echo(FromServerSentence);
		    	}	
		     System.exit(-1);
		 }
	 }
	 catch(IOException e)
	 {
		 System.err.println("IOException has occurred" + e);
	 }
	 ClientSocket.close();
 	}

 //simple function to echo data to terminal
 public static void echo(String message)
 {
	 System.out.println(message);
 }
} 
