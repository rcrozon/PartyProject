package entities;

import lists.Items;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myparty.R;

public class BluetoothDeviceItem extends LinearLayout implements Items {

	private BluetoothDevice device;
	private ProgressBar progressBar;
	private int progress;
	private Handler progressBarHandler = new Handler();
	private LinearLayout layoutDevicesData;

	public BluetoothDeviceItem(Context context, BluetoothDevice device) {
		super(context);

		this.setBackgroundResource(R.drawable.list_border);
		this.setOrientation(VERTICAL);
		this.layoutDevicesData = new LinearLayout(context);
		this.layoutDevicesData.setOrientation(HORIZONTAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(20, 0, 20, 0);
		layoutDevicesData.setLayoutParams(layoutParams);
		layoutParams.weight = 1;
		ImageView imgView = new ImageView(context);
		LayoutParams llp = new LayoutParams(400, 300, Gravity.CENTER_HORIZONTAL);
		layoutParams.weight = 4;
		imgView.setBackgroundResource(R.drawable.ic_action_refresh);
		imgView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
			}
		});
		imgView.setLayoutParams(llp);

		progressBar = new ProgressBar(context);
		progressBar.setProgress(0);
		progressBar.setMax(100);

		TextView deviceName = new TextView(context);
		deviceName.setText(device.getName());
		deviceName.setTextColor(getResources().getColor(R.color.white));

		this.layoutDevicesData.addView(deviceName);
		this.layoutDevicesData.addView(imgView);
		this.addView(this.layoutDevicesData);
		this.addView(progressBar);
		updateDevices();
	}

	public void updateDevices() {
		new Thread(new Runnable() {
			public void run() {
				while (progress < 100) {
					// process some tasks
					progress += 5;
					// your computer is too fast, sleep 1 second
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Update the progress bar
					progressBar.setProgress(progress);
				}
				// ok, file is downloaded,
				if (progress >= 100) {
					// sleep 2 seconds, so that you can see the 100%
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();

	}

	@Override
	public String toString() {
		return device.getName();
	}

	@Override
	public void setVisible(boolean visible) {
		this.setVisible(visible);
	}
}
