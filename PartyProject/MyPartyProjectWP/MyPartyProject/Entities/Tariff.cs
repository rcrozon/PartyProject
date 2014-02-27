using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyParty.Entities
{
    public class Tariff : Entity
    {
        public int id { get; set; }
        public string label { get; set; }
        public string price { get; set; }

        public Tariff(int id, string label,	string price){
		    this.id = id;
		    this.label = label;
		    this.price = price;
	    }

        public Tariff()
        {
            // TODO: Complete member initialization
        }

        public static string getLabelTariffFromId(List<Tariff> tariffs, string id)
        {
            string label = "";
            foreach (Tariff tariff in tariffs)
            {
                if (tariff.id.Equals(id))
                {
                    label = tariff.label + "  " + tariff.price; 
                }
            }
            return label;
        }
    }
}
