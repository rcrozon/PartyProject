using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using Huyn.QRCode;

namespace Sample
{
    public partial class MainPage : PhoneApplicationPage
    {
        // Constructor
        public MainPage()
        {
            InitializeComponent();
        }

        private void GenerateButton_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            var generator=new QRCodeGenerator();
            QRCode.Source=generator.Generate(this.QRCodeText.Text);
        }
    }
}