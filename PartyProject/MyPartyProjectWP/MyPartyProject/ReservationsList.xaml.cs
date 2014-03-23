using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using MyPartyProject.Entities;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO.IsolatedStorage;
using System.Windows.Media.Imaging;
using MyPartyProject.Encrypt;

namespace MyPartyProject
{
    public partial class ReservationsList : PhoneApplicationPage
    {
        public ReservationsList()
        {
            InitializeComponent();
            if (PhoneApplicationService.Current.State["connected"].Equals(1))
            {
                Uri imageUriConnected = new Uri("/Images/ic_connected.png", UriKind.Relative);
                BitmapImage imageBitmapConnected = new BitmapImage(imageUriConnected);
                imgConnected.Source = imageBitmapConnected;
            }
            else
            {
                Uri imageUriNotConnected = new Uri("/Images/ic_not_connected.png", UriKind.Relative);
                BitmapImage imageBitmapNotConnected = new BitmapImage(imageUriNotConnected);
                imgConnected.Source = imageBitmapNotConnected;
            }
            try
            {
                List<Concert> concerts = (List<Concert>)IsolatedStorageSettings.ApplicationSettings["concerts"];
                List<Ticket> tickets = (List<Ticket>)IsolatedStorageSettings.ApplicationSettings["tickets"];
                foreach (Ticket ticket in tickets)
                {
                    Concert currentConcert = Concert.getConcertFromId(concerts, ticket.id_concert);
                    ticket.location = currentConcert.location;
                    ticket.name_concert = currentConcert.name_concert;
                    ticket.start_datetime = currentConcert.start_datetime;
                    ticket.end_datetime = currentConcert.end_datetime;
                    ticket.image = currentConcert.image;
                }
                List<Reservation> resByConcert = Reservation.getListReservationByConcert(tickets, concerts);
                reservationsListBox.ItemsSource = resByConcert;
            }
            catch (System.Collections.Generic.KeyNotFoundException e) {}
        }

        
        private void reservationsListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (reservationsListBox.SelectedItem == null)
                return;
            Reservation currentRes = (Reservation)reservationsListBox.SelectedItem;
            PhoneApplicationService.Current.State["reservation"] = currentRes;
            reservationsListBox.SelectedItem = null;
            NavigationService.Navigate(new Uri("/TicketPage.xaml", UriKind.Relative));
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Uri("/MainPage.xaml", UriKind.Relative));
        }

        private void Button_Click_update(object sender, System.Windows.RoutedEventArgs e)
        {
            WebClient webClientTicket = new WebClient();
            webClientTicket.DownloadStringCompleted += ticket_DownloadStringCompleted;
            webClientTicket.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getReservationsByCLient/id:" + (string)(PhoneApplicationService.Current.State["idClient"])));
            
        }

        private void ticket_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Ticket> result = JsonConvert.DeserializeObject<List<Ticket>>(decryptedResultJSON);
                List<Ticket> tickets = new List<Ticket>();
                for (int i = 0; i < result.Count; ++i)
                {
                    if (result[i].id_client.Equals((string)(PhoneApplicationService.Current.State["idClient"])))
                    {
                        string s3 = result[i].concertLabel;
                        tickets.Add(new Ticket
                        {
                            id = result[i].id,
                            id_client = result[i].id_client,
                            id_concert = result[i].id_concert,
                        });
                    }
                }
                IsolatedStorageSettings.ApplicationSettings["tickets"] = tickets;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
        }
    }

}