//Grace Keane
//Operating Systems
//Linking a client and server together
package ie.gmit.sw;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

public class Client 
{
	
	private Socket connection;
	private String message;
	private  Scanner console;
	private  String ipaddress;
	private  int portaddress;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Client()
	{
		console = new Scanner(System.in);
		
		System.out.println("Enter the IP Address of the server");
		ipaddress = console.nextLine();
		
		System.out.println("Enter the TCP Port");
		portaddress  = console.nextInt();
		
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
	
	
	public static void main(String[] args) 
	{
			Client temp = new Client();
			temp.clientapp();
	}

	public void clientapp()
	{
		
		try 
		{
			connection = new Socket(ipaddress,portaddress);
		
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client Side ready to communicate");
		
		
		    // Client app.
			
			do
			{
				do
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
				}while(!message.equalsIgnoreCase("1")&&!message.equalsIgnoreCase("2"));
				
				if(message.equals("1"))
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
					
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
					
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
				
					message = (String)in.readObject();
					System.out.println(message);
				
				}
			
				else if(message.equals("2"))
				{
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
					
					message = (String)in.readObject();
					System.out.println(message);
					
				}
			
				message = (String)in.readObject();
				System.out.println(message);
				message = console.next();
				sendMessage(message);
				
			}while(message.equalsIgnoreCase("Y"));
			
			out.close();
			in.close();
			connection.close();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
