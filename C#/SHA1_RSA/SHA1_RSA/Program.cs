using System;
using System.Numerics;


namespace SHA1_RSA
{
    class Program
    {
        static void Main(string[] args)
        {
            Prime.TestPrimeGenerator(1024, 10000);
            Prime.GetMediumPrimeGeneratorTime(1024, 1000);

            Console.ReadKey();
        }

        

        public static void rsa()
        {

        }
    }
}
