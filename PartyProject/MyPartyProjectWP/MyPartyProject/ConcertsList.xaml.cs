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
            Concert c1 = new Concert(1,"Michael Jackson","Images/party2.jpg",
                            "22/04/2012","23/04/2012",
                            "Poitiers", 
                            500, 
                            0);
            Concert c2 = new Concert(2,"Queen", "Images/party2.jpg","22/04/2012","23/04/2012",
                           "Bordeaux",900, 0);
            list.Add(c1);
            list.Add(c2);
            string json = JsonConvert.SerializeObject(list);
            concertText.Text = json;
                /*new Concert{id = 3, 
                            title = "Lorie", 
                            imgPath = "Images/party3.jpg",
                            beginDate = "22/04/2012",
                            endDate = "23/04/2012",
                            location = "Poitiers", 
                            nbSeets = 500, 
                            full = 0},
                new Concert{id = 4, 
                            title = "Police", 
                            imgPath = "Images/party4.jpg",
                            beginDate = "22/04/2012",
                            endDate = "23/04/2012",
                            location = "Poitiers", 
                            nbSeets = 500, 
                            full = 0},
            };
            concertsListBox.ItemsSource = list.OrderBy(e => e.id);*/
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
                }
                /*RootObject result = JsonConvert.DeserializeObject<RootObject>("{\"result\":" + e.Result + "}");
                if (result.listResult == null)
                    concertText.Text = "null";
                else
                    concertText.Text = "pas null";*/
                //concertText.Text = result.listResult[0].Concert.title;
                //concertsListBox.ItemsSource = result.listResult;//.Select(r => r.Concert.title);
            }
        }
        private void concertsListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (concertsListBox.SelectedItem == null)
            return;
            Concert currentArticle = (Concert)concertsListBox.SelectedItem;
            PhoneApplicationService.Current.State["Concert"] = currentArticle;
            concertsListBox.SelectedItem = null;
            NavigationService.Navigate(new Uri("/ConcertDetails.xaml", UriKind.Relative));
        }
    }
}