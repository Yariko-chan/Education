using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace SHA1_RSA
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Enter msg");
            var s = Console.ReadLine();
            s = Sha1(s);
            Console.WriteLine(s);

            Console.ReadKey();
        }

        private static string Sha1(string s)
        {
            int bytesInPiece = 512 / 8;

            uint h0 = 0x67452301;
            uint h1 = 0xEFCDAB89;
            uint h2 = 0x98BADCFE;
            uint h3 = 0x10325476;
            uint h4 = 0xC3D2E1F0;

            /* PRE-PROCESSING */
            int sizeLengthBytesCount = 64 / 8;
            byte firstAdditionalByte = 0x80;  // [10000000]
            byte zeroesAdditionalByte = 0x00; // [00000000]
            // get string bits
            byte[] initStringInBytes = Encoding.UTF8.GetBytes(s);
            int initLengthBytesCount = initStringInBytes.Length;
            // create final array to meet requirement [sizeInBits%512 = 0]
            var t = initLengthBytesCount + sizeLengthBytesCount;
            byte[] finalStringInBytes = new byte[t + (bytesInPiece - (t % bytesInPiece))];
            // copy string bits
            initStringInBytes.CopyTo(finalStringInBytes, 0);
            // append bit 1 to message
            finalStringInBytes[initLengthBytesCount] = firstAdditionalByte;
            // index in array, from which size should be appended
            var sizeStartIndex = finalStringInBytes.Length - sizeLengthBytesCount;
            // append zero's
            for (int i = initLengthBytesCount + 1; i < sizeStartIndex; i++)
            {
                finalStringInBytes[i] = zeroesAdditionalByte;
            }
            // append size
            byte[] sizeInBytes = BitConverter.GetBytes((long) initLengthBytesCount * 8 /*in bits*/);
            sizeInBytes.CopyTo(finalStringInBytes, sizeStartIndex);


            // PROCESS MESSAGE
            // i is index of first byte in this piece
            for (int i = 0; i < finalStringInBytes.Length - bytesInPiece; i += bytesInPiece)
            {
                int initialWordCount = 16;
                int wordCount = 80;
                int bytesInWord = bytesInPiece / initialWordCount; // 4 byte, 32 bit
                byte[] words = new byte[wordCount * bytesInWord];
                // copy 16 initial words
                Array.Copy(finalStringInBytes, i, words, 0, bytesInPiece);
                // adding words till 17-80
                for (int j = initialWordCount; j < wordCount; j++)
                {
                    byte[] result = new byte[bytesInWord];
                    byte[] temp = new byte[bytesInWord];
                    // w[i - 3]
                    Array.Copy(words, bytesInWord * (j - 3), result, 0, bytesInWord);
                    // w[i - 3] ^ w[i - 8]
                    Array.Copy(words, bytesInWord * (j - 8), temp, 0, bytesInWord);
                    Xor(result, temp);
                    // w[i - 3] ^ w[i - 8] ^ w[i - 14]
                    Array.Copy(words, bytesInWord * (j - 14), temp, 0, bytesInWord);
                    Xor(result, temp);
                    // w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16]
                    Array.Copy(words, bytesInWord * (j - 16), temp, 0, bytesInWord);
                    Xor(result, temp);
                    // leftrotate 1
                    RotateLeft(result, 1);
                    // copy to words array
                    Array.Copy(result, 0, words, j, bytesInWord);
                }

                // hash values for this piece
                uint a = h0;
                uint b = h1;
                uint c = h2;
                uint d = h3;
                uint e = h4;
                uint k, f;

                // main cycle
                for (int j = 0; j < wordCount; j++)
                {
                    if (j >= 0 && j <= 19)
                    {
                        f = (b & c) | ((~b) & d);
                        k = 0x5A827999;
                    } else if (j >= 20 && j <= 39)
                    {
                        f = b ^ c ^ d;
                        k = 0x6ED9EBA1;
                    } else if (j >= 40 && j <= 59)
                    {
                        f = (b & c) | (b & d) | (c & d);
                        k = 0x8F1BBCDC;
                    } else
                    {
                        f = b ^ c ^ d;
                        k = 0xCA62C1D6;
                    }

                    uint temp = leftrotate(a, 5) + f + e + k +
                }
            }
            
            return s;
        }

        /// <summary>
        /// Computes xor of bytes array and writes result to first array
        /// If no additional arrays, then first array not changed.
        /// </summary>
        /// <param name="byteArray1"></param>
        /// <param name="additionals"></param>
        /// <returns></returns>
        private static byte[] Xor(byte[] byteArray1, params byte[][] additionals)
        {
            for (int i = 0; i < byteArray1.Length; i++)
            {
                foreach (byte[] b in additionals)
                {
                    if (b.Length > i)
                    {
                        byteArray1[i] = (byte)(byteArray1[i] ^ b[i]);
                    }
                    
                }
            }

            return byteArray1;
        }

        /// <summary>
        /// Rotates byte array left
        /// </summary>
        /// <param name="array">Array to left rotate</param>
        /// <param name="count">Cpunt of bits to rotate</param>
        private static void RotateLeft(byte[] array, int count)
        {
            if (array.Length != 4)
            {
                throw new ArgumentException("Byte array for int32 should have size = 4");
            }
            uint temp = ByteToUInt32(array);
            temp = leftrotate(temp, count);
            BitConverter.GetBytes(temp).CopyTo(array, 0);
        }

        private static uint ByteToUInt32(byte[] array)
        {
            return System.BitConverter.ToUInt32(array, 0);
        }

        private static uint leftrotate(uint temp, int count)
        {
            return (temp << count) | (temp >> (32 - count));
        }
    }
}
