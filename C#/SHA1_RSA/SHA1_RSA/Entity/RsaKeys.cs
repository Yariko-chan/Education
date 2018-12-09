using System.Numerics;

namespace SHA1_RSA
{
    class RsaKeys
    {

        public RsaKeys(BigInteger publicExp, BigInteger privateExp, BigInteger modulus)
        {
            PublicKey = new Key(publicExp, modulus);
            PrivateKey = new Key(privateExp, modulus);
        }

        public Key PublicKey { get; }

        public Key PrivateKey { get; }
    }


}
