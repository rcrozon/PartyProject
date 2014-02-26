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
using MyPartyProject.Entities;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace MyPartyProject
{
    public partial class ConcertsList : PhoneApplicationPage
    {
        public ConcertsList()
        {
            InitializeComponent();
            if (PhoneApplicationService.Current.State["ConcertMode"].Equals(0))
                pageTitle.Text = "All concerts";
            else if (PhoneApplicationService.Current.State["ConcertMode"].Equals(1))
                pageTitle.Text = "Next concerts";
            else 
                pageTitle.Text = "News";
            WebClient webClient = new WebClient();
            webClient.DownloadStringCompleted += concert_DownloadStringCompleted;
            webClient.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllConcertsWindows"));
        }

        private void concert_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                List<Concert> result = JsonConvert.DeserializeObject<List<Concert>>(e.Result);
                if (result == null)
                    concertText.Text = "null";
                else{
                    concertText.Text = "pas null";
                }
                List<Concert> concerts = new List<Concert>();
                for (int i = 0; i < result.Count; ++i)
                {
                    concerts.Add(new Concert
                    {
                        start_datetime = result[i].start_datetime,
                        end_datetime = result[i].end_datetime,
                        location = result[i].location,
                        image = result[i].image,
                        nb_seats = result[i].nb_seats,
                        name_concert = result[i].name_concert,
                    });
                }
                concertText.Text = result[0].start_datetime + result[0].end_datetime +
                                    result[0].location + result[0].image + result[0].nb_seats +
                                    result[0].nb_seats + result[0].name_concert;
                concertsListBox.ItemsSource = concerts.Select(c => c.name_concert);
            }
        }
        private void concertsListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            /*if (concertsListBox.SelectedItem == null)
            return;
            Concert currentArticle = (Concert)concertsListBox.SelectedItem;
            PhoneApplicationService.Current.State["Concert"] = currentArticle;
            concertsListBox.SelectedItem = null;
            NavigationService.Navigate(new Uri("/ConcertDetails.xaml", UriKind.Relative));*/
        }
    }
}