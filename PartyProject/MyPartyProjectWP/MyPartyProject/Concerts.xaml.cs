using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;

namespace MyPartyProject
{
    public partial class Concerts : PhoneApplicationPage
    {
        public Concerts()
        {
            InitializeComponent();
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