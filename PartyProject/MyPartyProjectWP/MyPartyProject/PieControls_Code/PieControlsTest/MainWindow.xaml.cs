using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Collections.ObjectModel;
using PieControls;
using Xceed.Wpf.Toolkit;


namespace PieControlsTest
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            DataContext = this;            
            InitializeComponent();
            PopulateCharts();
            listView.ItemsSource = collection1;
            allListsBox.ItemsSource = (from a in collectionList select a.CollectionName).ToArray();
            allListsBox.SelectedIndex = 0;
        }
        
        List<PieDataCollection<PieSegment>> collectionList = new List<PieDataCollection<PieSegment>>();
        PieDataCollection<PieSegment> collection1;
        PieDataCollection<PieSegment> collection2;
        PieDataCollection<PieSegment> collection3;
        PieDataCollection<PieSegment> collection4;

        void PopulateCharts()
        {
            collection1 = new PieDataCollection<PieSegment>();
            collection1.CollectionName = "Animals";
            collection1.Add(new PieSegment { Color = Colors.Green, Value = 5, Name = "Dogs" });
            collection1.Add(new PieSegment { Color = Colors.Yellow, Value = 12, Name = "Cats" });
            collection1.Add(new PieSegment { Color = Colors.Red, Value = 20, Name = "Mice" });
            collection1.Add(new PieSegment { Color = Colors.DarkCyan, Value = 22, Name = "Lizards" });
            
            collection2 = new PieDataCollection<PieSegment>();
            collection2.CollectionName = "Foods";
            collection2.Add(new PieSegment { Color = Colors.Green, Value = 20, Name = "Dairy" });
            collection2.Add(new PieSegment { Color = Colors.Yellow, Value = 10, Name = "Fruites" });
            collection2.Add(new PieSegment { Color = Colors.Red, Value = 10, Name = "Vegetables" });
            collection2.Add(new PieSegment { Color = Colors.DarkCyan, Value = 18, Name = "Meat" });
            collection2.Add(new PieSegment { Color = Colors.Wheat, Value = 20, Name = "Grains" });
            collection2.Add(new PieSegment { Color = Colors.Gold, Value = 8, Name = "Sweets" });

            collection3 = new PieDataCollection<PieSegment>();
            collection3.CollectionName = "Fruites";
            collection3.Add(new PieSegment { Color = Colors.Green, Value = 200, Name = "Apples" });
            collection3.Add(new PieSegment { Color = Colors.Yellow, Value = 150, Name = "Oranges" });
            collection3.Add(new PieSegment { Color = Colors.Red, Value = 250, Name = "Grapes" });
            collection3.Add(new PieSegment { Color = Colors.DarkCyan, Value = 100, Name = "Melons" });
            collection3.Add(new PieSegment { Color = Colors.Brown, Value = 140, Name = "Peaches" });

            collection4 = new PieDataCollection<PieSegment>();
            collection4.CollectionName = "Furniture";
            collection4.Add(new PieSegment { Color = Colors.Green, Value = 8.5, Name = "Tables" });
            collection4.Add(new PieSegment { Color = Colors.Yellow, Value = 7.5, Name = "Chairs" });
            collection4.Add(new PieSegment { Color = Colors.Red, Value = 9.2, Name = "Beds" });

            pie1.Data = collection1;
            pie2.Data = collection2;
            pie1.PopupBrush = Brushes.LightGray;
            chart1.Data = collection3;
            chart1.PopupBrush = Brushes.LightBlue;
            chart2.Data = collection4;
            chart2.PopupBrush = Brushes.LightCoral;
            collectionList.AddRange(new [] { collection1, collection2, collection3, collection4 });
        }

        private void button1_Click(object sender, RoutedEventArgs e)
        {
            Random rand = new Random();
            for (int i = collectionList.Count - 1; i > 0; i--) //Knuth-Fisher-Yates shuffle algorithm
            {
                int next = rand.Next(i + 1);
                var temp = collectionList[i];
                collectionList[i] = collectionList[next];
                collectionList[next] = temp;                
            }
            pie1.Data = collectionList[0];
            pie2.Data = collectionList[1];
            chart1.Data = collectionList[2];
            chart2.Data = collectionList[3];
            //this.InvalidateVisual();
        }

        private void allListsBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            listView.ItemsSource = collectionList[allListsBox.SelectedIndex];
        }        
    }
}
