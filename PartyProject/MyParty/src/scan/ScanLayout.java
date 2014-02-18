package scan;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myparty.R;

public class ScanLayout extends RelativeLayout implements OnClickListener{

	private Button scan;
	private Activity activity ;
	private TextView text; 
	private ImageView imgView; 
	private Button buttonTariff;
	
	public ScanLayout(Context context, Activity activity) {
		super(context);
		this.activity = activity; 
		this.imgView = new ImageView(context);
		LayoutParams llpLayout = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT);  
		this.setLayoutParams(llpLayout); 
		LayoutParams llpImg = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		llpImg.addRule(this.CENTER_HORIZONTAL);
		llpImg.addRule(this.ALIGN_PARENT_TOP); 
		llpImg.setMargins(0, 50, 0, 0);  
		LayoutParams llpTextView = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		llpTextView.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		LayoutParams llpButtonTariff = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		llpButtonTariff.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		llpTextView.addRule(this.CENTER_HORIZONTAL);
		llpTextView.addRule(this.ALIGN_PARENT_BOTTOM);
		llpTextView.setMargins(0, 500, 0, 0); 
		llpButtonTariff.setMargins(20, 0, 20, 0); 
		this.imgView.setBackgroundResource(R.drawable.qrcode_blue);
		this.imgView.setOnClickListener(this);
		this.addView(this.imgView, llpImg);
		this.text = new TextView(context);
		this.buttonTariff = new Button(context);
		this.buttonTariff.setBackgroundResource(R.color.blue);
		this.text.setText("Bonjour!");
		this.text.setTextColor(getResources().getColor(R.color.white));
		this.addView(this.text, llpTextView);
//		this.addView(this.buttonTariff, llpButtonTariff); 
//		this.setBackgroundResource(R.drawable.list_border);
	}

	public void onClick (View view){
		IntentIntegrator integrator = new IntentIntegrator(activity);
		integrator.initiateScan();
	}
	
	public TextView getTextView(){
		return text;
	}

	public ImageView getImageView(){
		return imgView;
	}
	
	public Button getButtonTariff(){
		return buttonTariff;
	}
	
	
}
