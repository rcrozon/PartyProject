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
                reservationsListBox.ItemsSource = (List<Reservation>)IsolatedStorageSettings.ApplicationSettings["reservations"];
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

        private void research_TextChanged(object sender, TextChangedEventArgs e)
        {
            List<Reservation> list = (List<Reservation>)IsolatedStorageSettings.ApplicationSettings["reservations"];
            for(int i = 0; i < list.Count; ++i)
            {
                if (!list[i].name_concert.Contains(research.Text))
                {
                    list.RemoveAt(i);
                }    
            }
            reservationsListBox.ItemsSource = list;
        }
    }
}