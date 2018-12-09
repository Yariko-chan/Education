using System;
using System.CodeDom;
using System.Numerics;
using System.IO;
using System.Text;

namespace SHA1_RSA
{
    class Program
    {
        private const string Create = "create";
        private const string Sign = "sign";
        private const string Check = "check";
        private const string KeysDir = "keys";
        private const string PublicExt = ".public";
        private const string PrivateExt = ".private";
        private const string SignExt = ".sign";

        private const int KeySizeInBits = 2048;
        private const int KeySizeInBytes = 2048 / 8;

        static void Main(string[] args)
        {
//            Rsa.testRsa();
            
//            GenerateKeys(
//                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\create");
            GenerateSign(
                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\create\\3b21eb8e-ddab-4281-b3c2-595abf4962f3\\3b21eb8e-ddab-4281-b3c2-595abf4962f3.private",
                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\test.txt",
                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\create");
            CheckSign(
                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\create\\test_3b21eb8e-ddab-4281-b3c2-595abf4962f3.sign",
                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\create\\3b21eb8e-ddab-4281-b3c2-595abf4962f3\\3b21eb8e-ddab-4281-b3c2-595abf4962f3.public",
                "C:\\Users\\diana\\Dropbox\\бгуир\\криптография\\test\\test.txt");
            
            Console.ReadKey();
            /*

                        if (args.Length < 2)
                        {
                            Console.WriteLine("Not enough arguments");
                        }

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
                        Console.ReadKey();*/
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
            String name = generateName();
            String keyPath = Path.Combine(path, name);
            Directory.CreateDirectory(keyPath);
            String publicKeyPath = Path.Combine(keyPath, name + PublicExt);
            String privateKeyPath = Path.Combine(keyPath, name + PrivateExt);

            RsaKeys keys = Rsa.generateRsaKeys(KeySizeInBits);

            // write public key file
            Key publicKey = keys.PublicKey;
            byte[] exp = publicKey.Exp.ToByteArray();
            byte[] mod = publicKey.Modulus.ToByteArray();
            byte[] b = new byte[KeySizeInBytes * 2]; // for two keys, in bytes
            Array.Copy(exp, b, exp.Length);
            Array.Copy(mod, 0, b, KeySizeInBytes + (KeySizeInBytes - mod.Length), mod.Length);
            WriteFile(publicKeyPath, b);

            // write private key file
            Key privateKey = keys.PrivateKey;
            exp = privateKey.Exp.ToByteArray();
            b = new byte[KeySizeInBytes * 2]; // for two keys, in bytes
            Array.Copy(exp, b, exp.Length);
            Array.Copy(mod, 0, b, KeySizeInBytes + (KeySizeInBytes - mod.Length), mod.Length);
            WriteFile(privateKeyPath, b);

            Console.WriteLine("Generated keys saved to " + keyPath);
        }

        private static void WriteFile(string publicKeyPath, byte[] b)
        {
            FileStream fileStream = File.Create(publicKeyPath);
            fileStream.Write(b, 0, b.Length);
        }

        private static void GenerateSign(string privateKeyPath, string filePath, string savePath)
        {
            // retrieve key
            Key privateKey = ReadKey(privateKeyPath);

            // get text bytes 
            var text = ReadFile(filePath);

            // generate sign
            BigInteger sign = Rsa.sign(text, privateKey);
            string signFileName = Path.GetFileNameWithoutExtension(filePath) + "_" + Path.GetFileNameWithoutExtension(privateKeyPath) + SignExt;
            String signPath = Path.Combine(savePath, signFileName);
            FileStream fileStream = File.Create(signPath);
            byte[] signBytes = sign.ToByteArray();
            fileStream.Write(signBytes, 0, signBytes.Length);
            fileStream.Close();

            Console.WriteLine("Sign successfully saved at " + signPath);
        }

        private static void CheckSign(string signPath, string publicKeyPath, string signedFilePath)
        {
            // get sign to biginteger
            byte[] b = File.ReadAllBytes(signPath);
            BigInteger sign = new BigInteger(b);

            // get public key as object
            var publicKey = ReadKey(publicKeyPath);

            // get text bytes
            var text = ReadFile(signedFilePath);

            // check sign, print result
            bool isCorrect = Rsa.checkSign(text, sign, publicKey);
            if (isCorrect)
            {
                Console.WriteLine("Correct sign");
            } else
            {
                Console.WriteLine("Incorrect sign");
            }

            
        }

        private static String generateName()
        {
            return Guid.NewGuid().ToString();
        }

        private static byte[] ReadFile(string filePath)
        {
            // todo all read write operatins with filestream and check exception
            byte[] text = File.ReadAllBytes(filePath);
            return text;
        }

        private static Key ReadKey(string privateKeyPath)
        {
            byte[] b = File.ReadAllBytes(privateKeyPath);
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
