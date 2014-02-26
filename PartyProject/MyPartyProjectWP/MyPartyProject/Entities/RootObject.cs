using MyParty.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyPartyProject.Entities
{
    public class RootObject
    {
        public List<Concert> listResult { get; set; }
    }
   /* public class Page
    {
        public int label { get; set; }
        public string start { get; set; }
    }

    public class Cursor
    {
        public int currentPageIndex { get; set; }
        public string estimatedResultCount { get; set; }
        public string moreResultsUrl { get; set; }
        public List<Page> pages { get; set; }
        public string resultCount { get; set; }
        public string searchResultTime { get; set; }
    }

    public class Result
    {
        public string GsearchResultClass { get; set; }
        public string cacheUrl { get; set; }
        public string content { get; set; }
        public string title { get; set; }
        public string titleNoFormatting { get; set; }
        public string unescapedUrl { get; set; }
        public string url { get; set; }
        public string visibleUrl { get; set; }
    }

    public class ResponseData
    {
        public Cursor cursor { get; set; }
        public List<Result> results { get; set; }
    }

    public class RootObject
    {
        public ResponseData responseData { get; set; }
        public object responseDetails { get; set; }
        public int responseStatus { get; set; }
    }*/
}
