package hong.ppc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private int port = 30102;
	private String ip;

	public static final int CONNECT_FAIL = 0;
	public static final int CONNECT_SUCC = 1;
	
	public Client(String ip){
		this.ip = ip;
	}

	public void make(int command) throws UnknownHostException, IOException {
		Socket s = new Socket(ip, port);
		new LinkThread(s, command).start();
	}
}

class LinkThread extends Thread {
	private Socket s = null;
	private PrintStream out = null;
	private BufferedReader in = null;
	private int command = -1;

	public LinkThread(Socket s, int command) {
		this.s = s;
		this.command = command;
		try {
			out = new PrintStream(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			out.println(command);
			out.flush();
			String str = in.readLine();
			if (str != null) {
				System.out.println(str);
			}
			if (command == -2) {
				System.out.println("finish this session！");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!s.isClosed()) {
					s.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}