using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace MyPartyProject.Entities
{
    public class Tariff : Entity
    {
        public string id { get; set; }
        public string label { get; set; }
        public string price { get; set; }

        public Tariff(string id, string label,	string price){
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
                if (tariff.id == id)
                {
                    label = tariff.label + " : " + tariff.price + "€"; 
                }
            }
            return label;
        }
    }
}
