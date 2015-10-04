package uk.co.cpascoe.huffman;

import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanCompressorTests {
    @Test
    public void lengthToBits() {
        assertArrayEquals(HuffmanCompressor.lengthToBits(0), new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        assertArrayEquals(HuffmanCompressor.lengthToBits(1), new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        assertArrayEquals(HuffmanCompressor.lengthToBits(2), new byte[] { 0, 0, 0, 0, 0, 0, 0, 1, 0 });
        assertArrayEquals(HuffmanCompressor.lengthToBits(3), new byte[] { 0, 0, 0, 0, 0, 0, 0, 1, 1 });
        assertArrayEquals(HuffmanCompressor.lengthToBits(255), new byte[] { 0, 1, 1, 1, 1, 1, 1, 1, 1 });
        assertArrayEquals(HuffmanCompressor.lengthToBits(256), new byte[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    @Test
    public void lengthFromBits() {
        BitManager bitMgr = new BitManager();
        bitMgr.add(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        bitMgr.resetPosition();
        assertEquals(HuffmanCompressor.lengthFromBits(bitMgr), 0);

        bitMgr = new BitManager();
        bitMgr.add(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        bitMgr.resetPosition();
        assertEquals(HuffmanCompressor.lengthFromBits(bitMgr), 1);

    }
}
