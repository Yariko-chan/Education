using System;
using System.Diagnostics;
using System.Numerics;

namespace SHA1_RSA
{
    class Prime
    {
        private Prime()
        {
            // static functions only
        }

        /// <summary>
        /// Generates BigInteger, whose max common divisor with src is 1
        /// </summary>
        /// <param name="src"></param>
        /// <returns></returns>
        public static BigInteger GenerateMutuallyPrime(BigInteger src)
        {
            int size = src.ToByteArray().Length;
            while (true)
            {
                Random rand = new Random();
                byte[] bytes = new byte[size];
                rand.NextBytes(bytes);
                BigInteger result = new BigInteger(bytes);
                if (result < 0) result = -result;
                if (BigInteger.GreatestCommonDivisor(src, result) == 1)
                {
                    return result;
                }
            }
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
                if (isPrime(result))
                {
                    return result;
                }
            }
        }

            

        public static bool isPrime(BigInteger b)
        {
            // 5 times enough
            return CheckPrimesLess2000(b) && RabinMillerTest(b, 30);
        }

        /// <summary>
        /// Checks if number has factor among primes under 2000
        /// This check excludes >80% of composite numbers.
        ///
        /// Uses wheel factorization to find primes under 2000
        /// </summary>
        /// <param name="b">number to test</param>
        /// <returns>is number prime</returns>
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
        /// Makes [rounds] of Rabin-Miller test
        /// </summary>
        /// <param name="p">number to test</param>
        /// <param name="rounds">count of test</param>
        /// <returns></returns>
        private static bool RabinMillerTest(BigInteger p, int rounds)
        {
            int b = 0;
            BigInteger m = p - 1;
            while (m.IsEven)
            {
                m /= 2;
                b++;
            }

            for (int i = 0; i < rounds; i++)
            {
                // guaranteed to be less than p and small to speed up calculations
                int a = new Random().Next() + 2; // to prevent 0 and 1
                BigInteger z = BigInteger.ModPow(a, m, p);
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
