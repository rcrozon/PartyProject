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
using System.IO;
using MyPartyProject.Entities;
using Huyn.QRCode;
using ZXing.QrCode;
using ZXing;
using Windows.UI;
using System.IO.IsolatedStorage;
using System.Windows.Media;

namespace MyPartyProject
{
    public partial class TicketPage : PhoneApplicationPage
    {
        public TicketPage()
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
            Reservation res = (Reservation)PhoneApplicationService.Current.State["reservation"];
            List<Ticket> tickets = Ticket.getTicketsForOneConcert((List<Ticket>)IsolatedStorageSettings.ApplicationSettings["tickets"], res.id_concert);
            int index = 1;
            foreach (Ticket ticket in tickets)
            {
                PivotItem pivotItem = new PivotItem();
                StackPanel stack = new StackPanel();
                ImageBrush brush = new ImageBrush();
                BitmapImage image = new BitmapImage(new Uri(ticket.image, UriKind.Absolute));
                brush.ImageSource = image;
                stack.Background = brush; 
                TextBlock textBlockTitle = new TextBlock();
                TextBlock textBlockLocation = new TextBlock();
                TextBlock textBlockStartTime = new TextBlock();
                TextBlock textBlockEndTime = new TextBlock();
                TextBlock textBlockArtist = new TextBlock();
                TextBlock textBlockStyle = new TextBlock();
                TextBlock textBlockTariff = new TextBlock();
                textBlockTitle.Text = ticket.name_concert;
                textBlockLocation.Text = ticket.location;
                textBlockStartTime.Text = ticket.start_datetime;
                textBlockEndTime.Text = ticket.end_datetime;
                textBlockArtist.Text = "Artists :";
                textBlockStyle.Text = "Style :";
                textBlockTariff.Text = "Tariff : " + ticket.id_tariff;
                
                Image i = new Image();
                BarcodeWriter writer = new BarcodeWriter { Format = BarcodeFormat.QR_CODE };
                string code = ticket.id + ";" + ticket.id_concert + ";" + ticket.id_client + ";" + ticket.id_tariff;
                WriteableBitmap writeableBitmap = writer.Write(code);
                i.Source = writeableBitmap;
                i.MaxHeight = 200;
                i.MaxWidth = 200;
                
                stack.Children.Add(i);
                stack.Children.Add(textBlockTitle);
                stack.Children.Add(textBlockLocation);
                stack.Children.Add(textBlockStartTime);
                stack.Children.Add(textBlockEndTime);
                stack.Children.Add(textBlockArtist);
                stack.Children.Add(textBlockStyle);
                stack.Children.Add(textBlockTariff);
                pivotItem.Content = stack;
                pivotItem.Header = "Ticket " + index;
                index++;
                pivot.Items.Add(pivotItem);
            }
            
            /*
            PivotItem p = new PivotItem();
            Image     i = new Image();

            i.Source = new BitmapImage(new Uri("/image.jpg", UriKind.Relative));
            p.Margin = new Thickness(0, -10, 0, -2);

            DiaporamaPivot.Items.Add(i); 
             
             * 
                Uri backgroundUri = new Uri(ticket.image, UriKind.Absolute);
                BitmapImage bitmap = new BitmapImage(backgroundUri);
                imgBackground.Source = bitmap;
             */
            

        }
    }
}