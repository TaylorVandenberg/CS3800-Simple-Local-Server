import java.net.*;
import java.net.ServerSocket;
import java.net.Socket; 
import java.io.*; 
import java.text.*;
import java.time.*;
import java.util.*;

public class Server 
{ 	
	public static void main(String args[])  throws Exception {
	{ 
		ServerSocket server = new ServerSocket(5000);
		System.out.println("Listening for connection on prot 5000...");
		while (true){
			Socket client = server.accept();

			//print the request when you try to load the page
			InputStreamReader stream = new InputStreamReader(client.getInputStream());
			BufferedReader reader = new BufferedReader(stream);
			String currentLine = reader.readLine();
			while(!currentLine.isEmpty()){
				System.out.println(currentLine);
				currentLine = reader.readLine();
			}

			//create an actual date response

			Date currentDate = new Date();

			DateFormat pdtDateFormat = DateFormat.getDateTimeInstance();
			String pdtDateTimeString = pdtDateFormat.format(currentDate);

			DateFormat estDateFormat = DateFormat.getDateTimeInstance();
			estDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
			String estDateTimeString = estDateFormat.format(currentDate);

			DateFormat gmtDateFormat = DateFormat.getDateTimeInstance();
			gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String gmtDateTimeString = gmtDateFormat.format(currentDate);

			String completeResponse = "<html><head><title>Current Date and Time</title></head><body>"
        			+ "<h1>Date/Times</h1>"
        			+ "<h1>PDT: " + pdtDateTimeString + "</h1>"
        			+ "<h1>EST: " + estDateTimeString + "</h1>"
        			+ "<h1>GMT: " + gmtDateTimeString + "</h1>"
        			+ "</body></html>";

			// Send the HTML response to the client
			String httpResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + completeResponse.length() + "\r\n\r\n" + completeResponse;
			client.getOutputStream().write(httpResponse.getBytes("UTF-8"));

		}
	} 
} 
}
