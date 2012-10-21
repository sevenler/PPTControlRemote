package hong.ppc.client.ui;

import hong.ppc.client.Client;
import hong.ppc.client.Control;
import hong.ppc.client.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ControlActivity extends Activity {

	private BackStateHandler handler = new BackStateHandler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_activity);

		Intent intent = getIntent();
		String ip = intent.getStringExtra("ip");

		final Control control = new Control(handler, ip);

		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				control.start();
			}
		});
		Button stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				control.stop();
			}
		});
		Button previous = (Button) findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				control.previous();
			}
		});
		Button next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				control.next();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressLint("HandlerLeak")
	public class BackStateHandler extends Handler {
		public static final int WHAT = 10024;

		@Override
		public void handleMessage(Message msg) {
			int status = msg.arg1;
			int what = msg.what;
			if (what == WHAT) {
				switch (status) {
				case Client.CONNECT_FAIL:
					System.out.println("-----------CONNECT_FAIL---------");
					Toast.makeText(ControlActivity.this,
							R.string.alert_connect_failed, Toast.LENGTH_SHORT)
							.show();
					break;
				case Client.CONNECT_SUCC:
					System.out.println("-----------CONNECT_SUCC---------");
					break;
				}
			}
			super.handleMessage(msg);
		}
	};
}
