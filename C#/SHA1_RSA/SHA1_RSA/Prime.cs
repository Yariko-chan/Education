using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace SHA1_RSA
{
    class Prime
    {
        private Prime()
        {
            // static functions only
        }

        /// <summary>
        /// Generates prime number of declared size in bits
        /// </summary>
        /// <param name="size">size of number in bits</param>
        /// <returns></returns>
        public static BigInteger GenerateBigPrime(int size)
        {
            Random rand = new Random();
            byte[] bytes = new byte[size / 8];
            rand.NextBytes(bytes);
            // prevent leading zeros
            bytes[0] = (byte)(bytes[0] | ByteUtils.FirstNonzeroBitByte);
            // make it odd
            bytes[bytes.Length - 1] = (byte)(bytes[bytes.Length - 1] | ByteUtils.LastNonzeroBitByte);

            BigInteger result = new BigInteger(bytes);
            while (!isPrime(result))
            {
                result -= 2;
            }
            return result;
        }

        public static bool isPrime(BigInteger b)
        {
            BigInteger i = 2;
            while (i*i < b)
            {
                BigInteger remainder;
                BigInteger.DivRem(b, i, out remainder);
                if (remainder != 0)
                {
                    return false;
                }

                i++;
            }

            return true;
        }

        public static void TestPrimeGenerator(int primeSize, int sampleSize)
        {
            for (int i = 0; i < sampleSize; i++)
            {
                BigInteger b = GenerateBigPrime(primeSize);
                if (isPrime(b))
                {
                    Console.WriteLine("Generated not prime number: " + b);
                }
            }
        }

        public static void GetMediumPrimeGeneratorTime(int primeSize, int sampleSize)
        {
            long time = 0;
            for (int i = 0; i < sampleSize; i++)
            {
                long start = DateTime.Now.Ticks;
                BigInteger b = GenerateBigPrime(primeSize);
                time += DateTime.Now.Ticks - start;
            }
            time /= sampleSize;
            Console.WriteLine("Medium time = " + time + " msec");
        }
    }
}
