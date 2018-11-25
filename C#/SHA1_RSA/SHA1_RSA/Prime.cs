using System;
using System.Collections.Generic;
using System.Diagnostics;
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
            while (true)
            {
                Random rand = new Random();
                byte[] bytes = new byte[size / 8];
                rand.NextBytes(bytes);

                bytes[0] = (byte)(bytes[0] | ByteUtils.SecondNonzeroBitByte);// prevent leading zeros
                // make it odd
                bytes[bytes.Length - 1] = (byte)(bytes[bytes.Length - 1] | ByteUtils.LastNonzeroBitByte);

                BigInteger result = new BigInteger(bytes);
                if (result < 0) result = -result;
                //result = 2 * result - 1; // increase probability to get prime
                if (isPrime(result))
                {
                    return result;
                }
            }
        }

            

        public static bool isPrime(BigInteger b)
        {
            return CheckPrimesLess2000(b) && RabinMillerTest(b, 1);
        }

        /// <summary>
        /// Checks if number has factor among primes under 2000
        /// This check excludes >80% of composite numbers.
        ///
        /// Uses wheel factorization to find primes under 2000
        /// </summary>
        /// <param name="b"></param>
        /// <returns></returns>
        private static bool CheckPrimesLess2000(BigInteger b)
        {
            if (Mod(b, 2) == 0) return false;
            if (Mod(b, 3) == 0) return false;
            if (Mod(b, 5) == 0) return false;
            
            int[] primes = {1, 7, 11, 13, 17, 19, 23, 29};
            foreach (int prime in primes)
            {
                int k = (prime == 1) ? 1 : 0;
                BigInteger primeDivisor = 30 * k + prime;
                do
                {
                    if (Mod(b, primeDivisor) == 0) return false;
                    k++;
                    primeDivisor = 30 * k + prime;
                } while (primeDivisor < 2000);
            }

            return true;
        }

        /// <summary>
        /// Makes one iteration of Rabin-Miller test
        /// </summary>
        /// <param name="p">number to test</param>
        /// <returns></returns>
        private static bool RabinMillerTest(BigInteger p/*n*/, int rounds/*k*/)
        {
            //int b/*s*/ = (int) BigInteger.Log(p - 1, 2.0);
            //BigInteger m/*t*/ = BigInteger.Divide((p - 1), new BigInteger(Math.Pow(2.0, b)));
            int b/*s*/ = 0;
            BigInteger m/*t*/ = p - 1;
            while (m.IsEven)
            {
                m /= 2;
                b++;
            }

            for (int i = 0; i < rounds; i++)
            {
                // guaranteed to be less than p and small to speed up calculations
                int a = new Random().Next() + 2; // to prevent 0 and 1
                BigInteger z/*x*/ = BigInteger.ModPow(a, m, p);
                if (z == 1 || z == p - 1)
                {
                    continue;
                }

                for (int j = 0; j < b - 1; j++)
                {
                    z = BigInteger.ModPow(z, 2, p);
                    if (z == 1)
                    {
                        return false;
                    }

                    if (z == p - 1)
                    {
                        continue;
                    }
                }
                return false;
            }
            return true;
        }

        public static void TestPrimeGenerator(int primeSize, int sampleSize)
        {
            for (int i = 0; i < sampleSize; i++)
            {
                BigInteger b = GenerateBigPrime(primeSize);
                if (!isPrime(b))
                {
                    Console.WriteLine("Generated not prime number: " + b);
                }
            }
        }

        public static void GetMediumPrimeGeneratorTime(int primeSize, int sampleSize)
        {
            Stopwatch stopWatch;
            stopWatch = new Stopwatch();
            stopWatch.Start();
            for (int i = 0; i < sampleSize; i++)
            {
                BigInteger b = GenerateBigPrime(primeSize);
            }
            stopWatch.Stop();
            long time = ((long) stopWatch.Elapsed.TotalMilliseconds) / sampleSize;
            Console.WriteLine("Medium time = " + time + " msec");
        }

        private static BigInteger Mod(BigInteger dividend, BigInteger divisor)
        {
            BigInteger remainder;
            BigInteger.DivRem(dividend, divisor, out remainder);
            return remainder;
        }
    }
}
