import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class rccmnd
{
	public static void main(String args[]) throws IOException
	{
		//Checking for Help
		if(args[0].equals("-h help"))
		  {
			  echo("Execution commands: java rccmnd server_name port_number command num_exec delay help");
	          echo("where:" + "\n" + "a. 'java rccmnd' is for running the executable of client source code");
	          echo("b. server_name is the domain name of the server");
	          echo("c. port_number is the port number at which server is listening");
	          echo("d. command is the command to be executed by the server(Enter the command in quotes)");
	          echo("e. num_exec is number of times the command to be executed");
	          echo("f. delay is the delay in consecutive execution");
	          echo("g. help will print out how to run the executable");
	          System.exit(-1);
		  }
	  
	  //taking inputs from Command line
      String server_name = args[1];
      int port_number = Integer.parseInt(args[3]);
      String command = args[5];
      Integer ExecutionCount = Integer.parseInt(args[7]);
      Integer TimeDelay = Integer.parseInt(args[9]);

      boolean ClientOn = true;
      
      DatagramSocket socket = null;
      String s = null;
      
      try
      {
    	  socket = new DatagramSocket();
    	  InetAddress host = InetAddress.getByName(server_name);
                   
    	  while(ClientOn)
    	  {
              
              StringBuilder sA = new StringBuilder();
              
              sA.append(command).append(",").append(ExecutionCount.toString()).append(",").append(TimeDelay.toString());
              
              //Start the timer for RTT
              long lStartTime = new Date().getTime();
              
              //create and send a packet
              byte[] all = String.valueOf(sA).getBytes();
              DatagramPacket dp = new DatagramPacket(all , all.length , host , port_number);
              socket.send(dp);
              
              echo("Receiving from the Server:" + "\n");
              
              for(int i=1; i<= ExecutionCount; i++)
              {
            	  
            	  //buffer to receive incoming data
            	  byte[] inputbuffer = new byte[65536];
            	  DatagramPacket replyp = new DatagramPacket(inputbuffer, inputbuffer.length);
            	  socket.receive(replyp);
                  
            	  //Calculate RTT
            	  long lEndTime = new Date().getTime();
            	  long difference = lEndTime - lStartTime;
              
            	  byte[] data = replyp.getData();
            	  s = new String(data, 0, replyp.getLength());
                  
            	  echo("Elapsed Time(ms) to execute the command: " + difference);
              
            	  //echo the incoming data
            	  echo("Reply From Server:" + '\n' +s);
              }
              
              ClientOn = false;
              System.exit(-1);
    	   }
      }
      catch(IOException e)
      {
          System.err.println("IOException has occurred" + e);
      }
   }
 //simple function to echo data to terminal
   public static void echo(String message)
   {
       System.out.println(message);
   }
}
