using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.Security;
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

        public static string myDecrypt(string cipherText)
        {
            // encryption key... 
            var key = Encoding.UTF8.GetBytes("0123456789abcdef");
            var iv = Encoding.UTF8.GetBytes("fedcba9876543210");
            var cipher = CipherUtilities.GetCipher("AES/CBC/NoPadding");
            ParametersWithIV par = new ParametersWithIV(new KeyParameter(key), iv);
            // Initialise the cipher... 
            cipher.Init(false, par);
            var bytes = cipher.DoFinal(StringToByteArray(cipherText));
            string result = Encoding.UTF8.GetString(bytes, 0, bytes.Length); //result is Always \0\0\0\0\0\0\0\0\0\0\0.... 
            string[] words = result.Split('\0');
            return words[0];
        }

        public static string EncryptStringFromBytes(string plainText)
        {
            string cipherText;
            // Check arguments.
            //if (cipherText == null || cipherText.Length <= 0)
            //    throw new ArgumentNullException("cipherText");
            var Key = Encoding.UTF8.GetBytes("0123456789abcdef");
            var IV = Encoding.UTF8.GetBytes("fedcba9876543210");
            
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
