package com.jdw.netty.server.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PseudoAsynTimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8080;

		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {

			}
		}
		
		ServerSocket server = null;

		try {
			server = new ServerSocket(port);
			System.out.println("The  PseudoAsynTimeServer is start in port: " + port);
			Socket socket = null;
			
			TimeServerHandlerExecutePool singleExecutor=new TimeServerHandlerExecutePool(50, 10000);
			
			while (true) {
				socket = server.accept();
				singleExecutor.execute(new TimeServerHandler(socket));
			}
		} finally {
			if (server != null) {
				System.out.println("The TimeServer close.");
				server.close();
				server = null;
			}
		}
	}

}
