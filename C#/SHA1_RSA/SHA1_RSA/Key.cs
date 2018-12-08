using System.Numerics;

namespace SHA1_RSA
{
    class Key
    {
        public Key(BigInteger exp, BigInteger modulus)
        {
            Exp = exp;
            Modulus = modulus;
        }

        public BigInteger Exp { get; }

        public BigInteger Modulus { get; }
    }
}
