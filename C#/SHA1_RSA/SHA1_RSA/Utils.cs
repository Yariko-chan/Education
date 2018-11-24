using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SHA1_RSA
{
    class Utils
    {
        private Utils()
        {
            // static functions only
        }

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
