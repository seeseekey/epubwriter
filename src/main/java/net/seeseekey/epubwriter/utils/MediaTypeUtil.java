package net.seeseekey.epubwriter.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to map file extensions to Mime types from a properties file
 */
public class MediaTypeUtil {

    private MediaTypeUtil() {
    }

    private static Map<String, String> mediaTypeMap = setMediaFromProperties();

    /**
     * Created a map of media types for specific file extensions, read from the
     * epub-mediatypes.properties file
     *
     * @return a map of all the media types in the properties file
     */
    private static Map<String, String> setMediaFromProperties() {

        Properties properties = new Properties();

        try {
            properties.load(MediaTypeUtil.class.getClassLoader().getResourceAsStream("epub-mediatypes.properties"));
        } catch (IOException ex) {
            Logger.getLogger(MediaTypeUtil.class.getName()).log(Level.SEVERE,
                    "Could not read the 'epub-mediatypes.properties' file to populate the media types. "
                            + "Please use MediaTypeUtil.setMediaTypeMap(Map<String, String> aMediaTypeMap) to set the map.", ex);
            return new HashMap<>();
        }

        Map<String, String> mappings = new HashMap<>();

        Enumeration elements = properties.propertyNames();

        while (elements.hasMoreElements()) {

            String keyProps = (String) elements.nextElement();
            String valProps = properties.getProperty(keyProps);
            mappings.put(keyProps, valProps);
        }

        return mappings;
    }

    /**
     * Returns the correct media type for a specific file extension
     *
     * @param ext the file extension
     * @return the mime type
     */
    public static String getMediaTypeFromExt(String ext) {
        return mediaTypeMap.get(ext.toLowerCase());
    }

    /**
     * Returns the correct media type for a specific file name
     *
     * @param fileName the full filename
     * @return the mime type
     */
    public static String getMediaTypeFromFilename(String fileName) {

        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        return getMediaTypeFromExt(ext);
    }

    /**
     * Allows you to override the media type map
     *
     * @param aMediaTypeMap the mediaTypeMap to set
     */
    public static void setMediaTypeMap(Map<String, String> aMediaTypeMap) {
        mediaTypeMap = aMediaTypeMap;
    }
}
