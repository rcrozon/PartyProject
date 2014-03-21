using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Database
{
    public class LoginProcess
    {
        private string login, pwd, url;
        private string result = null;

        public LoginProcess(string _url, string _login, string _pwd){
            url = _url;
            login = _login;
            pwd = _pwd;
        }

        public string getIdResult()
        {
            return result;
        }
        // Create the web request object 
        public void authentification(){
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url); 
            webRequest.Method = "POST"; 

            //webRequest.Headers["Email"] = "abc@xyz.com"; 
            //webRequest.Headers["password"] = "abc123"; 


            webRequest.ContentType = "application/json;charset=utf-8"; 
            //"text/json";// 
            // Start the request 
            webRequest.BeginGetRequestStream(new AsyncCallback(GetRequestStreamCallback), webRequest); 
        } 

        public void GetRequestStreamCallback(IAsyncResult asynchronousResult) 
        { 
            HttpWebRequest webRequest = (HttpWebRequest)asynchronousResult.AsyncState; 
            // End the stream request operation 
            Stream postStream = webRequest.EndGetRequestStream(asynchronousResult); 

            // Create the post data 

            string postData = "{\"username\":\"" + login + "\",\"password\":\"" + pwd + "\"}"; 
            var input = JsonConvert.SerializeObject(postData); 
            byte[] byteArray = Encoding.UTF8.GetBytes(input); 

            // Add the post data to the web request 
            postStream.Write(byteArray, 0, byteArray.Length); 
            postStream.Close(); 

            // Start the web request 
            webRequest.BeginGetResponse(new AsyncCallback(GetResponseCallback), webRequest); 
        } 
        public void GetResponseCallback(IAsyncResult asynchronousResult) 
        { 
            try 
            { 
                HttpWebRequest webRequest = (HttpWebRequest)asynchronousResult.AsyncState; 
                HttpWebResponse response; 

                // End the get response operation 
                response = (HttpWebResponse)webRequest.EndGetResponse(asynchronousResult); 
                Stream streamResponse = response.GetResponseStream(); 
                StreamReader streamReader = new StreamReader(streamResponse); 
                var Response = streamReader.ReadToEnd(); 
                result = Response.ToString(); 
                streamResponse.Close(); 
                streamReader.Close(); 
                response.Close(); 
            } 
            catch (WebException e) 
            { 
            // Error treatment 
            // ... 
            } 
        }
    }
}
