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
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO.IsolatedStorage;
using System.Windows.Media.Imaging;

namespace MyPartyProject
{
    public partial class ConcertsList : PhoneApplicationPage
    {
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

        private void research_TextChanged(object sender, TextChangedEventArgs e)
        {
            List<Concert> list = (List<Concert>)IsolatedStorageSettings.ApplicationSettings["concerts"];
            for(int i = 0; i < list.Count; ++i)
            {
                if (!list[i].name_concert.Contains(research.Text))
                {
                    list.RemoveAt(i);
                }    
            }
            concertsListBox.ItemsSource = list;
        }
    }
}