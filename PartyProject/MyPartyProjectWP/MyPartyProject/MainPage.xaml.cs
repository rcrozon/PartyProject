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
using MyPartyProject.Database;
using MyPartyProject.Entities;
using System.IO.IsolatedStorage;
using Newtonsoft.Json;
using System.Windows.Media.Imaging;
using System.Diagnostics;

namespace MyPartyProject
{
    public partial class MainPage : PhoneApplicationPage
    {
        private int loaded = 0; 
        // Constructor
        public MainPage()
        {
            InitializeComponent();
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
            
            //DatabaseHandler handler = new DatabaseHandler();
            // Sample code to localize the ApplicationBar
            //BuildLocalizedApplicationBar();
        }

        private void updateDatabase(string idClient)
        {
            WebClient webClientConcert = new WebClient();
            webClientConcert.DownloadStringCompleted += concert_DownloadStringCompleted;
            webClientConcert.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllConcerts"));
            WebClient webClientTicket = new WebClient();
            webClientTicket.DownloadStringCompleted += ticket_DownloadStringCompleted;
            webClientTicket.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllReservationsById/id:" + idClient));
            WebClient webClientTariff = new WebClient();
            webClientTariff.DownloadStringCompleted += tariff_DownloadStringCompleted;
            webClientTariff.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllTariffs"));
        }
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            IsolatedStorageSettings.ApplicationSettings["login"] = login.Text;
            if (rememberCheckBox.IsChecked == true)
                IsolatedStorageSettings.ApplicationSettings["pwd"] = pwd.Password;
            else
                IsolatedStorageSettings.ApplicationSettings["pwd"] = null;
            IsolatedStorageSettings.ApplicationSettings.Save();

            //TODO verification du login et pwd
            /*
            if (notValid)
            {
                invalidLogin.Visibility = Visibility.Visible;    
            }
            else 
            {
                invalidLogin.Visibility = Visibility.Collapsed;    
            }*/
            string idClient = "5";
            PhoneApplicationService.Current.State["idClient"] = idClient;
            updateDatabase(idClient);
        }
        public void goToConcerts(){
             NavigationService.Navigate(new Uri("/Concerts.xaml", UriKind.Relative));
        }

        private void ticket_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                List<Reservation> result = JsonConvert.DeserializeObject<List<Reservation>>(e.Result);
                List<Reservation> reservations = new List<Reservation>();
                for (int i = 0; i < result.Count; ++i)
                {
                    //if (result[i].id_client.Equals((string)(PhoneApplicationService.Current.State["idClient"])))
                    //{
                        reservations.Add(new Reservation
                        {
                            id = result[i].id,
                            id_client = result[i].id_client,
                            id_concert = result[i].id_concert,
                            id_tarif = result[i].id_tarif,
                            scan = result[i].scan,
                        });
                    //}
                }
                IsolatedStorageSettings.ApplicationSettings["tickets"] = reservations;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 3)
            {
                goToConcerts();
            }
            //progressBarLogin.Value = 50;
            MessageBox.Show("Ticket" + loaded, "null", MessageBoxButton.OK);
        }
        private void tariff_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                List<Tariff> result = JsonConvert.DeserializeObject<List<Tariff>>(e.Result);
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
            loaded += 1;
            progressBarLogin.Value = 50;
            MessageBox.Show("Tariff" + loaded, "null", MessageBoxButton.OK);
            if (loaded == 3)
            {
                goToConcerts();
            } 
        }
        private void concert_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                List<Concert> result = JsonConvert.DeserializeObject<List<Concert>>(e.Result);
                List<Concert> concerts = new List<Concert>();
                
                for (int i = 0; i < result.Count; ++i)
                {

                    //MessageBox.Show("Concert" + loaded, "pas null", MessageBoxButton.OK); 
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
                PhoneApplicationService.Current.State["connected"] = 1;
                Uri imageUri = new Uri("/Images/ic_connected", UriKind.Relative);
                BitmapImage imageBitmap = new BitmapImage(imageUri);
                imgConnected.Source = imageBitmap;
            }
            else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                Uri imageUri = new Uri("/Images/ic_not_connected", UriKind.Relative);
                BitmapImage imageBitmap = new BitmapImage(imageUri);
                imgConnected.Source = imageBitmap;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            progressBarLogin.Value = 100;
            MessageBox.Show("Concert" + loaded, "null", MessageBoxButton.OK);
            if (loaded == 3)
            {
                goToConcerts();
            } 
        }
        // Sample code for building a localized ApplicationBar
        //private void BuildLocalizedApplicationBar()
        //{
        //    // Set the page's ApplicationBar to a new instance of ApplicationBar.
        //    ApplicationBar = new ApplicationBar();

        //    // Create a new button and set the text value to the localized string from AppResources.
        //    ApplicationBarIconButton appBarButton = new ApplicationBarIconButton(new Uri("/Assets/AppBar/appbar.add.rest.png", UriKind.Relative));
        //    appBarButton.Text = AppResources.AppBarButtonText;
        //    ApplicationBar.Buttons.Add(appBarButton);

        //    // Create a new menu item with the localized string from AppResources.
        //    ApplicationBarMenuItem appBarMenuItem = new ApplicationBarMenuItem(AppResources.AppBarMenuItemText);
        //    ApplicationBar.MenuItems.Add(appBarMenuItem);
        //}
    }
}