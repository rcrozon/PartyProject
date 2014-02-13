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
		LayoutParams llp0 = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT); 
		this.setLayoutParams(llp0);
		this.setBackgroundResource(R.drawable.list_border);
		LayoutParams llp = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				RelativeLayout.LayoutParams.WRAP_CONTENT); 
		llp.addRule(this.CENTER_HORIZONTAL);
		llp.addRule(this.ALIGN_PARENT_TOP);
		LayoutParams llp2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
											RelativeLayout.LayoutParams.WRAP_CONTENT); 
		llp2.addRule(this.CENTER_HORIZONTAL);
		llp2.addRule(this.ALIGN_PARENT_BOTTOM);
		this.imgView.setBackgroundResource(R.drawable.qrcode_red);
		this.imgView.setOnClickListener(this); 
		this.addView(this.imgView, llp); 
		this.text = new TextView(context);
		this.text.setText("J'aime quand on m'enduit d'huile!");
		this.addView(this.text, llp2);
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
