using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyParty.Entities
{
    public class Client : Entity
    {
        
        public int id { get; set;}
        public String firstName { get; set; }
        public String lastName { get; set; }
        public String email { get; set; }
        public String login { get; set; }
        public String password { get; set; }
        public int admin { get; set; }
        public String dateCreated { get; set; }

        public Client(int id, String firstName,	String lastName, String email, String login, String password, int admin, String dateCreated){
		    this.id = id;
		    this.firstName 	= firstName;
		    this.lastName 	= lastName;
		    this.email 		= email;
		    this.login 		= login;
		    this.password 	= password;
		    this.admin = admin;
		    this. dateCreated = dateCreated;
	    }

        public Client()
        {
            // TODO: Complete member initialization
        }
    }
}
