using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using System.Windows.Media.Imaging;

namespace MyPartyProject
{
    public partial class Concerts : PhoneApplicationPage
    {
        public Concerts()
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
        }
        //Button AllConcerts
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            PhoneApplicationService.Current.State["ConcertMode"] = 0;
            NavigationService.Navigate(new Uri("/ConcertsList.xaml", UriKind.Relative));
        }

        //Button NextConcerts
        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            PhoneApplicationService.Current.State["ConcertMode"] = 1;
            NavigationService.Navigate(new Uri("/ConcertsList.xaml", UriKind.Relative));
        }

        //Button NewsConcerts
        private void Button_Click_3(object sender, RoutedEventArgs e)
        {
            PhoneApplicationService.Current.State["ConcertMode"] = 2;
            NavigationService.Navigate(new Uri("/ConcertsList.xaml", UriKind.Relative));
        }
    }
}