using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Database
{
    public partial class DatabaseHandler
    {
        public DatabaseHandler()
        {
            /*WebClient client = new WebClient();
            client.DownloadStringCompleted += client_DownloadStringCompleted;
            client.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllClients"));*/
        }
        /*
         public List<Client> getClientFromJson(String json) {
    	List<Client> cl = new ArrayList<Client>();
    	
    	JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){
	    		
				JSONObject client = rep.getJSONObject(i);
				String infoStr = client.getString("Client");
				JSONObject infoClient = new JSONObject(infoStr);
				int id = Integer.parseInt(infoClient.getString(Tables.CLIENT_NAME_ID));
				String username = infoClient.getString(Tables.CLIENT_NAME_USERNAME);
				String mail = infoClient.getString(Tables.CLIENT_NAME_MAIL);
				/*TODO Crypter MDP
				//String password = infoClient.getString(Tables.CLIENT_NAME_PASSWORD);
				String firstName = infoClient.getString(Tables.CLIENT_NAME_FIRSTNAME);
				String lastName = infoClient.getString(Tables.CLIENT_NAME_LASTNAME);
				int admin = Integer.parseInt(infoClient.getString(Tables.CLIENT_NAME_ADMIN));
				String dateCreated = infoClient.getString(Tables.CLIENT_NAME_DATE_CREATE);
				Client clientObj = new Client(id, firstName, lastName, mail, username, "test", admin, dateCreated);
				cl.add(clientObj);			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return cl;
    }    	
         * 
        * List<string> resultats = new List<string>();
        JsonObject json = (JsonObject)JsonObject.Parse(e.Result);
        string nombreResultat = json["responseData"]["cursor"]["resultCount"];
        var listeResultat = 
                from resultat in (JsonArray)(json["responseData"]["results"])
                where ((string)resultat["content"]).IndexOf("tutoriel", StringComparison.CurrentCultureIgnoreCase) >= 0
                select resultat;

        foreach (var resultat in listeResultat)
        {
            resultats.Add(resultat["titleNoFormatting"]);
        }
         
        //"http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllClients"
        private void client_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                List<string> resultats = new List<string>();
                JsonObject json = (JsonObject)JsonObject.Parse(e.Result);
                string nombreResultat = json["Client"];
                System.Diagnostics.Debug.WriteLine("RESPONSE " + nombreResultat);
            }
        }/*/
    }
}
