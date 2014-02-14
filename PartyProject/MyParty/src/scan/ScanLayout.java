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

	Button scan;
	Activity activity ;
	TextView text; 
	ImageView imgView; 
	
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
		
		llpTextView.addRule(this.CENTER_HORIZONTAL);
		llpTextView.addRule(this.ALIGN_PARENT_BOTTOM);
		llpTextView.setMargins(0, 500, 0, 0); 
		this.imgView.setBackgroundResource(R.drawable.qrcode_red);
		this.imgView.setOnClickListener(this);
		this.addView(this.imgView, llpImg);
		this.text = new TextView(context);
		this.text.setText("J'aime quand on m'enduit d'huile!");
		this.text.setTextColor(getResources().getColor(R.color.white));
		this.addView(this.text, llpTextView);
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
	
	
}
