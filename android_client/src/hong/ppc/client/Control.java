package hong.ppc.client;

import java.io.IOException;
import java.net.UnknownHostException;

import hong.ppc.client.ui.ControlActivity.BackStateHandler;
import android.os.Message;

public class Control extends Client {
	public static final int COM_START = 1;
	public static final int COM_STOP = 2;
	public static final int COM_PREVIOUS = 3;
	public static final int COM_NEXT = 4;
	
	private BackStateHandler handler; 
	
	
	public Control(BackStateHandler handler,String ip) {
		super(ip);
		this.handler = handler;
	}

	public void start() {
		new Thread() {
			@Override
			public void run() {
				int result = Client.CONNECT_FAIL;
				try {
					make(COM_START);
					result = Client.CONNECT_SUCC;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					Message msg = new Message();
					msg.what = BackStateHandler.WHAT;
					msg.arg1 = result;
					handler.sendMessage(msg);
				}
				super.run();
			}
		}.start();
	}

	public void stop() {
		new Thread() {
			@Override
			public void run() {
				int result = Client.CONNECT_FAIL;
				try {
					make(COM_STOP);
					result = Client.CONNECT_SUCC;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					Message msg = new Message();
					msg.what = BackStateHandler.WHAT;
					msg.arg1 = result;
					handler.sendMessage(msg);
				}
				super.run();
			}
		}.start();
	}

	public void previous() {
		new Thread() {
			@Override
			public void run() {
				int result = Client.CONNECT_FAIL;
				try {
					make(COM_PREVIOUS);
					result = Client.CONNECT_SUCC;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					Message msg = new Message();
					msg.what = BackStateHandler.WHAT;
					msg.arg1 = result;
					handler.sendMessage(msg);
				}
				super.run();
			}
		}.start();
	}

	public void next() {
		new Thread() {
			@Override
			public void run() {
				int result = Client.CONNECT_FAIL;
				try {
					make(COM_NEXT);
					result = Client.CONNECT_SUCC;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					Message msg = new Message();
					msg.what = BackStateHandler.WHAT;
					msg.arg1 = result;
					handler.sendMessage(msg);
				}
				super.run();
			}
		}.start();
	}
}
