using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace MyPartyProject.Encrypt
{
    public class Encryption
    {
        string key = "0123456789abcdef";
        string iv = "fedcba9876543210";
            
        public static byte[] StringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                            .Where(x => x % 2 == 0)
                            .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                            .ToArray();
        }
          
        public static String DecryptRJ256(string input)
        {
            byte[] cypher = StringToByteArray(input);
            string key = "0123456789abcdef";
            string iv = "fedcba9876543210";
            //string res = BitConverter.ToString(cypher).Replace("-", string.Empty);
            var sRet = "";

            var encoding = new UTF8Encoding();
            var Key = encoding.GetBytes(key);
            var IV = encoding.GetBytes(iv);

            using (AesManaged rj = new AesManaged())
            {
                try
                {
                    rj.Padding = PaddingMode.Zeros;
                    rj.Mode = CipherMode.CBC;
                    rj.KeySize = 128;
                    rj.BlockSize = 128;
                    rj.Key = Key;
                    rj.IV = IV;
                    
                    var ms = new MemoryStream(cypher);

                    using (var cs = new CryptoStream(ms, rj.CreateDecryptor(Key, IV), CryptoStreamMode.Read))
                    {
                        //  cs.Write(cypher, 0, cypher.Length);
                        //cs.FlushFinalBlock();
                        //cs.Close();
                        using (var sr = new StreamReader(cs))
                        {
                            sRet = sr.ReadLine();
                        }
                    }
                }
                finally
                {
                    rj.Clear();
                }
            }
            string[] words = sRet.Split('\0');
            return words[0];
        }
    
        public static string EncryptStringFromBytes(string plainText, byte[] Key, byte[] IV)
        {
            string cipherText;
            // Check arguments.
            //if (cipherText == null || cipherText.Length <= 0)
            //    throw new ArgumentNullException("cipherText");
            if (Key == null || Key.Length <= 0)
                throw new ArgumentNullException("Key");
            if (IV == null || IV.Length <= 0)
                throw new ArgumentNullException("Key");

            // Declare the string used to hold
            // the decrypted text.
            //RijndaelManaged m;
            // Create an RijndaelManaged object
            // with the specified key and IV.
            using (AesManaged rijAlg = new AesManaged())
            {
                rijAlg.Key = Key;
                rijAlg.IV = IV;

                // Create a decrytor to perform the stream transform.
                ICryptoTransform decryptor = rijAlg.CreateEncryptor(rijAlg.Key, rijAlg.IV);

                // Create the streams used for decryption.
                using (var memoryStream = new MemoryStream())
                {
                    using (var cryptoStream = new CryptoStream(memoryStream, decryptor, CryptoStreamMode.Write))
                    {
                        using (var streamWriter = new StreamWriter(cryptoStream))
                        {
                            streamWriter.Write(plainText);
                            streamWriter.Flush();
                        }
                        //cipherText = Convert.ToBase64String(Encoding.UTF8.GetBytes(Encoding.UTF8.GetString(memoryStream.ToArray())));
                        cipherText = BitConverter.ToString(memoryStream.ToArray()).Replace("-", string.Empty);
                        //cryptoStream.FlushFinalBlock();
                    }
                }
            }
            return cipherText;
        }
    }
}
