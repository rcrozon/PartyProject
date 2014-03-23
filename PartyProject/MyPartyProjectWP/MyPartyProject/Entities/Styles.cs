using System;
using System.Collections.Generic;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Entities
{
    public class Styles
    {
        public string id { get; set; }
        public string name { get; set; }

        public static string getStylesFromConcert(string id_concert){
            string result = "";
            try{
                List<AssocStyle> assocStyles = (List<AssocStyle>)IsolatedStorageSettings.ApplicationSettings["assocstyles"];
                List<Styles> styles = (List<Styles>)IsolatedStorageSettings.ApplicationSettings["styles"];
                if (assocStyles != null)
                {
                    foreach (AssocStyle assoc in assocStyles)
                    {
                        if (assoc.id_concert.Equals(id_concert))
                        {
                            if (!result.Equals(""))
                                result += ", ";
                            Styles s = getStyleById(styles, assoc.id_style);
                            if(s != null)
                                result += s.name;
                        } 
                    }
                }
            }catch (KeyNotFoundException e) { }
            return result;
        }

        public static Styles getStyleById(List<Styles> styles, string id_style)
        {
            foreach (Styles style in styles)
            {
                if (style.id.Equals(id_style))
                {
                    return style;
                }
            } return null;

        }
    }

}
