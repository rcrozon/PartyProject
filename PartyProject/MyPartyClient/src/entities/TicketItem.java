package entities;

import lists.Items;
import QRCode.Contents;
import QRCode.QRCodeEncoder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class TicketItem extends LinearLayout implements Items{ 
	
	ImageView imgFlashCode;

	public TicketItem(Context context, Ticket ticket, int idClient){
		super(context);
		imgFlashCode = new ImageView(context);
		
		createQRCode(ticket.getConcert(), ticket.getId(), ticket.getIdClient(), ticket.getIdTariff());
		//this.setBackgroundResource(R.drawable.list_border);
		//this.setOrientation(VERTICAL);
		
		//TextView concert = new TextView(context);
		//concert.setText(ticket.getConcertName());
		//concert.setTextColor(getResources().getColor(R.color.blue));
		LinearLayout.LayoutParams llp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		this.addView(imgFlashCode);
	}
	
	@Override
	public String toString(){
		return "";
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.setVisible(visible);
	}
	
	private void createQRCode(Concert concert, int idTicket, int idClient, int idTariff){
		String stringQRCode = idTicket + ";" + concert.getId() + ";" + idClient + ";" + idTariff;
		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(stringQRCode, 
														null, 
														Contents.Type.TEXT,
														BarcodeFormat.QR_CODE.toString(), 
														300);
		try {
			Bitmap bitmapQRCode = qrCodeEncoder.encodeAsBitmap();
			Drawable drawable = new BitmapDrawable(getResources(), bitmapQRCode);
			imgFlashCode.setBackground(drawable);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}
