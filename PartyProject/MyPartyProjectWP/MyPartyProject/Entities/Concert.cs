using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyParty.Entities
{
    public class Concert : Entity
    {
        public int id { get; set; }
        public String imgPath {get; set;}
        public String title {get; set;}
        public String beginDate {get; set;}
        public String endDate {get; set;}
        public String location {get; set;}
        public int nbSeets {get; set;}
        public int full {get; set;}

        public Concert(int id, String imgPath, String title, String begin, String end, String location, int nbSeets, int full)
        {
            this.id = id;
            this.imgPath = "Images/party1.jpg";
            this.title = title;
            this.beginDate = begin;
            this.endDate = end;
            this.location = location;
            this.nbSeets = nbSeets;
            this.full = full;
        }

        public Concert()
        {
            // TODO: Complete member initialization
        }
    }
}
