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
        public string name_concert { get; set; }
        public string location { get; set; }
        public string end_datetime { get; set; }
        public string start_datetime { get; set; }
        public string nb_seats_reserved { get; set; }
        public string image { get; set; }
        public string nbTickets { get; set; }
        public string id_tarif { get; set; }
        public string scan { get; set; }
        public string created { get; set; }
        public List<Ticket> tickets { get; set; }

        public Reservation(string id_client, string id_concert, List<Ticket> tickets)
        {
            this.id_client = id_client;
            this.id_concert = id_concert;
            this.tickets = tickets;
        }
        public Reservation()
        {
        }
        /*
        public static List<Reservation> sortReservationsByConcert(List<Ticket> reservations){
            List<Reservation> resByConcert = new List<Reservation>();
            string id_concert = reservations[0].id_concert;
            int cpt = 0;
            foreach (Ticket ticket in reservations)
            {
                if (ticket.id_concert == id_concert)
                    cpt++;
                else
                {
                    resByConcert.Add(new Reservation())
                    cpt = 1;
                    id_concert = ticket.id_concert;
                }
            }
            resByConcert.Add(id_concert, cpt);
            return resByConcert;
        }*/

        public static List<Reservation> getListReservationByConcert(List<Ticket> tickets, List<Concert> concerts)
        {
            List<Reservation> resByConcert = new List<Reservation>();
            string id_concert = tickets[0].id_concert;
            string id_client = tickets[0].id_client;
            int cpt = 0;
            foreach (Ticket ticket in tickets)
            {
                if (ticket.id_concert == id_concert)
                    cpt++;
                else
                {
                    resByConcert.Add(new Reservation
                    {
                        id_client = id_client,
                        id_concert = ticket.id_concert,
                        location = ticket.location,
                        name_concert = ticket.name_concert,
                        start_datetime = ticket.start_datetime,
                        end_datetime = ticket.end_datetime,
                        image = ticket.image,
                        nb_seats_reserved = cpt.ToString(),
                    });
                    cpt = 1;
                    id_concert = ticket.id_concert;
                }
            }
            Concert concert = Concert.getConcertFromId(concerts, id_concert);
            resByConcert.Add(new Reservation
            {
                id_client = id_client,
                id_concert = id_concert,
                location = concert.location,
                name_concert = concert.name_concert,
                start_datetime = concert.start_datetime,
                end_datetime = concert.end_datetime,
                image = concert.image,
                nb_seats_reserved = cpt.ToString(),
            });
            return resByConcert;
        }
    }
}
