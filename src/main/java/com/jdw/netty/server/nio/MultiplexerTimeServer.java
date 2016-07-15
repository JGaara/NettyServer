package com.jdw.netty.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

	private Selector selector;

	private ServerSocketChannel servChannel;

	private volatile boolean stop;

	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			SocketAddress address = new InetSocketAddress(port);
			System.out.println(address.toString());
			servChannel.socket().bind(address, 1024);
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port: " + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		try {
			selector.select(1000);
			Set<SelectionKey> selectedKeys=selector.selectedKeys();
			Iterator<SelectionKey> it=selectedKeys.iterator();
			SelectionKey key=null;
			while(it.hasNext()){
				key=it.next();
				it.remove();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doWrite(SocketChannel channel,String response) throws IOException{
		if(response!=null && response.trim().length()>0){
			byte[] bytes=response.getBytes();
			ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}
}
