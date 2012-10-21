package hong.ppc.server;

import hong.ppc.server.control.AbstractControl;
import hong.ppc.server.control.OpenOfficeControl;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private int port = 30102;

	public Server() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace(); 
		}
		AbstractControl control = new OpenOfficeControl(robot);
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);

			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();
			System.out.println("server is ready, " + "and runing at ip:" + ip
					+ ":" + port);

			while (true) {
				Socket s = ss.accept();
				new ServerThread(s, control).start();
			}
		} catch (BindException e) {
			System.out
					.println(String.format("port %s is already in use", port));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null)
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}

class ServerThread extends Thread {
	private Socket s = null;
	private BufferedReader read = null;
	private PrintStream print = null;
	private AbstractControl control = null;

	public ServerThread(Socket s, AbstractControl control) {
		System.out.println("get connect");
		this.s = s;
		this.control = control;
		try {
			read = new BufferedReader(new InputStreamReader(s.getInputStream()));
			print = new PrintStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String message = null;
			int command = -1;
			while ((message = read.readLine()) != null) {
				try {
					command = Integer.parseInt(message);
				} catch (Exception ex) {
					System.out.println("'" + message + "' not command");
					ex.printStackTrace();
					continue;
				}
				switch (command) {
				case AbstractControl.COM_START:
					control.start();
					break;
				case AbstractControl.COM_STOP:
					control.stop();
					break;
				case AbstractControl.COM_PREVIOUS:
					control.previous();
					break;
				case AbstractControl.COM_NEXT:
					control.next();
					break;
				}
				// back the execute command
				print.println("get command:" + command);
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