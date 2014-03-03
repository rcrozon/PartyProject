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
            Reservation ticket = (Reservation)PhoneApplicationService.Current.State["Ticket"];

            BarcodeWriter writer = new BarcodeWriter { Format = BarcodeFormat.QR_CODE };
            string code = ticket.id + ";" + ticket.id_concert + ";" + ticket.id_client + ";" + ticket.id_tarif;
            WriteableBitmap writeableBitmap = writer.Write(code);
            imgQRCode.Source = writeableBitmap;

            Uri backgroundUri = new Uri(ticket.image, UriKind.Absolute);
            BitmapImage bitmap = new BitmapImage(backgroundUri);
            imgBackground.Source = bitmap;
            


        }
    }
}