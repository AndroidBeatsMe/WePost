package com.nancyberry.wepost.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility class for IO operation with Stream and bytes.
 *
 * @author zhaonan
 * @version %I%, %G%
 */
public final class IOUtils {

    private static final int BUF_SIZE = 0x1000;

    private IOUtils() {
    }

    /**
     * Copy all the data from InputStream into byte[]
     *
     * @param ins InputStream
     *
     * @return byte[] data from InputStream
     * @throws IOException rased when failed to read data from InputStream
     */
    public static byte[] toByteArray(InputStream ins) throws IOException {
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        copy(ins, outs);
        return outs.toByteArray();
    }

    private static long copy(InputStream from, OutputStream to) throws IOException {
        byte[] buf = new byte[BUF_SIZE];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }
}
