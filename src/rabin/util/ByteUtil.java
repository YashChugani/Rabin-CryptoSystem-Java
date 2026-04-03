/**
 * Utility class for byte array operations.
 *
 * Used heavily in encoding/decoding pipeline:
 *
 * Encoding:
 *      [HEADER] || [MESSAGE] || [HASH]
 *
 * This class helps construct and split byte arrays.
 */

package rabin.util;

import java.util.Arrays;

public class ByteUtil {

    /**
     * Concatenates three byte arrays.
     *
     * Result:
     *      result = a || b || c
     */
    public static byte[] concat(byte[] a, byte[] b, byte[] c) {
        byte[] result = new byte[a.length + b.length + c.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        System.arraycopy(c, 0, result, a.length + b.length, c.length);
        return result;
    }

    /**
     * Extracts a portion of a byte array.
     *
     * Used during decoding to split:
     *      [HEADER][MESSAGE][HASH]
     */
    public static byte[] subArray(byte[] arr, int start, int end) {
        return Arrays.copyOfRange(arr, start, end);
    }
}