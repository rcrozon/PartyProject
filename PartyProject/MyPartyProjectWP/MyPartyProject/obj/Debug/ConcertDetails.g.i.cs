﻿#pragma checksum "C:\Users\Romain\Documents\GitHub\PartyProject\PartyProject\MyPartyProjectWP\MyPartyProject\ConcertDetails.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "3ABDB6A7F4F2FEA96954B4E09F4C42F2"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.18449
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using Microsoft.Phone.Controls;
using Microsoft.Phone.Maps.Controls;
using System;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Automation.Peers;
using System.Windows.Automation.Provider;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Imaging;
using System.Windows.Resources;
using System.Windows.Shapes;
using System.Windows.Threading;


namespace MyPartyProject {
    
    
    public partial class ConcertDetails : Microsoft.Phone.Controls.PhoneApplicationPage {
        
        internal System.Windows.Controls.Grid LayoutRoot;
        
        internal System.Windows.Controls.Grid gridDetails;
        
        internal System.Windows.Controls.Image imgConcert;
        
        internal System.Windows.Controls.TextBlock textTitle;
        
        internal System.Windows.Controls.TextBlock textLocation;
        
        internal System.Windows.Controls.TextBlock textBeginDate;
        
        internal System.Windows.Controls.TextBlock textEndDate;
        
        internal System.Windows.Controls.TextBlock textNbSeets;
        
        internal System.Windows.Controls.ListBox ticketsListBox;
        
        internal Microsoft.Phone.Maps.Controls.Map map;
        
        internal System.Windows.Controls.Image imgConnected;
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Windows.Application.LoadComponent(this, new System.Uri("/MyPartyProject;component/ConcertDetails.xaml", System.UriKind.Relative));
            this.LayoutRoot = ((System.Windows.Controls.Grid)(this.FindName("LayoutRoot")));
            this.gridDetails = ((System.Windows.Controls.Grid)(this.FindName("gridDetails")));
            this.imgConcert = ((System.Windows.Controls.Image)(this.FindName("imgConcert")));
            this.textTitle = ((System.Windows.Controls.TextBlock)(this.FindName("textTitle")));
            this.textLocation = ((System.Windows.Controls.TextBlock)(this.FindName("textLocation")));
            this.textBeginDate = ((System.Windows.Controls.TextBlock)(this.FindName("textBeginDate")));
            this.textEndDate = ((System.Windows.Controls.TextBlock)(this.FindName("textEndDate")));
            this.textNbSeets = ((System.Windows.Controls.TextBlock)(this.FindName("textNbSeets")));
            this.ticketsListBox = ((System.Windows.Controls.ListBox)(this.FindName("ticketsListBox")));
            this.map = ((Microsoft.Phone.Maps.Controls.Map)(this.FindName("map")));
            this.imgConnected = ((System.Windows.Controls.Image)(this.FindName("imgConnected")));
        }
    }
}

