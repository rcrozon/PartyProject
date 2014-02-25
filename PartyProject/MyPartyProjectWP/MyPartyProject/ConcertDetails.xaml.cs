using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using MyParty.Entities;
using System.Windows.Media.Imaging;
using System.Windows.Media;
using System.ComponentModel;
using System.Data;
using System.Device.Location;
//using System.Device.Location.GeoCoordinateWatcher;

namespace MyPartyProject
{
    //private GeoCoordinateWatcher geoWatcher;

    public partial class ConcertDetails : PhoneApplicationPage
    {

        public ConcertDetails()
        {
            InitializeComponent();
            List<Ticket> list = new List<Ticket>
             {
                new Ticket {
                    id = 1,
                    nbSeets = 3,
                    concert = new Concert(1, "/Images/party2.jpg", "Lorie", "22/05/2014", "23/08/2011", "Poitiers", 500, 1 ),
                },
                new Ticket {
                    id = 2,
                    concert = new Concert(2, "/Images/party2.jpg", "Queen", "22/05/2014", "23/08/2011", "Bordeaux", 800, 1 ),
                    nbSeets = 5,
                },
                new Ticket {
                    id = 3,
                    concert = new Concert(3, "/Images/party3.jpg", "ACDC", "22/05/2014", "23/08/2011", "Angoulême", 500, 1 ),
                    nbSeets = 8,
                },
                new Ticket {
                    id = 4,
                    concert = new Concert(4, "/Images/party4.jpg", "Queen", "22/05/2014", "23/08/2011", "Poitiers", 500, 1 ),
                    nbSeets = 10,
                },
            };
            ticketsListBox.ItemsSource = list.OrderBy(e => e.id);
            Concert concert = (Concert)PhoneApplicationService.Current.State["Concert"];
            Uri imageUri = new Uri(concert.imgPath, UriKind.Relative);
            BitmapImage imageBitmap = new BitmapImage(imageUri);
            Image myImage = new Image();
            imgConcert.Source = imageBitmap;
            textBeginDate.Text = concert.beginDate;
            textEndDate.Text = concert.endDate;
            textLocation.Text = concert.location;
            textNbSeets.Text = "Number of seets " + concert.nbSeets;
            textTitle.Text = concert.title;
            //geoWatcher = new GeoCoordinateWatcher();
            //geoWatcher.StatusChanged += geoWatcher_StatusChanged;
            //geoWatcher.Start();
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