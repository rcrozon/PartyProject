package scan;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myparty.R;

public class ScanLayout extends RelativeLayout implements OnClickListener{

	private Activity activity ;
	private TextView text; 
	private ImageView imgView; 
	private Button buttonTariff;
	private LinearLayout linearLayout ;
	
	public ScanLayout(Context context, Activity activity) {
		super(context);
		this.activity = activity; 
		this.linearLayout = new LinearLayout(context);
		this.linearLayout.setId(500);
		this.linearLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams llpLayout = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT);
		this.linearLayout.setLayoutParams(llpLayout);
		this.setLayoutParams(llpLayout);  
	
		this.imgView = new ImageView(context);
		this.imgView.setId(1000);
		this.imgView.setBackgroundResource(R.drawable.qrcode_blue);
		this.imgView.setOnClickListener(this);
		LayoutParams llpImg = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		llpImg.addRule(RelativeLayout.ABOVE, 1001);
		llpImg.addRule(this.CENTER_HORIZONTAL);
		llpImg.addRule(this.ALIGN_PARENT_TOP); 
		llpImg.setMargins(0, 50, 0, 0);  
		
		this.text = new TextView(context);
		this.text.setId(1001);
		this.text.setText("Bonjour!");
		this.text.setTextColor(getResources().getColor(R.color.white));
		LayoutParams llpTextView = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		llpTextView.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		llpTextView.addRule(RelativeLayout.CENTER_HORIZONTAL);
		llpTextView.setMargins(0, 500, 0, 0); 
		
		this.buttonTariff = new Button(context);
		this.buttonTariff.setBackgroundResource(R.color.blue);
		this.buttonTariff.setText("Student Tariff");
		LayoutParams llpButtonTariff = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);  
		llpLayout.addRule(RelativeLayout.BELOW, 500);
//		llpTextView.addRule(RelativeLayout.BELOW, 1001);
//		llpButtonTariff.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		llpButtonTariff.setMargins(20, 0, 20, 0); 
		
		this.linearLayout.addView(this.imgView, llpImg);
		this.linearLayout.addView(this.text, llpTextView);
		this.addView(linearLayout);
		this.addView(this.buttonTariff, llpButtonTariff);
		
		this.setBackgroundResource(R.drawable.list_border);
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
