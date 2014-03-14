package scan;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
		RelativeLayout.LayoutParams llpMainLayout = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams llpLayout = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.WRAP_CONTENT);
		llpLayout.addRule(ALIGN_PARENT_TOP);
		this.linearLayout.setLayoutParams(llpLayout);
		this.setLayoutParams(llpMainLayout);  
	
		this.imgView = new ImageView(context);
		this.imgView.setId(1000);
		this.imgView.setBackgroundResource(R.drawable.qrcode_blue);
		this.imgView.setOnClickListener(this);
		LinearLayout.LayoutParams llpImg = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, 1f);
		llpImg.setMargins(0, 50, 0, 0);  
		llpImg.gravity = Gravity.CENTER;
		this.text = new TextView(context);
		this.text.setId(1001);
		this.text.setText("Click on the flashcode to scan a ticket");
		this.text.setTextColor(getResources().getColor(R.color.white));
		LinearLayout.LayoutParams llpTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.text.setGravity(Gravity.TOP | Gravity.CENTER);
		
		LayoutParams llpButtonTariff = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);  
		llpButtonTariff.addRule(ALIGN_PARENT_BOTTOM);
		this.buttonTariff = new Button(context);
		this.buttonTariff.setBackgroundResource(R.color.blue);
		this.buttonTariff.setVisibility(INVISIBLE);
		this.buttonTariff.setGravity(Gravity.CENTER);
		this.buttonTariff.setLayoutParams(llpButtonTariff);
		llpButtonTariff.setMargins(20, 700, 20, 0); 

		this.linearLayout.addView(this.imgView, llpImg);
		this.linearLayout.addView(this.text, llpTextView);
		this.addView(linearLayout);
		this.addView(this.buttonTariff);
		
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
