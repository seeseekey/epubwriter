package net.seeseekey.epubwriter.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Utility class for data specific operations
 */
public class DataUtils {

    private DataUtils() {
    }

    public static boolean isEmpty(Collection<?> list) {

        return list == null || list.isEmpty();
    }

    /**
     * Converts the input stream into a byte array
     *
     * @param stream Input stream with data
     * @return Byte array with data
     */
    public static byte[] toByteArray(InputStream stream) throws IOException {

        // Create data
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        // Read data
        byte[] data = new byte[4];
        int nRead;

        while ((nRead = stream.readNBytes(data, 0, data.length)) != 0) {
            buffer.write(data, 0, nRead);
        }

        // Flush buffer and return array
        buffer.flush();
        return buffer.toByteArray();
    }
}
