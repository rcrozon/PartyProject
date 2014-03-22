using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using MyPartyProject.Resources;
using MyPartyProject.Entities;
using System.IO.IsolatedStorage;
using Newtonsoft.Json;
using System.Windows.Media.Imaging;
using System.Diagnostics;
using System.IO;

using MyPartyProject.Encrypt;
using System.Text;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.Serialization.Json;

namespace MyPartyProject
{
    public partial class MainPage : PhoneApplicationPage
    {
        
        private int loaded = 0;
        private BitmapImage imageBitmapConnected;
        private BitmapImage imageBitmapDisconnected;
        private string idClient = null;
        private string _pwd = "";
        private string _login = "";
        // Constructor
        public MainPage()
        {
            InitializeComponent();
            invalidLogin.Visibility = Visibility.Collapsed;
            PhoneApplicationService.Current.State["connected"] = 0;
            try
            {
                if (IsolatedStorageSettings.ApplicationSettings["login"] != null)
                {
                    login.Text = (string)IsolatedStorageSettings.ApplicationSettings["login"];
                }
                if (IsolatedStorageSettings.ApplicationSettings["pwd"] != null)
                {
                    pwd.Password = (string)IsolatedStorageSettings.ApplicationSettings["pwd"];
                }
            }
            catch (System.Collections.Generic.KeyNotFoundException e) { }
            Uri imageUri = new Uri("/Images/ic_connected", UriKind.Relative);
            imageBitmapConnected = new BitmapImage(imageUri);
            Uri imageUri2 = new Uri("/Images/ic_not_connected", UriKind.Relative);
            imageBitmapDisconnected = new BitmapImage(imageUri);
        }

        private void updateDatabase(string idClient)
        {
            WebClient webClientConcert = new WebClient();
            webClientConcert.DownloadStringCompleted += concert_DownloadStringCompleted;
            webClientConcert.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllConcerts"));
            WebClient webClientTicket = new WebClient();
            webClientTicket.DownloadStringCompleted += ticket_DownloadStringCompleted;
            webClientTicket.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getReservationsByCLient/id:" + idClient));
            WebClient webClientTariff = new WebClient();
            webClientTariff.DownloadStringCompleted += tariff_DownloadStringCompleted;
            webClientTariff.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllTariffs"));
            WebClient webClientArtist = new WebClient();
            webClientArtist.DownloadStringCompleted += artists_DownloadStringCompleted;
            webClientArtist.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllArtists"));
        }
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            progressBarLogin.Visibility = System.Windows.Visibility.Visible;
            IsolatedStorageSettings.ApplicationSettings["login"] = login.Text;
            if (rememberCheckBox.IsChecked == true)
                IsolatedStorageSettings.ApplicationSettings["pwd"] = pwd.Password;
            else
                IsolatedStorageSettings.ApplicationSettings["pwd"] = null;
            IsolatedStorageSettings.ApplicationSettings.Save();
            _pwd = Encryption.EncryptStringFromBytes(pwd.Password);
            _login = login.Text;
            WebClient webClientClients = new WebClient();
            webClientClients.DownloadStringCompleted += clients_DownloadStringCompleted;
            webClientClients.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllClients"));
        }
        public void goToConcerts(){
             NavigationService.Navigate(new Uri("/Concerts.xaml", UriKind.Relative));
        }

        private void clients_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            progressText.Text = "Authentification...";
            List<Client> clients = new List<Client>();
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Client> result = JsonConvert.DeserializeObject<List<Client>>(decryptedResultJSON);
                imgConnected.Source = imageBitmapConnected;
                for (int i = 0; i < result.Count; ++i)
                {
                    string s4 = Encryption.myDecrypt(result[i].password);
                    if (result[i].username.Equals(IsolatedStorageSettings.ApplicationSettings["login"]) && 
                            s4.Equals(IsolatedStorageSettings.ApplicationSettings["pwd"])) {
                        PhoneApplicationService.Current.State["connected"] = 1;
                        PhoneApplicationService.Current.State["idClient"] = result[i].id;
                        updateDatabase(result[i].id);
                    }
                    clients.Add(new Client
                    {
                        id = result[i].id,
                        username = result[i].username,
                        password = s4,
                    });
                }
                IsolatedStorageSettings.ApplicationSettings["clients"] = clients;
            }
            else
            {
                clients = (List<Client>)IsolatedStorageSettings.ApplicationSettings["clients"];
                foreach (Client client in clients)
                {
                    if (client.username.Equals(IsolatedStorageSettings.ApplicationSettings["login"]) &&
                            client.password.Equals(IsolatedStorageSettings.ApplicationSettings["pwd"]))
                    {
                        PhoneApplicationService.Current.State["connected"] = 1;
                        PhoneApplicationService.Current.State["idClient"] = client.id;
                        updateDatabase(client.id);
                    }
                }
                if (PhoneApplicationService.Current.State["connected"].Equals(0))
                {
                    progressText.Visibility = System.Windows.Visibility.Collapsed;
                    imgConnected.Source = imageBitmapDisconnected;
                    invalidLogin.Visibility = System.Windows.Visibility.Visible;
                    progressBarLogin.Visibility = System.Windows.Visibility.Collapsed;
                }
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
        }
        private void ticket_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Ticket> result = JsonConvert.DeserializeObject<List<Ticket>>(decryptedResultJSON);
                imgConnected.Source = imageBitmapConnected;
                List<Ticket> tickets = new List<Ticket>();
                progressText.Text = "loading reservations...";
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
            }else{
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 4)
            {
                goToConcerts();
            }
        }
        private void tariff_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {

                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Tariff> result = JsonConvert.DeserializeObject<List<Tariff>>(decryptedResultJSON);
                imgConnected.Source = imageBitmapConnected;
                List<Tariff> tariffs = new List<Tariff>();
                progressText.Text = "loading tariffs...";
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
            else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 4)
            {
                goToConcerts();
            }
        }
        
        private void artists_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {

                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Artist> result = JsonConvert.DeserializeObject<List<Artist>>(decryptedResultJSON);
                imgConnected.Source = imageBitmapConnected;
                List<Artist> artists = new List<Artist>();
                progressText.Text = "loading artists...";
                for (int i = 0; i < result.Count; ++i)
                {
                    artists.Add(new Artist
                    {
                        id = result[i].id,
                        name = result[i].name,
                    });
                }
                IsolatedStorageSettings.ApplicationSettings["artist"] = artists;
            }
            else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 4)
            {
                goToConcerts();
            } 
        }
        private void concert_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                imgConnected.Source = imageBitmapConnected;
                List<Concert> result = JsonConvert.DeserializeObject<List<Concert>>(decryptedResultJSON);
                List<Concert> concerts = new List<Concert>();
                progressText.Text = "loading concerts...";
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
            else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 4)
            {
                goToConcerts();
            } 
        }
    }
}