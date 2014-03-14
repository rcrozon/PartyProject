using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Maps;
using Microsoft.Phone.Maps.Controls;
using Microsoft.Phone.Shell;
using MyPartyProject.Entities;
using System.Windows.Media.Imaging;
using System.Windows.Media;
using System.ComponentModel;
using System.Data;
using System.Device.Location;
using Newtonsoft.Json;
using System.IO.IsolatedStorage;

namespace MyPartyProject
{
    public partial class ConcertDetails : PhoneApplicationPage
    {

        public ConcertDetails()
        {
            InitializeComponent();
            GeoCoordinateWatcher geoWatcher = new GeoCoordinateWatcher(GeoPositionAccuracy.High);
            geoWatcher.StatusChanged += geoWatcher_StatusChanged; 
            geoWatcher.PositionChanged += geoWatcher_PositionChanged;
            geoWatcher.Start();
            map.ZoomLevel = 15;
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
            Concert concert = (Concert)PhoneApplicationService.Current.State["Concert"];
            Uri imageUri = new Uri(concert.image, UriKind.Absolute);
            BitmapImage imageBitmap = new BitmapImage(imageUri);
            imgConcert.Source = imageBitmap;
            textBeginDate.Text = concert.start_datetime;
            textEndDate.Text = concert.end_datetime;
            textLocation.Text = concert.location;
            textNbSeets.Text = "Number of seats " + concert.nb_seats;
            textTitle.Text = concert.name_concert;
            try
            {
                List<Reservation> reservations = (List<Reservation>)IsolatedStorageSettings.ApplicationSettings["tickets"];
                List<Concert> concerts = (List<Concert>)IsolatedStorageSettings.ApplicationSettings["concerts"];
                for (int i = 0; i < reservations.Count; ++i)
                {
                    if (reservations[i].id_concert.Equals(concert.id))
                    {
                        Concert c = Concert.getConcertFromId(concerts, reservations[i].id_concert);
                        reservations[i].image = c.image;
                        reservations[i].concertLabel = c.name_concert;
                    }
                }
                ticketsListBox.ItemsSource = reservations;
            }
            catch (System.Collections.Generic.KeyNotFoundException e) { }
            /********************** Affichage des tickets *******************************/
            
            //geoWatcher = new GeoCoordinateWatcher();
            //geoWatcher.StatusChanged += geoWatcher_StatusChanged;
            //geoWatcher.Start();*/
        }
        
        private void ticketsListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (ticketsListBox.SelectedItem == null)
                return;
            Reservation currentTicket = (Reservation)ticketsListBox.SelectedItem;
            PhoneApplicationService.Current.State["Ticket"] = currentTicket;
            ticketsListBox.SelectedItem = null;
            NavigationService.Navigate(new Uri("/TicketPage.xaml", UriKind.Relative));
        }

        private void geoWatcher_PositionChanged(object sender, GeoPositionChangedEventArgs<GeoCoordinate> e)
        {
            map.Center = e.Position.Location;
            
            /*Dispatcher.BeginInvoke(() =>
            {
            
                if (map.Children.OfType<Pushpin>().Any())
                {
                    pushPin.Location = e.Position.Location;
                }
                else
                {
                    pushPin = new Pushpin { Location = e.Position.Location };
                    map.Children.Add(pushPin);
                }

            });*/
        }

        private void geoWatcher_StatusChanged(object sender, GeoPositionStatusChangedEventArgs e)
        {
            switch (e.Status)
            {
                case GeoPositionStatus.Disabled:
                    // le gps n'est pas activé
                    break;
                case GeoPositionStatus.Initializing:
                    // le gps est en cours d'initialisation
                    break;
                case GeoPositionStatus.NoData:
                    // il n'y a pas d'informations
                    break;
                case GeoPositionStatus.Ready:
                    // le GPS est activé et disponible
                    break;
            }
        }


    }
}