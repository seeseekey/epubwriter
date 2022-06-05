package net.seeseekey.epubwriter.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data object for one meta tag.
 */
public class MetaTag {

    private final Map<String, String> attributes;

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public MetaTag() {
        attributes = new LinkedHashMap<>();
    }

    public void addAttribute(String key, String value) {

        attributes.put(key, value);
    }
}
