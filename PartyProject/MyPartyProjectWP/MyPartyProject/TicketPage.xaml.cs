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
using MyPartyProject.QRCode;
using System.IO;

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
            QRCodeGenerator generator = new QRCodeGenerator();
            WriteableBitmap code = generator.Generate("63;2;4;4");
            using (MemoryStream ms = new MemoryStream())
            {
                code.SaveJpeg(ms, (int)imgTicket.Width, (int)imgTicket.Height, 0, 100);
                BitmapImage bmp = new BitmapImage();
                bmp.SetSource(ms);
                imgTicket.Source = bmp;
            }
            
            


        }
    }
}