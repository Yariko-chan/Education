using System;

namespace SHA1_RSA
{
    class ByteUtils
    {
        private ByteUtils()
        {
            // static functions only
        }

        public const byte FirstNonzeroBitByte = 0x80; // [10000000]
        public const byte LastNonzeroBitByte = 0x01;  // [00000001]
        public const byte FullZeroBitByte = 0x00;     // [00000000]

        /// <summary>
        /// Creates int from little-endian bit-array
        /// </summary>
        /// <param name="array">array in little-endian</param>
        /// <returns></returns>
        public static uint ByteToUInt32(byte[] array)
        {
            Array.Reverse(array);
            return System.BitConverter.ToUInt32(array, 0);
        }

        public static uint leftrotate(uint temp, int count)
        {
            return (temp << count) | (temp >> (32 - count));
        }

        public static uint reverse(uint i)
        {
            byte[] bytes = BitConverter.GetBytes(i);
            Array.Reverse(bytes);
            return System.BitConverter.ToUInt32(bytes, 0);
        }


    }
}
