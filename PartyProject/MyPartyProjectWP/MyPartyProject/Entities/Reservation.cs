using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Entities
{
    public class Reservation : Entity
    {
        public string id { get; set; }
        public string id_client { get; set; }
        public string id_concert { get; set; }
        public string id_tarif { get; set; }
        public string scan { get; set; }
        public string image { get; set; }
        public string concertLabel { get; set; }
        public string tariffLabel { get; set; }

        public Reservation(string id, string id_client, string id_concert, string id_tarif, string scan, string image, string tariffLabel)
        {
            this.id = id;
            this.id_client = id_client;
            this.id_concert = id_concert;
            this.id_tarif = id_tarif;
            this.tariffLabel = tariffLabel;
            this.scan = scan;
            this.image = image;
        }
        public Reservation()
        {
        }

        
    }
}
