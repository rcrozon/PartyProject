using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Entities
{
    public class Ticket : Entity
    {
        public string id { get; set; }
        public string id_client { get; set; }
        public string id_concert { get; set; }
        public string id_tariff { get; set; }
        public string name_concert { get; set; }
        public string location { get; set; }
        public string end_datetime { get; set; }
        public string start_datetime { get; set; }
        public string concertLabel { get; set; }
        public string tariffLabel { get; set; }
        public string image { get; set; }
        public string nbTickets { get; set; }

        public Ticket(string id, string id_client, string id_concert, string id_tarif, string image)
        {
            this.id = id;
            this.id_client = id_client;
            this.id_concert = id_concert;
            this.image = image;
        }
        public Ticket()
        {
        }
        public static List<Ticket> getTicketsForOneConcert(List<Ticket> tickets, string id_concert) 
        {
            List<Ticket> ticketsByConcert = new List<Ticket>();
            foreach (Ticket ticket in tickets)
            {
                if (ticket.id_concert.Equals(id_concert))
                {
                    ticketsByConcert.Add(ticket);
                }
            }
            return ticketsByConcert;
        }
    }
}
