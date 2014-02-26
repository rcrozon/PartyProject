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
            webClient.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllConcerts"));
            List<Concert> list = new List<Concert>();
            Concert c1 = new Concert("1", false, "0", "2", "Michael Jackson", "Images/party2.jpg", "23/04/2012", "23/04/2012", "Bordeaux", "500", false);
            Concert c2 = new Concert("2", false, "0", "2", "Queen", "Images/party3.jpg", "24/04/2012", "23/04/2012", "paris", "800", false);
            list.Add(c1);
            list.Add(c2);
            string json = JsonConvert.SerializeObject(list);
            concertText.Text = json;
            List<Concert> result = JsonConvert.DeserializeObject<List<Concert>>(json);
            /*for (int i = 0; i < result.Count; ++i)
            {
                concertText.Text += " " + result[i].name_concert + " " + result[i].location;
            }*/
            concertsListBox.ItemsSource = result.Select(c => c.name_concert);
        }

        private void concert_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                /*JArray a = JArray.Parse(e.Result);
                foreach (var item in a.Children())
                {
                    var itemProperties = item.Children<JProperty>();
                    //you could do a foreach or a linq here depending on what you need to do exactly with the value
                    var myElement = itemProperties.ElementAt(0);
                    var myElementValue = myElement.Value; ////This is a JValue type
                    concertText.Text = myElementValue.ElementAt(0).ToString();
                }*/
                /*string json = "{\"Concert\" :[{\"id\":\"1\",\"start_datetime\":\"2014-02-19 17:43:00\",\"end_datetime\":\"2015-02-19 17:43:00\",\"location\":\"Stade de france\",\"image\":\"GUETTA.jpg\",\"id_tarif\":\"0\",\"nb_seats\":\"79\",\"full\":false,\"id_creator\":\"1\",\"name_concert\":\"Guetta Party tour\",\"online\":false},{\"id\":\"2\",\"start_datetime\":\"2014-02-24 12:49:00\",\"end_datetime\":\"2014-02-25 12:49:00\",\"location\":\"Stade de france\",\"image\":\"Affiche Daft Punk.png\",\"id_tarif\":\"0\",\"nb_seats\":\"10000\",\"full\":false,\"id_creator\":\"1\",\"name_concert\":\"Daft Punk Party\",\"online\":false}]}";
                RootObject result = JsonConvert.DeserializeObject<RootObject>(json);
                concertText.Text = json;
                if (result == null)
                    concertText.Text += "null";
                else{
                    concertText.Text += "pas null";
                    if (result.listResult == null)
                        concertText.Text += "  null";
                    else
                        concertText.Text += "  pas null";
                }
                /*for (int i = 0; i < result.Count; ++i )
                {
                    concertText.Text += "\n" + result[i].id + "  " + 
                                        result[i].beginDate + "  " + 
                                        result[i].endDate + "  " +
                                        result[i].location + "  " +
                                        result[i].imgPath + "  " +
                                        result[i].id_creator + "  " + result[i].nbSeets + "  " + result[i].id_tarif
                                        + "  " + result[i].name_concert + "  " + result[i].online;
                }*/
                
                //concertText.Text = result.listResult[0].Concert.title;
                //concertsListBox.ItemsSource = result;
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