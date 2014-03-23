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
    public partial class ConcertsList : PhoneApplicationPage
    {
        public int loaded = 0;
        public ConcertsList()
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
            if (PhoneApplicationService.Current.State["ConcertMode"].Equals(0))
                pageTitle.Text = "All concerts";
            else if (PhoneApplicationService.Current.State["ConcertMode"].Equals(1))
                pageTitle.Text = "Next concerts";
            else 
                pageTitle.Text = "News";
            try
            {
                concertsListBox.ItemsSource = (List<Concert>)IsolatedStorageSettings.ApplicationSettings["concerts"];
            }
            catch (System.Collections.Generic.KeyNotFoundException e) {}
        }

        
        private void concertsListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (concertsListBox.SelectedItem == null)
                return;
            Concert currentConcert = (Concert)concertsListBox.SelectedItem;
            PhoneApplicationService.Current.State["Concert"] = currentConcert;
            concertsListBox.SelectedItem = null;
            NavigationService.Navigate(new Uri("/ConcertDetails.xaml", UriKind.Relative));
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Uri("/MainPage.xaml", UriKind.Relative));
        }

        private void Button_Click_update(object sender, System.Windows.RoutedEventArgs e)
        {
            progressBarUpdate.Visibility = System.Windows.Visibility.Visible;
            WebClient webClientConcert = new WebClient();
            webClientConcert.DownloadStringCompleted += concert_DownloadStringCompleted;
            webClientConcert.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllConcerts"));
            WebClient webClientTicket = new WebClient();
            webClientTicket.DownloadStringCompleted += ticket_DownloadStringCompleted;
            webClientTicket.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getReservationsByCLient/id:" + (string)(PhoneApplicationService.Current.State["idClient"])));
            WebClient webClientTariff = new WebClient();
            webClientTariff.DownloadStringCompleted += tariff_DownloadStringCompleted;
            webClientTariff.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllTariffs"));
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
            loaded++;
            if (loaded == 3)
            {
                progressBarUpdate.Visibility = System.Windows.Visibility.Collapsed;
            }
        }
        private void tariff_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {

                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Tariff> result = JsonConvert.DeserializeObject<List<Tariff>>(decryptedResultJSON);
                List<Tariff> tariffs = new List<Tariff>();
                for (int i = 0; i < result.Count; ++i)
                {
                    tariffs.Add(new Tariff
                    {
                        id = result[i].id,
                        label = result[i].label,
                        price = result[i].price,
                    });
                }
                IsolatedStorageSettings.ApplicationSettings["tariffs"] = tariffs;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded++;
            if (loaded == 3)
            {
                progressBarUpdate.Visibility = System.Windows.Visibility.Collapsed;
            }
        }
        private void concert_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Concert> result = JsonConvert.DeserializeObject<List<Concert>>(decryptedResultJSON);
                List<Concert> concerts = new List<Concert>();
                for (int i = 0; i < result.Count; ++i)
                {
                    concerts.Add(new Concert
                    {
                        id = result[i].id,
                        id_creator = result[i].id_creator,
                        id_tarif = result[i].id_tarif,
                        start_datetime = "Begin date : " + result[i].start_datetime,
                        end_datetime = "End date :" + result[i].end_datetime,
                        location = result[i].location,
                        image = "http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/img/Concerts/" + result[i].image,
                        nb_seats = result[i].nb_seats,
                        name_concert = result[i].name_concert,
                    });
                }
                IsolatedStorageSettings.ApplicationSettings["concerts"] = concerts;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded++;
            if (loaded == 3)
            {
                progressBarUpdate.Visibility = System.Windows.Visibility.Collapsed;
            }
        }
    }
}