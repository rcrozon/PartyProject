using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyParty.Entities
{
    public class Concert : Entity
    {
        public string id { get; set; }
        public string start_datetime { get; set; }
        public string end_datetime { get; set; }
        public string location { get; set; }
        public string image { get; set; }
        public string id_tarif { get; set; }
        public string nb_seats { get; set; }
        public bool full { get; set; }
        public string id_creator { get; set; }
        public string name_concert { get; set; }
        public bool online { get; set; }

        public Concert(string id, bool online, string id_tarif, string id_creator, string name,string imgPath, string begin, string end, string location, string nbSeets, bool full)
        {
            this.id = id;
            this.id_tarif = id_tarif;
            this.id_creator = id_creator;
            this.image = imgPath;
            this.start_datetime = begin;
            this.end_datetime = end;
            this.location = location;
            this.name_concert = name;
            this.nb_seats = nbSeets;
            this.online = online;
            this.full = full;
        }
    }
}
