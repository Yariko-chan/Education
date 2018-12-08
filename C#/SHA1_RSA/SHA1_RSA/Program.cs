using System;
using System.Numerics;


namespace SHA1_RSA
{
    class Program
    {
        static void Main(string[] args)
        {
            byte[] randomBytes = new byte[1024];
            new Random().NextBytes(randomBytes);
            RsaKeys keys = Rsa.generateRsaKeys();
            BigInteger sign = Rsa.sign(randomBytes, keys.PrivateKey);
            bool check = Rsa.checkSign(randomBytes, sign, keys.PublicKey);
            Console.WriteLine(check);

            Console.ReadKey();
        }
    }
}
