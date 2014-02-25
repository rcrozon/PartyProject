using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyParty.Entities
{
    public class Reservation : Entity
    {
        private int id;
        private int nbSeets;
        private String concert;
        private int id_concert;

        public Reservation(int id, int nbSeets, String concert, int id_concert)
        {
            this.id = id;
            this.nbSeets = nbSeets;
            this.concert = concert;
            this.id_concert = id_concert;
        }
    }
}
