package entities;

import java.util.ArrayList;

import lists.Items;
import QRCode.Contents;
import QRCode.QRCodeEncoder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myparty.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException; 

import databaseHandler.DatabaseHandler;

public class TicketItem extends RelativeLayout implements Items{ 
	
	private ImageView imgFlashCode;
	private TextView textStyle;
	private TextView textArtists;
	private TextView textEndDate;
	private TextView textBeginDate;
	private TextView textTitle;
	private TextView textTariff; 

	private int TEXT_SIZE = 15;
	private int PADDING_LAYOUT = 30;
	
	public TicketItem(Context context, TicketUnitaire ticket, int idClient){
		super(context);
		LayoutParams llpItem = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(llpItem);
		imgFlashCode = new ImageView(context);
		textStyle = new TextView(context);
		textArtists = new TextView(context);
		textBeginDate = new TextView(context);
		textEndDate = new TextView(context);
		textTitle = new TextView(context);
		textTariff = new TextView(context);
		textStyle.setTextColor(Color.WHITE);
		textArtists.setTextColor(Color.WHITE);
		textBeginDate.setTextColor(Color.WHITE);
		textEndDate.setTextColor(Color.WHITE);
		textTitle.setTextColor(Color.WHITE);
		textTariff.setTextColor(Color.WHITE);
        textArtists.setTextSize(TEXT_SIZE);
        textBeginDate.setTextSize(TEXT_SIZE);
        textEndDate.setTextSize(TEXT_SIZE);
        textTariff.setTextSize(TEXT_SIZE);
        textStyle.setTextSize(TEXT_SIZE);
        textTitle.setTextSize(TEXT_SIZE);
		createQRCode(ticket.getConcert(), ticket.getId(), ticket.getIdClient(), ticket.getIdTariff());
		
		Concert concert = DatabaseHandler.getConcertWithId(ticket.getIdConcert());
		ArrayList<String> artistsList = DatabaseHandler.getArtistsFromConcert(ticket.getIdConcert());
		ArrayList<String> stylesList = DatabaseHandler.getStylesFromConcert(ticket.getIdConcert());
		
		textArtists.setText("Artists : ");
        for(int i = 0; i < artistsList.size(); ++i){
        	textArtists.setText(textArtists.getText() + artistsList.get(i) + " ");
        }
        textStyle.setText("Styles : ");
        for(int i = 0; i < artistsList.size(); ++i){
        	textStyle.setText(textStyle.getText() + stylesList.get(i) + " ");
        }
        textBeginDate.setText("Begin date : " + concert.getBeginDate());
        textEndDate.setText("End date : "+ concert.getEndDate());
        textTitle.setText("Title : " + concert.getTitle());
        textTariff.setText("Tariff : " + DatabaseHandler.getLabelById(ticket.getIdTariff()));
        LinearLayout layoutTextViewInfos = new LinearLayout(context);
        layoutTextViewInfos.setPadding(PADDING_LAYOUT,  PADDING_LAYOUT,  PADDING_LAYOUT, PADDING_LAYOUT);
        //layoutTextViewInfos.setBackgroundColor(Color.BLACK);
        layoutTextViewInfos.setBackgroundResource(R.drawable.corners);
        layoutTextViewInfos.setOrientation(LinearLayout.VERTICAL);
        layoutTextViewInfos.addView(textTitle);
        layoutTextViewInfos.addView(textBeginDate);
        layoutTextViewInfos.addView(textEndDate);
        layoutTextViewInfos.addView(textArtists);
        layoutTextViewInfos.addView(textStyle);
        layoutTextViewInfos.addView(textTariff);
        
        RelativeLayout.LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llp.addRule(ALIGN_PARENT_BOTTOM);
        llp.setMargins(30, 0, 30, 60);
        RelativeLayout.LayoutParams llpImg = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llpImg.addRule(ALIGN_PARENT_TOP);
        
        this.addView(imgFlashCode, llpImg);
		this.addView(layoutTextViewInfos, llp);
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
