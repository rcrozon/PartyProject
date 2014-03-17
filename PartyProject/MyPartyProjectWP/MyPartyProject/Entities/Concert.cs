using System;
using System.Collections.Generic;
using System.IO;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media.Imaging;
using System.Windows.Resources;

namespace MyPartyProject.Entities
{
    public class Concert : Entity
    {
        public string id { get; set; }
        public string start_datetime { get; set; }
        public string end_datetime { get; set; }
        public string location { get; set; }
        public string image { get; set; }
        public string id_tarif { get; set; }
        public string nb_seats { get; set; }
        public bool full { get; set; }
        public string id_creator { get; set; }
        public string name_concert { get; set; }
        public bool online { get; set; }

        public Concert(string id, bool online, string id_tarif, string id_creator, string name,string imgPath, string begin, string end, string location, string nbSeets, bool full)
        {
            this.id = id;
            this.id_tarif = id_tarif;
            this.id_creator = id_creator;
            this.image = imgPath;
            this.start_datetime = begin;
            this.end_datetime = end;
            this.location = location;
            this.name_concert = name;
            this.nb_seats = nbSeets;
            this.online = online;
            this.full = full;
        }

        public Concert()
        {
            // TODO: Complete member initialization
        }

        public static Concert getConcertFromId(List<Concert> concerts, string id)
        {
            foreach (Concert concert in concerts)
            {
                if (concert.id == id)
                {
                    return concert;
                }
            }
            return null;
        }

        public static void saveImage(string path, string image)
        {
            // First of all create a filename for JPEG file in isolated storage.
            // Now we are going to Create virtual store and file stream. Check for duplicate tempJPEG files.
            using (IsolatedStorageFile ISF = IsolatedStorageFile.GetUserStoreForApplication())
            {
                if (!ISF.FileExists(image))
                {
                    IsolatedStorageFileStream FS = ISF.CreateFile(image);
                    StreamResourceInfo Str_ri = null;
                    Uri ur = new Uri(path + image, UriKind.RelativeOrAbsolute);
                    Str_ri = Application.GetResourceStream(ur);
                    BitmapImage BI = new BitmapImage();
                    BI.SetSource(Str_ri.Stream);
                    WriteableBitmap Wr_B = new WriteableBitmap(BI);
                    // Furthe we have to encode WriteableBitmap object to a JPEG stream.
                    Extensions.SaveJpeg(Wr_B, FS, Wr_B.PixelWidth, Wr_B.PixelHeight, 0, 85);
                    Wr_B.SaveJpeg(FS, Wr_B.PixelWidth, Wr_B.PixelHeight, 0, 85);
                    FS.Close();
                    MessageBox.Show("Image has been saved");
                }
            }
        }

        public static BitmapImage loadImage(string image)
        {
            BitmapImage Bit_Img = new BitmapImage();
            using (IsolatedStorageFile ISF = IsolatedStorageFile.GetUserStoreForApplication())
            {
                using (IsolatedStorageFileStream FS = ISF.OpenFile(image, FileMode.Open, FileAccess.Read))
                {
                   Bit_Img.SetSource(FS);
                }
            }
            MessageBox.Show(Bit_Img.UriSource.ToString());
            return Bit_Img;
        }
    }
}
