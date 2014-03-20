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

        public void test() {
            Chilkat.Crypt2 crypt = new Chilkat.Crypt2();

            bool success;
            success = crypt.UnlockComponent("Anything for 30-day trial");
            if (success != true)
            {
                MessageBox.Show("Crypt component unlock failed");
                return;
            }

            //  AES is also known as Rijndael.
            crypt.CryptAlgorithm = "aes";

            //  CipherMode may be "ecb" or "cbc"
            crypt.CipherMode = "cbc";

            //  KeyLength may be 128, 192, 256
            crypt.KeyLength = 256;

            //  Pad with NULL bytes (PHP pads with NULL bytes)
            crypt.PaddingScheme = 3;

            //  EncodingMode specifies the encoding of the output for
            //  encryption, and the input for decryption.
            //  It may be "hex", "url", "base64", or "quoted-printable".
            crypt.EncodingMode = "hex";

            string ivAscii;
            ivAscii = "fedcba9876543210";
            crypt.SetEncodedIV(ivAscii, "ascii");

            //  The secret key must equal the size of the key.  For
            //  256-bit encryption, the binary secret key is 32 bytes.
            //  For 128-bit encryption, the binary secret key is 16 bytes.
            string keyAscii;
            keyAscii = "0123456789abcdef";
            crypt.SetEncodedKey(keyAscii, "ascii");

            string plainText;
            plainText = "The quick brown fox jumped over the lazy dog";

            string cipherText;
            cipherText = crypt.EncryptStringENC(plainText);
            MessageBox.Show(cipherText);

            //  Do 128-bit AES encryption:
            crypt.KeyLength = 128;
            keyAscii = "0123456789abcdef";
            crypt.SetEncodedKey(keyAscii, "ascii");

            cipherText = crypt.EncryptStringENC(plainText);
            MessageBox.Show(cipherText);

            string test = crypt.DecryptStringENC("baded608bb1bbc5caeb40e10e872bc1e");
        }
        public static string DecryptStringFromBytes(byte[] cipherText, byte[] Key, byte[] IV)
        {
            
            // Check arguments.
            if (cipherText == null || cipherText.Length <= 0)
                throw new ArgumentNullException("cipherText");
            if (Key == null || Key.Length <= 0)
                throw new ArgumentNullException("Key");
            if (IV == null || IV.Length <= 0)
                throw new ArgumentNullException("Key");

            // Declare the string used to hold
            // the decrypted text.
            string plaintext = null;
            //RijndaelManaged m;
            // Create an RijndaelManaged object
            // with the specified key and IV.
            using (AesManaged rijAlg = new AesManaged())
            {
                rijAlg.Key = Key;
                rijAlg.IV = IV;
                // Create a decrytor to perform the stream transform.
                ICryptoTransform decryptor = rijAlg.CreateDecryptor(rijAlg.Key, rijAlg.IV);

                // Create the streams used for decryption.
                using (MemoryStream msDecrypt = new MemoryStream(cipherText))
                {
                    using (CryptoStream csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read))
                    {
                        using (StreamReader srDecrypt = new StreamReader(csDecrypt))
                        {

                            // Read the decrypted bytes from the decrypting stream
                            // and place them in a string.
                            plaintext = srDecrypt.ReadToEnd();
                        }
                    }
                }
            }
            return plaintext;
        }
        /*
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
                    using (var cryptoStream = new CryptoStream(memoryStream, encryptor, CryptoStreamMode.Write))
                    {
                        using (var streamWriter = new StreamWriter(cryptoStream))
                        {
                            streamWriter.Write(plainText);
                            streamWriter.Flush();
                        }
                        //cipherText = Convert.ToBase64String(Encoding.UTF8.GetBytes(Encoding.UTF8.GetString(memoryStream.ToArray())));
                        cipherText = Convert.ToBase64String(memoryStream.ToArray());
                        //cryptoStream.FlushFinalBlock();
                    }
                }
            }
            return plaintext;
        }
        */
        public static string Encrypt(string plainText, byte[] Key, byte[] IV)
        {

            string cipherText;
            using (AesManaged rijAlg = new AesManaged())
            {
                rijAlg.Key = Key;
                rijAlg.IV = IV;
                ICryptoTransform encryptor = rijAlg.CreateEncryptor(rijAlg.Key, rijAlg.IV);

                using (var memoryStream = new MemoryStream())
                {
                    using (var cryptoStream = new CryptoStream(memoryStream, encryptor, CryptoStreamMode.Write))
                    {
                        using (var streamWriter = new StreamWriter(cryptoStream))
                        {
                            streamWriter.Write(plainText);
                            streamWriter.Flush();
                        }
                        //cipherText = Convert.ToBase64String(Encoding.UTF8.GetBytes(Encoding.UTF8.GetString(memoryStream.ToArray())));
                        cipherText = Convert.ToBase64String(memoryStream.ToArray());
                        //cryptoStream.FlushFinalBlock();
                    }
                }
            }
            return cipherText;
        }


        public static byte[] GetBytes(string str)
        {
            byte[] bytes = new byte[str.Length * sizeof(char)];
            System.Buffer.BlockCopy(str.ToCharArray(), 0, bytes, 0, bytes.Length);
            return bytes;
        }
    }
}
