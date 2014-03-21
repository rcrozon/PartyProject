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
using MyPartyProject.Entities;
using System.IO.IsolatedStorage;
using Newtonsoft.Json;
using System.Windows.Media.Imaging;
using System.Diagnostics;
using System.IO;

using MyPartyProject.Encrypt;
using System.Text;

namespace MyPartyProject
{
    public partial class MainPage : PhoneApplicationPage
    {
        
        private int loaded = 0;
        private string key = "0123456789abcdef";
        private string iv = "fedcba9876543210";
        private BitmapImage imageBitmapConnected;
        private BitmapImage imageBitmapDisconnected;
        private string idClient = null;
        private string _pwd = "";
        private string _login = "";
        // Constructor
        public MainPage()
        {
            InitializeComponent();
            invalidLogin.Visibility = Visibility.Collapsed;  
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
            Uri imageUri = new Uri("/Images/ic_connected", UriKind.Relative);
            imageBitmapConnected = new BitmapImage(imageUri);
            Uri imageUri2 = new Uri("/Images/ic_not_connected", UriKind.Relative);
            imageBitmapDisconnected = new BitmapImage(imageUri);
                
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
            webClientTicket.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getReservationsByCLient/id:" + idClient));
            WebClient webClientTariff = new WebClient();
            webClientTariff.DownloadStringCompleted += tariff_DownloadStringCompleted;
            webClientTariff.DownloadStringAsync(new Uri("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/getAllTariffs"));
        }
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            progressBarLogin.Visibility = System.Windows.Visibility.Visible;
            IsolatedStorageSettings.ApplicationSettings["login"] = login.Text;
            if (rememberCheckBox.IsChecked == true)
                IsolatedStorageSettings.ApplicationSettings["pwd"] = pwd.Password;
            else
                IsolatedStorageSettings.ApplicationSettings["pwd"] = null;
            IsolatedStorageSettings.ApplicationSettings.Save();
            _pwd = Encryption.EncryptStringFromBytes(pwd.Password);
            _login = login.Text;
            authentification("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/login");
            //while ((idClient = loginProcess.getIdResult()) == null) ;
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
            
        }
        public void goToConcerts(){
             NavigationService.Navigate(new Uri("/Concerts.xaml", UriKind.Relative));
        }
        private void ticket_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Ticket> result = JsonConvert.DeserializeObject<List<Ticket>>(decryptedResultJSON);
                PhoneApplicationService.Current.State["connected"] = 1;
                imgConnected.Source = imageBitmapConnected;
                List<Ticket> tickets = new List<Ticket>();
                progressText.Text = "loading reservations...";
                for (int i = 0; i < result.Count; ++i)
                {
                    //if (result[i].id_client.Equals((string)(PhoneApplicationService.Current.State["idClient"])))
                    //{
                    tickets.Add(new Ticket
                        {
                            id = result[i].id,
                            id_client = result[i].id_client,
                            id_concert = result[i].id_concert,
                        });
                    //}
                }
                IsolatedStorageSettings.ApplicationSettings["tickets"] = tickets;
            }else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 3)
            {
                goToConcerts();
            }
        }
        private void tariff_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {

                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                List<Tariff> result = JsonConvert.DeserializeObject<List<Tariff>>(decryptedResultJSON);
                PhoneApplicationService.Current.State["connected"] = 1;
                imgConnected.Source = imageBitmapConnected;
                List<Tariff> tariffs = new List<Tariff>();
                progressText.Text = "loading tariffs...";
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
            else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 3)
            {
                goToConcerts();
            } 
        }
        private void concert_DownloadStringCompleted(object sender, DownloadStringCompletedEventArgs e)
        {
            if (e.Error == null)
            {
                string s = Encryption.myDecrypt(e.Result.Trim());
                string decryptedResultJSON = s;
                imgConnected.Source = imageBitmapConnected;
                List<Concert> result = JsonConvert.DeserializeObject<List<Concert>>(decryptedResultJSON);
                PhoneApplicationService.Current.State["connected"] = 1;
                List<Concert> concerts = new List<Concert>();
                progressText.Text = "loading concerts...";
                for (int i = 0; i < result.Count; ++i)
                {
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
            }
            else
            {
                PhoneApplicationService.Current.State["connected"] = 0;
                imgConnected.Source = imageBitmapDisconnected;
            }
            IsolatedStorageSettings.ApplicationSettings.Save();
            loaded += 1;
            if (loaded == 3)
            {
                goToConcerts();
            } 
        }


        // Create the web request object 
        public void authentification(string url)
        {
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Method = "POST";

            //webRequest.Headers["Email"] = "abc@xyz.com"; 
            //webRequest.Headers["password"] = "abc123"; 


            webRequest.ContentType = "application/json;charset=utf-8";
            //"text/json";// 
            // Start the request 
            webRequest.BeginGetRequestStream(new AsyncCallback(GetRequestStreamCallback), webRequest);
        }

        public void GetRequestStreamCallback(IAsyncResult asynchronousResult)
        {
            HttpWebRequest webRequest = (HttpWebRequest)asynchronousResult.AsyncState;
            // End the stream request operation 
            Stream postStream = webRequest.EndGetRequestStream(asynchronousResult);

            // Create the post data 

            string postData = "[{\"username\":\"" + _login + "\",\"password\":\"" + _pwd + "\"}]";
            var input = JsonConvert.SerializeObject(postData);
            byte[] byteArray = Encoding.UTF8.GetBytes(postData);

            // Add the post data to the web request 
            postStream.Write(byteArray, 0, byteArray.Length);
            postStream.Close();

            // Start the web request 
            webRequest.BeginGetResponse(new AsyncCallback(GetResponseCallback), webRequest);
        }
        public void GetResponseCallback(IAsyncResult asynchronousResult)
        {
            string result = "";
            try
            {
                HttpWebRequest webRequest = (HttpWebRequest)asynchronousResult.AsyncState;
                HttpWebResponse response;

                // End the get response operation 
                response = (HttpWebResponse)webRequest.EndGetResponse(asynchronousResult);
                Stream streamResponse = response.GetResponseStream();
                StreamReader streamReader = new StreamReader(streamResponse);
                var Response = streamReader.ReadToEnd();
                result = Response.ToString();
                streamResponse.Close();
                streamReader.Close();
                response.Close();
            }
            catch (WebException e)
            {
                string s = "pas cool";
                // Error treatment 
                // ... 
            }
            idClient = result;
            if (idClient != null)
            {
                updateDatabase(idClient);
                PhoneApplicationService.Current.State["idClient"] = idClient;
            }
        }
    }
}