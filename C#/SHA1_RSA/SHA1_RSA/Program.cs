using System;
using System.Numerics;
using System.IO;
using SHA1_RSA.Entity;

namespace SHA1_RSA
{
    class Program
    {
        private const string Create = "create";
        private const string Sign = "sign";
        private const string Check = "check";
        private const string PublicExt = ".public";
        private const string PrivateExt = ".private";
        private const string SignExt = ".sign";

        private const int KeySize = 1024;
        private const int KeySizeInBytes = KeySize / 8;

        static void Main(string[] args)
        {
//            Rsa.TestRsa();
            
//            GenerateKeys(
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\create");
//            GenerateSign(
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\create\\f5366187-e2e6-470a-868d-d5ebeafe6de4\\f5366187-e2e6-470a-868d-d5ebeafe6de4.private",
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\test.txt",
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\create");
//            CheckSign(
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\create\\test_f5366187-e2e6-470a-868d-d5ebeafe6de4.sign",
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\create\\f5366187-e2e6-470a-868d-d5ebeafe6de4\\f5366187-e2e6-470a-868d-d5ebeafe6de4.public",
//                "C:\\Users\\diana\\Dropbox\\�����\\������������\\test\\test.txt");

            
            if (args.Length < 2)
            {
                Console.WriteLine("Not enough arguments");
            }

            try
            {
                switch (args[0])
                {
                    case Create:
                        string path = args[1];
                        if (CheckGenerateParams(path))
                            GenerateKeys(path);
                        else Console.WriteLine("Incorrect args");
                        break;
                    case Sign:
                        if (CheckSignParams(args))
                            GenerateSign(args[1], args[2], args[3]);
                        else Console.WriteLine("Incorrect args");
                        break;
                    case Check:
                        if (CheckCheckParams(args))
                            CheckSign(args[1], args[2], args[3]);
                        else Console.WriteLine("Incorrect args");
                        break;
                    default:
                        Console.WriteLine("Unknown command");
                        break;
                }
            }
            catch (IOException e)
            {
                Console.WriteLine("Error reading/writing file : " + e.Message);
            }
            catch (UnauthorizedAccessException e)
            {
                Console.WriteLine("File access not authorized : " + e.Message);
            }
            
            Console.ReadKey();
        }

        private static bool CheckGenerateParams(string path)
        {
            return Directory.Exists(path) && 
                   Directory.CreateDirectory(path).Exists;
        }

        private static bool CheckSignParams(string[] args)
        {
            return args.Length >= 4 &&
                   File.Exists(args[1]) &&        // private key path
                   File.Exists(args[2]) &&        // file path
                   (Directory.Exists(args[3]) ||  // directory for saving
                    Directory.CreateDirectory(args[3]).Exists);
        }

        private static bool CheckCheckParams(string[] args)
        {
            return args.Length >= 4 &&
                   File.Exists(args[1]) && // sign path
                   File.Exists(args[2]) && // public key path
                   File.Exists(args[3]);   // signed file path
        }

        private static void GenerateKeys(string path)
        {
            String name = GenerateName();
            String keyPath = Path.Combine(path, name);
            Directory.CreateDirectory(keyPath);
            String publicKeyPath = Path.Combine(keyPath, name + PublicExt);
            String privateKeyPath = Path.Combine(keyPath, name + PrivateExt);

            RsaKeys keys = Rsa.GenerateRsaKeys(KeySize);

            // write public key file
            Key publicKey = keys.PublicKey;
            byte[] exp = publicKey.Exp.ToByteArray();
            byte[] mod = publicKey.Modulus.ToByteArray();
            byte[] b = new byte[KeySizeInBytes * 2]; // for two keys, in bytes
            Array.Copy(exp, b, exp.Length);
            Array.Copy(mod, 0, b, KeySizeInBytes + (KeySizeInBytes - mod.Length), mod.Length);
            WriteNewFile(publicKeyPath, b);

            // write private key file
            Key privateKey = keys.PrivateKey;
            exp = privateKey.Exp.ToByteArray();
            b = new byte[KeySizeInBytes * 2]; // for two keys, in bytes
            Array.Copy(exp, b, exp.Length);
            Array.Copy(mod, 0, b, KeySizeInBytes + (KeySizeInBytes - mod.Length), mod.Length);
            WriteNewFile(privateKeyPath, b);

            Console.WriteLine("Generated keys saved to " + keyPath);
        }

        private static void GenerateSign(string privateKeyPath, string filePath, string savePath)
        {
            // retrieve key
            Key privateKey = ReadKey(privateKeyPath);

            // get text bytes 
            var text = ReadFile(filePath);

            // generate sign
            BigInteger sign = Rsa.Sign(text, privateKey);
            string signFileName = Path.GetFileNameWithoutExtension(filePath) + "_" + Path.GetFileNameWithoutExtension(privateKeyPath) + SignExt;
            String signPath = Path.Combine(savePath, signFileName);
            byte[] signBytes = sign.ToByteArray();
            WriteNewFile(signPath, signBytes);

            Console.WriteLine("Sign successfully saved at " + signPath);
        }

        private static void CheckSign(string signPath, string publicKeyPath, string signedFilePath)
        {
            // get sign to biginteger
            byte[] b = ReadFile(signPath);
            BigInteger sign = new BigInteger(b);

            // get public key as object
            var publicKey = ReadKey(publicKeyPath);

            // get text bytes
            var text = ReadFile(signedFilePath);

            // check sign, print result
            bool isCorrect = Rsa.CheckSign(text, sign, publicKey);
            if (isCorrect)
            {
                Console.WriteLine("Correct sign");
            } else
            {
                Console.WriteLine("Incorrect sign");
            }

            
        }

        private static String GenerateName()
        {
            return Guid.NewGuid().ToString();
        }

        private static byte[] ReadFile(string filePath)
        {
            // todo all read write operatins with filestream and check exception
            byte[] text = File.ReadAllBytes(filePath);
            return text;
        }

        private static void WriteNewFile(string publicKeyPath, byte[] b)
        {
            FileStream fileStream = File.Create(publicKeyPath);
            fileStream.Write(b, 0, b.Length);
            fileStream.Close();
        }

        private static Key ReadKey(string privateKeyPath)
        {
            byte[] b = ReadFile(privateKeyPath);
            byte[] e = new byte[KeySizeInBytes];
            byte[] m = new byte[KeySizeInBytes];
            Array.Copy(b, e, KeySizeInBytes);
            Array.Copy(b, KeySizeInBytes, m, 0, KeySizeInBytes);
            BigInteger exp = new BigInteger(e);
            BigInteger mod = new BigInteger(m);
            Key privateKey = new Key(exp, mod);
            return privateKey;
        }
    }
}
