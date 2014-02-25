using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyParty.Entities
{
    public class Ticket : Entity
    {
        public int id { get; set; }
        public int nbSeets { get; set; }
        public Concert concert { get; set; }

        public Ticket(int id, int nbSeets, Concert concert)
        {
            this.id = id;
            this.nbSeets = nbSeets;
            this.concert = concert;
        }
        public Ticket()
        {
            // TODO: Complete member initialization
        }
    }
}
