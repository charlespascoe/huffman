package uk.co.cpascoe.huffman;

import org.junit.Test;
import static org.junit.Assert.*;


public class UtilsTests {
    @Test
    public void byteToBits() {
        assertArrayEquals(Utils.toBits((byte)0), new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        assertArrayEquals(Utils.toBits((byte)1), new byte[] { 0, 0, 0, 0, 0, 0, 0, 1 });
        assertArrayEquals(Utils.toBits((byte)2), new byte[] { 0, 0, 0, 0, 0, 0, 1, 0 });
        assertArrayEquals(Utils.toBits((byte)3), new byte[] { 0, 0, 0, 0, 0, 0, 1, 1 });
        assertArrayEquals(Utils.toBits((byte)127), new byte[] { 0, 1, 1, 1, 1, 1, 1, 1 });
        assertArrayEquals(Utils.toBits((byte)128), new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 });
        assertArrayEquals(Utils.toBits((byte)255), new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 });
    }

    @Test
    public void intToBits() {
        assertArrayEquals(Utils.toBits(0, 1), new byte[] { 0 });
        assertArrayEquals(Utils.toBits(0, 5), new byte[] { 0, 0, 0, 0, 0 });
        assertArrayEquals(Utils.toBits(1, 5), new byte[] { 0, 0, 0, 0, 1 });
        assertArrayEquals(Utils.toBits(3, 5), new byte[] { 0, 0, 0, 1, 1 });
        assertArrayEquals(Utils.toBits(127, 8), new byte[] { 0, 1, 1, 1, 1, 1, 1, 1 });
        assertArrayEquals(Utils.toBits(128, 8), new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 });
        assertArrayEquals(Utils.toBits(255, 8), new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 });
        assertArrayEquals(Utils.toBits(256, 8), new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        assertArrayEquals(Utils.toBits(256, 32), new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    @Test
    public void concat() {
        assertArrayEquals(Utils.concat(new byte[0], new byte[0]), new byte[0]);
        assertArrayEquals(Utils.concat(new byte[0], new byte[0], new byte[0]), new byte[0]);
    }
}
