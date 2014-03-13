package databaseHandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.Tab;
import android.content.Context;
import android.util.Log;
import entities.Client;
import entities.Concert;

public class MyJsonParser {

	private Context context;

	public MyJsonParser(Context context) {
		this.context = context;
	}


	public Boolean reponseIsClient(String reponse){

		JSONArray rep;
		try {
			rep = new JSONArray(reponse);
		}
		catch (JSONException e) {
			Log.i("SERVER", "FAUX");
			return false;
		}
		Log.i("SERVER", "VRAI");
		return true;
	}

	public Boolean reponseIsJson(String reponse){
		JSONArray rep;
		try {
			rep = new JSONArray(reponse);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}

	public List<Client> getClientFromJson(String json) {
		List<Client> cl = new ArrayList<Client>();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoClient = rep.getJSONObject(i);
				//String infoStr = client.getString("Client");
				//JSONObject infoClient = new JSONObject(infoStr);
				int id = Integer.parseInt(infoClient.getString(Tables.CLIENT_NAME_ID));
				String username = infoClient.getString(Tables.CLIENT_NAME_USERNAME);
				String mail = infoClient.getString(Tables.CLIENT_NAME_MAIL);
				/*TODO Crypter MDP*/
				String password = infoClient.getString(Tables.CLIENT_NAME_PASSWORD);
				String firstName = infoClient.getString(Tables.CLIENT_NAME_FIRSTNAME);
				String lastName = infoClient.getString(Tables.CLIENT_NAME_LASTNAME);
				int admin = Integer.parseInt(infoClient.getString(Tables.CLIENT_NAME_ADMIN));

				/*	if (admin == 1){
					DatabaseHandler dataBase = new DatabaseHandler(context);
					dataBase.open();
					Client c = dataBase.getClientWithId(id);
					if (c != null){
						password= c.getPassword();
					}
					else{
						password= "errorTEst";
					}

				}*/

				String dateCreated = infoClient.getString(Tables.CLIENT_NAME_DATE_CREATE);
				Client clientObj = new Client(id, firstName, lastName, mail, username, password, admin, dateCreated);
				Log.i("ADMIN", "INFO: "+clientObj.testToString());
				cl.add(clientObj);			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cl=null;

		}
		return cl;
	}    	


	public List<Concert> getConcertFromJson(String json) {
		List<Concert> cl = new ArrayList<Concert>();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoConcert = rep.getJSONObject(i);
				//String infoStr = concert.getString("Concert");
				//JSONObject infoConcert = new JSONObject(infoStr);
				int id = Integer.parseInt(infoConcert.getString(Tables.CONCERT_NAME_ID));
				String begin = infoConcert.getString(Tables.CONCERT_NAME_START_DATE);
				String end = infoConcert.getString(Tables.CONCERT_NAME_END_DATE);
				String location = infoConcert.getString(Tables.CONCERT_NAME_LOCATION);
				String imgPath = infoConcert.getString(Tables.CONCERT_NAME_IMAGE);
				int nbSeets = Integer.parseInt(infoConcert.getString(Tables.CONCERT_NAME_NB_SEAT));
				String fullBool = infoConcert.getString(Tables.CONCERT_NAME_FULL);
				int full;
				if (fullBool.equalsIgnoreCase("false")){
					full = 0;
				}
				else{
					full = 1;
				}
				int idCreator = Integer.parseInt(infoConcert.getString(Tables.CONCERT_NAME_ID_CREATOR));
				int idTarif = Integer.parseInt(infoConcert.getString(Tables.CONCERT_NAME_ID_TARIF));
				String creationDate = infoConcert.getString(Tables.CONCERT_NAME_CREATED);
				String title = infoConcert.getString(Tables.CONCERT_NAME_TITLE_CONCERT);
				String onlineBool = infoConcert.getString(Tables.CONCERT_NAME_ONLINE);
				int online;
				if (onlineBool.equalsIgnoreCase("false")){
					online = 0;
				}
				else{
					online = 1;
				}
				Concert concertObj = new Concert(id, imgPath, title, begin, end, creationDate, location, nbSeets, full,idTarif, idCreator, online);
				cl.add(concertObj);			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cl=null;
		}
		return cl;
	}   

	public Boolean getReservationAndInsert(String json) {
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoReservation= rep.getJSONObject(i);
				//String infoStr = reservation.getString("Reservation");
				//JSONObject infoReservation = new JSONObject(infoStr);
				int id = Integer.parseInt(infoReservation.getString(Tables.RES_NAME_ID));
				int id_concert = Integer.parseInt(infoReservation.getString(Tables.RES_NAME_ID_CONCERT));
				int id_client = Integer.parseInt(infoReservation.getString(Tables.RES_NAME_ID_CLIENT));
				int id_tarif = Integer.parseInt(infoReservation.getString(Tables.RES_NAME_ID_TARIF));
				int scan = Integer.parseInt(infoReservation.getString(Tables.RES_NAME_SCAN));
				dataBase.insertRes(id, id_concert, id_client, id_tarif, scan);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean getAssocTariffsAndInsert(String json) {
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoAssocTarrif = rep.getJSONObject(i);
				int id = Integer.parseInt(infoAssocTarrif.getString(Tables.ASSOC_TARIFF_NAME_ID));
				int id_concert = Integer.parseInt(infoAssocTarrif.getString(Tables.ASSOC_TARIFF_NAME_ID_CONCERT));
				int id_tariff = Integer.parseInt(infoAssocTarrif.getString(Tables.ASSOC_TARIFF_NAME_ID_TARIFF));
				Log.i("ASSOC", "id :" +id+"idtarrif :"+id_tariff+"id_concert :"+id_concert);
				dataBase.insertAssocTariff(id, id_tariff, id_concert);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean getAssocArtistsAndInsert(String json) {
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoAssocArtist = rep.getJSONObject(i);
				int id = Integer.parseInt(infoAssocArtist.getString(Tables.ASSOC_ARTIST_NAME_ID));
				int id_concert = Integer.parseInt(infoAssocArtist.getString(Tables.ASSOC_ARTIST_NAME_ID_CONCERT));
				int id_artist = Integer.parseInt(infoAssocArtist.getString(Tables.ASSOC_ARTIST_NAME_ID_ARTISTS));
				Log.i("ASSOC", "id :" +id+"idartist:"+id_artist+"id_concert :"+id_concert);
				dataBase.insertAssocArtist(id, id_artist, id_concert);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean getAssocStylesAndInsert(String json) {
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoAssocStyles = rep.getJSONObject(i);
				int id = Integer.parseInt(infoAssocStyles.getString(Tables.ASSOC_STYLES_NAME_ID));
				int id_style = Integer.parseInt(infoAssocStyles.getString(Tables.ASSOC_STYLES_NAME_ID_STYLES));
				int id_concert = Integer.parseInt(infoAssocStyles.getString(Tables.ASSOC_STYLES_NAME_ID_CONCERT));
				Log.i("ASSOC", "id :" +id+"idstyle :"+id_style+"id_concert :"+id_concert);
				dataBase.insertAssocStyle(id, id_style, id_concert);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	} 

	public Boolean getStylesAndInsert(String json) {
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoStyles = rep.getJSONObject(i);
				int id = Integer.parseInt(infoStyles.getString(Tables.STYLE_NAME_ID));
				String name = infoStyles.getString(Tables.STYLE_NAME_STYLE_NAME);
				Log.i("ASSOC", "idStyle :" +id+"Name :"+name);
				dataBase.insertStyle(id, name);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	} 

	public Boolean getArtistsAndInsert(String json) {
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoArtists = rep.getJSONObject(i);
				int id = Integer.parseInt(infoArtists.getString(Tables.ARTIST_NAME_ID));
				String name = infoArtists.getString(Tables.ARTIST_NAME_ARTIST_NAME);
				Log.i("ASSOC", "idArtist :" +id+"Name :"+name);
				dataBase.insertArtist(id, name);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	} 

	public Boolean getTariffsAndInsert(String json){
		DatabaseHandler dataBase = new DatabaseHandler(context);
		dataBase.open();

		JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){

				JSONObject infoTarrif = rep.getJSONObject(i);
				int id = Integer.parseInt(infoTarrif.getString(Tables.TARIFF_NAME_ID));
				String label = infoTarrif.getString(Tables.TARIFF_NAME_LABEL);
				Double price = infoTarrif.getDouble(Tables.TARIFF_NAME_PRICE);
				dataBase.insertTariff(id, label, price);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}



}