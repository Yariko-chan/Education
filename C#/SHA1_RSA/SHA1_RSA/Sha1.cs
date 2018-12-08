using System;
using System.Linq;
using System.Security.Cryptography;

namespace SHA1_RSA
{
    class Sha1
    {
        private Sha1()
        {
            // static functions only
        }

        public static byte[] calculate(byte[] initBytes)
        {
            int bytesInPiece = 512 / 8;

            uint h0 = 0x67452301;
            uint h1 = 0xEFCDAB89;
            uint h2 = 0x98BADCFE;
            uint h3 = 0x10325476;
            uint h4 = 0xC3D2E1F0;

            /* PRE-PROCESSING */
            int sizeLengthBytesCount = 64 / 8;
            // get string bits
            int initLengthBytesCount = initBytes.Length;
            // create final array to meet requirement [(sizeInBits + 64bit)%512 = 0]
            var t = initLengthBytesCount + sizeLengthBytesCount + 1;
            var size = (t % bytesInPiece == 0) ? t : t + (bytesInPiece - (t % bytesInPiece));
            byte[] finalBytes = new byte[size];
            // copy string bits
            initBytes.CopyTo(finalBytes, 0);
            // append bit 1 to message
            finalBytes[initLengthBytesCount] = ByteUtils.FirstNonzeroBitByte;
            // index in array, from which size should be appended
            var sizeStartIndex = finalBytes.Length - sizeLengthBytesCount;
            // append zero's
            for (int i = initLengthBytesCount + 1; i < sizeStartIndex; i++)
            {
                finalBytes[i] = ByteUtils.FullZeroBitByte;
            }
            // append size
            byte[] sizeInBytes = BitConverter.GetBytes((long)initLengthBytesCount * 8 /*in bits*/);
            Array.Reverse(sizeInBytes);
            sizeInBytes.CopyTo(finalBytes, sizeStartIndex);

            // PROCESS MESSAGE
            // i is index of first byte in this piece
            for (int i = 0; i <= finalBytes.Length - bytesInPiece; i += bytesInPiece)
            {
                int initialWordCount = 16;
                int wordCount = 80;
                int bytesInWord = bytesInPiece / initialWordCount; // 4 byte, 32 bit
                uint[] words = new uint[wordCount];
                // copy 16 initial words
                for (int j = 0; j < initialWordCount; j++)
                {
                    byte[] wordBytes = new byte[bytesInWord];
                    int firstWordByteIndex = i + j * bytesInWord;
                    Array.Copy(finalBytes, firstWordByteIndex, wordBytes, 0, bytesInWord);
                    words[j] = ByteUtils.ByteToUInt32(wordBytes);
                }
                // adding words 17-80
                for (int j = initialWordCount; j < wordCount; j++)
                {
                    words[j] = (words[j - 3] ^ words[j - 8] ^ words[j - 14] ^ words[j - 16]);
                    words[j] = ByteUtils.leftrotate(words[j], 1);
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
                    }
                    else if (j >= 20 && j <= 39)
                    {
                        f = b ^ c ^ d;
                        k = 0x6ED9EBA1;
                    }
                    else if (j >= 40 && j <= 59)
                    {
                        f = (b & c) | (b & d) | (c & d);
                        k = 0x8F1BBCDC;
                    }
                    else
                    {
                        f = b ^ c ^ d;
                        k = 0xCA62C1D6;
                    }

                    uint temp = ByteUtils.leftrotate(a, 5) + f + e + k + words[j];
                    e = d;
                    d = c;
                    c = ByteUtils.leftrotate(b, 30);
                    b = a;
                    a = temp;
                }

                // Add this chunk's hash to result so far:
                h0 = h0 + a;
                h1 = h1 + b;
                h2 = h2 + c;
                h3 = h3 + d;
                h4 = h4 + e;
            }

            h0 = ByteUtils.reverse(h0);
            h1 = ByteUtils.reverse(h1);
            h2 = ByteUtils.reverse(h2);
            h3 = ByteUtils.reverse(h3);
            h4 = ByteUtils.reverse(h4);

            byte[] result = new byte[20];
            Array.Copy(BitConverter.GetBytes(h0), 0, result, 0, 4);
            Array.Copy(BitConverter.GetBytes(h1), 0, result, 4, 4);
            Array.Copy(BitConverter.GetBytes(h2), 0, result, 8, 4);
            Array.Copy(BitConverter.GetBytes(h3), 0, result, 12, 4);
            Array.Copy(BitConverter.GetBytes(h4), 0, result, 16, 4);
            return result;
        }

       

        public static void TestSha1()
        {
            for (int i = 0; i <= 100000; i++)
            {
                Random rand = new Random();
                byte[] bytes = new byte[i];
                rand.NextBytes(bytes);
                byte[] mysha = Sha1.calculate(bytes);
                SHA1 sha = new SHA1CryptoServiceProvider();
                byte[] realsha = sha.ComputeHash(bytes);
                if (mysha.SequenceEqual(realsha))
                {
                    Console.WriteLine("ok");
                }
                else
                {
                    Console.WriteLine("wrong value at byte array size " + i);
                    break;
                }
            }
        }
    }
}
