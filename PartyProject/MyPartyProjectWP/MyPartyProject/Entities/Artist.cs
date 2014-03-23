using System;
using System.Collections.Generic;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Entities
{
    public class Artist
    {
        public string id { get; set; }
        public string name { get; set; }


        public static string getArtistsFromConcert(string id_concert)
        {
            string result = "";
            try
            {
                List<AssocArtist> assocArtists = (List<AssocArtist>)IsolatedStorageSettings.ApplicationSettings["assocartists"];
                List<Artist> artists = (List<Artist>)IsolatedStorageSettings.ApplicationSettings["artists"];
                if (assocArtists != null)
                {
                    foreach (AssocArtist assoc in assocArtists)
                    {
                        if (assoc.id_concert.Equals(id_concert))
                        {
                            if (!result.Equals(""))
                                result += ", ";
                            Artist s = getArtistById(artists, assoc.id_artist);
                            if (s != null)
                                result += s.name;
                        }
                    }
                }
            }catch (KeyNotFoundException e) {
                string s = "pas bon";
            }
            return result;
        }

        public static Artist getArtistById(List<Artist> artists, string id_artist)
        {
            foreach (Artist artist in artists)
            {
                if (artist.id.Equals(id_artist))
                {
                    return artist;
                }
            } return null;

        }

    }
}
