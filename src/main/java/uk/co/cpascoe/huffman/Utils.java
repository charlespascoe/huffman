package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;
import java.io.InputStream;
import java.io.IOException;

public class Utils {
    /**
     * Returns the big-endian representation of a byte value
     *
     * @param  val The value to convert to bits
     * @return     The big-endian array of bits (as bytes)
     */
    public static byte[] toBits(byte val) {
        byte[] bits = new byte[8];

        byte mask = 1;

        for (int i = 7; i >= 0; i--) {
            bits[i] = (byte)((val & mask) > 0 ? 1 : 0);
            mask = (byte)(mask << 1);
        }

        return bits;
    }

    public static byte byteFromBits(byte[] bits) {
        // TODO: Check bits length

        byte val = 0;

        byte bitValue = 1;

        for (int i = 7; i >= 0; i--) {
            val += bits[i] * bitValue;
            bitValue = (byte)(bitValue << 1);
        }

        return val;
    }

    public static byte[] readAllBytesFromStream(InputStream strm) throws IOException {
        byte[] data = new byte[0];
        byte[] buffer = new byte[65536];
        int readCount = 0;
        int prevLength = 0;

        while (true) {
            readCount = strm.read(buffer, 0, buffer.length);
            if (readCount == -1) { break; }

            prevLength = data.length;

            data = Arrays.copyOf(data, data.length + readCount);
            System.arraycopy(buffer, 0, data, prevLength, readCount);
        }

        return data;
    }

    public static int toUnsignedByte(byte b) {
        return b + (b < 0 ? 256 : 0);
    }
}
