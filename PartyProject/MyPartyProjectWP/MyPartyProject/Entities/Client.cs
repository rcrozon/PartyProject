using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Entities
{
    public class Client
    {
        public string id { get; set; }
        public string username { get; set; }
        public string mail { get; set; }
        public string password { get; set; }
        public string first_name { get; set; }
        public string last_name { get; set; }
        public string admin { get; set; }
        public string created { get; set; }
    }
}
