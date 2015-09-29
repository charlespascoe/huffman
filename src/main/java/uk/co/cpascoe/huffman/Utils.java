package uk.co.cpascoe.huffman;

public static class Utils {
    /**
     * Returns the big-endian representation of a byte value
     *
     * @param  val The value to convert to bits
     * @return     The big-endian array of bits (as bytes)
     */
    public byte[] toBits(byte val) {
        byte[] bits = new byte[8];

        byte mask = 1;

        for (int i = 7; i >= 0; i--) {
            bits[i] = (byte)((val & mask) > 0 ? 1 : 0);
            mask = mask >> 1;
        }

        return bits;
    }
}
