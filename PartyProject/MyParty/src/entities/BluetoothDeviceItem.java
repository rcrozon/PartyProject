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
import android.view.View.OnClickListener;
import bluetooth.BluetoothClient;
import bluetooth.BluetoothServer;

import com.example.myparty.R;

public class BluetoothDeviceItem extends LinearLayout implements Items {

	private BluetoothDevice device;
	private ProgressBar progressBar;
	private int progress = 0;
	private Handler progressBarHandler = new Handler();
	private LinearLayout layoutDevicesData;

	public BluetoothDeviceItem(final Context context, final BluetoothDevice device) {
		super(context);

		this.setBackgroundResource(R.drawable.list_border);
		this.setOrientation(VERTICAL);
		this.layoutDevicesData = new LinearLayout(context);
		this.layoutDevicesData.setOrientation(HORIZONTAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams2.setMargins(0, 0, 0, 0);
		this.setLayoutParams(layoutParams);
		layoutDevicesData.setLayoutParams(layoutParams);
		
		LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsText.weight = 1;
		layoutParams.weight = 4;
		ImageView imgView = new ImageView(context);
		LayoutParams llp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
		llp.weight = 1; 
		imgView.setBackgroundResource(R.drawable.ic_action_refresh);
		imgView.setLayoutParams(llp);

		progressBar = new ProgressBar(context, null,android.R.attr.progressBarStyleHorizontal);
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(INVISIBLE);

		TextView deviceName = new TextView(context);
		deviceName.setText(device.getName());
		deviceName.setTextColor(getResources().getColor(R.color.white));

		this.layoutDevicesData.addView(deviceName, layoutParamsText);
		this.layoutDevicesData.addView(imgView);
		this.addView(this.layoutDevicesData);
		this.addView(progressBar); 

		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				BluetoothClient client = new BluetoothClient(device, context);
				//client.start();
			}
		}); 
	} 

	public void updateDevices() {
		progressBar.setVisibility(VISIBLE);
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
