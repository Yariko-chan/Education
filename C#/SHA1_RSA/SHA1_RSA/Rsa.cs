using System;
using System.Diagnostics;
using System.Numerics;
using System.Security.Cryptography;

namespace SHA1_RSA
{
    class Rsa
    {
        private Rsa()
        {
            // static functions only
        }

        public static BigInteger sign(byte[] message, Key privateKey)
        {
            BigInteger sha1 = new BigInteger(Sha1.calculate(message));
            BigInteger sign = BigInteger.ModPow(sha1, privateKey.Exp, privateKey.Modulus);
            return sign;
        }

        public static bool checkSign(byte[] message, BigInteger sign, Key publicKey)
        {
            BigInteger sha1 = new BigInteger(Sha1.calculate(message));
            BigInteger m = BigInteger.ModPow(sign, publicKey.Exp, publicKey.Modulus);
            return sha1.Equals(m);
        }

        /// <summary>
        /// Generates public and private key
        /// </summary>
        /// <param name="size">size of keys, in bits</param>
        /// <returns></returns>
        public static RsaKeys generateRsaKeys(int size)
        {
            BigInteger p = Prime.GenerateBigPrime(size/2);
            BigInteger q = Prime.GenerateBigPrime(size/2);
            BigInteger n = p * q;
            BigInteger fn = (p - 1) * (q - 1);
            // e and rEuler should be mutually prime - max common divider is 1
            BigInteger e = Prime.GenerateMutuallyPrime(fn);
            BigInteger d = getD(e, fn);

            Debug.Assert(e * d % fn == 1);
            return new RsaKeys(d, e, n);
        }

        public static BigInteger getD(BigInteger e, BigInteger fn)
        {
            BigInteger gcd, x, y;
            GcdEuclideanAlgorithm(e, fn, out gcd, out x, out y);
            Debug.Assert(gcd == 1);
            while (x < 0)
            {
                x += fn;
            }
            Debug.Assert(e * x % fn == 1);
            return x;
        }

        /// <summary>
        /// Recursive algorithm for finding not only GCD,
        /// but also x and y coefficient such that satisfy condition:
        /// a*x + b*y = gcd(a, b)
        /// </summary>
        /// <param name="a"></param>
        /// <param name="b"></param>
        /// <param name="gcd"></param>
        /// <param name="x"></param>
        /// <param name="y"></param>
        public static void GcdEuclideanAlgorithm(BigInteger a, BigInteger b, out BigInteger gcd, out BigInteger x, out BigInteger y)
        {
            BigInteger s = new BigInteger();
            if (b == 0)
            {
                gcd = a;
                x = 1;
                y = 0;
                return;
            }

            BigInteger abmod = new BigInteger();
            BigInteger.DivRem(a, b, out abmod);
            GcdEuclideanAlgorithm(b, abmod, out gcd, out x, out y);

            s = y;
            y = x - a / b * y;
            x = s;
        }

        public static void testRsa()
        {
            RsaKeys rsaKeys = generateRsaKeys(2048);

            BigInteger m = Prime.GenerateBigPrime(160);
            BigInteger encrypted = BigInteger.ModPow(m, rsaKeys.PrivateKey.Exp, rsaKeys.PrivateKey.Modulus);
            BigInteger decrypted = BigInteger.ModPow(encrypted, rsaKeys.PublicKey.Exp, rsaKeys.PublicKey.Modulus);
            Debug.Assert(m == decrypted);
            Console.WriteLine(m == decrypted);
        }

        public static void TestRsaSign()
        {
            byte[] randomBytes = new byte[2048];
            new Random().NextBytes(randomBytes);
            RsaKeys keys = Rsa.generateRsaKeys(1024);
            BigInteger sign = Rsa.sign(randomBytes, keys.PrivateKey);
            bool check = Rsa.checkSign(randomBytes, sign, keys.PublicKey);
            Console.WriteLine(check);
        }
    }
}
