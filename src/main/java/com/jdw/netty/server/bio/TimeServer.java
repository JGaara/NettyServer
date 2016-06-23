package com.jdw.netty.server.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: TimeServer
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiadawei jiadawei@zehin.com.cn
 * @date 2016年6月23日 下午5:16:13
 */
public class TimeServer {

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
			System.out.println("The TimeServer is start in port: " + port);
			Socket socket = null;
			while (true) {
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
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
