﻿#pragma checksum "C:\Users\Romain\Documents\GitHub\PartyProject\PartyProject\MyPartyProjectWP\MyPartyProject\ReservationsList.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "AD60317F32482B49366C6ED669C4A974"
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
    
    
    public partial class ReservationsList : Microsoft.Phone.Controls.PhoneApplicationPage {
        
        internal System.Windows.Controls.Grid LayoutRoot;
        
        internal System.Windows.Controls.TextBlock pageTitle;
        
        internal System.Windows.Controls.Grid ContentPanel;
        
        internal System.Windows.Controls.ListBox reservationsListBox;
        
        internal System.Windows.Controls.TextBox research;
        
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
            System.Windows.Application.LoadComponent(this, new System.Uri("/MyPartyProject;component/ReservationsList.xaml", System.UriKind.Relative));
            this.LayoutRoot = ((System.Windows.Controls.Grid)(this.FindName("LayoutRoot")));
            this.pageTitle = ((System.Windows.Controls.TextBlock)(this.FindName("pageTitle")));
            this.ContentPanel = ((System.Windows.Controls.Grid)(this.FindName("ContentPanel")));
            this.reservationsListBox = ((System.Windows.Controls.ListBox)(this.FindName("reservationsListBox")));
            this.research = ((System.Windows.Controls.TextBox)(this.FindName("research")));
            this.imgConnected = ((System.Windows.Controls.Image)(this.FindName("imgConnected")));
        }
    }
}

