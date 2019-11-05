//Grace Keane
//Operating Systems
package ie.gmit.sw;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
//test
public class server {

	public static void main(String[] args) {
		
		ServerSocket listener;
		int clientid=0;
		try 
		{
			 listener = new ServerSocket(10000,10);
			 
			 while(true)
			 {
				System.out.println("Main thread listening for incoming new connections");
				Socket newconnection = listener.accept();
				
				System.out.println("New connection received and spanning a thread");
				Connecthandler t = new Connecthandler(newconnection, clientid);
				clientid++;
				t.start();
			 }
			
		} 
		
		catch (IOException e) 
		{
			System.out.println("Socket not opened");
			e.printStackTrace();
		}
	}

}


class Connecthandler extends Thread
{

	Socket individualconnection;
	int socketid;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	int result;
	
	public Connecthandler(Socket s, int i)
	{
		individualconnection = s;
		socketid = i;
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public void run()
	{
		
		try 
		{
		
			out = new ObjectOutputStream(individualconnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(individualconnection.getInputStream());
			System.out.println("Connection"+ socketid+" from IP address "+individualconnection.getInetAddress());
		
		 
			//Commence
			do 
			{
			
				do
				{
					sendMessage("Please Enter 1 to Add or 2 to Square Root");
					message = (String)in.readObject();
				}while(!message.equals("1")&&!message.equals("2"));
			
				if(message.equals("1"))
				{
					result = 0;
					
					sendMessage("Num1?");
					message = (String)in.readObject();
					result += Integer.parseInt(message);
					
					sendMessage("Num2?");
					message = (String)in.readObject();
					result += Integer.parseInt(message);
					
					sendMessage("Num3?");
					message = (String)in.readObject();
					result += Integer.parseInt(message);
				
					sendMessage("The result of the addition is "+result);
				}
			
				else if(message.equals("2"))
				{
					sendMessage("Please enter the number for the square root");
					message = (String)in.readObject();
					int num = Integer.parseInt(message);
				    sendMessage("The result is: "+Math.sqrt(num));	
				}
			
			
				sendMessage("Y to repeat or N to terminate");
			
				message = (String)in.readObject();
			}while(message.equalsIgnoreCase("Y"));
		
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{
				out.close();
				in.close();
				individualconnection.close();
			}
			
	
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		
	}
	
}
