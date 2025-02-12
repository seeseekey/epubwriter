package net.seeseekey.epubwriter.model;

import net.seeseekey.epubwriter.utils.DataUtils;
import net.seeseekey.epubwriter.utils.MediaTypeUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Representation of content in the book
 */
public class Content {

    /**
     * The MediaType get set as the media-type attribute value in the OPF
     * manifest items
     */
    private String mediaType;

    /**
     * The HREF get set as the HREF attribute value in the OPF manifest items
     */
    private String href;

    /**
     * The id get set as the id attribute value in the OPF manifest items and
     * the id ref in the spine. This must be unique
     */
    private String id;

    /**
     * The properties get set as the properties attribute value in the OPF
     * manifest items. This is used for the TOC and cover-image content objects
     */
    private String properties;

    /**
     * The file content
     */
    private byte[] content;

    /**
     * Specifies if it is a linear item in the spine
     */
    private boolean linear = true;

    /**
     * Specifies if it must be added to the spine
     */
    private boolean spine = true;

    /**
     * Specifies if it must be added to the TOC (Navigation Document)
     */
    private boolean toc = false;

    /**
     * The fallback content
     */
    private Content fallBack;

    /**
     * Creates new instance of Content
     *
     * @param mediaType the mime type
     * @param href      the link to the content item
     * @param content   the file byte array
     */
    public Content(String mediaType, String href, byte[] content) {

        if (mediaType == null) {
            this.mediaType = MediaTypeUtil.getMediaTypeFromFilename(href);
        } else {
            this.mediaType = mediaType;
        }

        this.href = href;
        this.content = content;
    }

    /**
     * Creates new instance of Content
     *
     * @param href    the link to the content item
     * @param content the file byte array
     */
    public Content(String href, byte[] content) {
        this(MediaTypeUtil.getMediaTypeFromFilename(href), href, content);
    }

    /**
     * Creates a new instance of Content
     *
     * @param mediaType  the mime type
     * @param href       the link to the content item
     * @param id         used as the id attribute value in the OPF manifest items and
     *                   the id ref in the spine
     * @param properties the properties attribute value in the OPF
     * @param content    the file byte array
     */
    public Content(String mediaType, String href, String id, String properties, byte[] content) {
        this(mediaType, href, content);
        this.id = id;
        this.properties = properties;
        this.content = content;
    }

    /**
     * Creates a new instance of Content
     *
     * @param href       the link to the content item
     * @param id         used as the id attribute value in the OPF manifest items and
     *                   the id ref in the spine
     * @param properties the properties attribute value in the OPF
     * @param content    the file byte array
     */
    public Content(String href, String id, String properties, byte[] content) {
        this(MediaTypeUtil.getMediaTypeFromFilename(href), href, id, properties, content);
    }

    /**
     * Creates new instance of Content
     *
     * @param mediaType the mime type
     * @param href      the link to the content item
     * @param content   the file byte array
     * @throws java.io.IOException if content could not be converted to byte
     *                             array
     */
    public Content(String mediaType, String href, InputStream content) throws IOException {
        this(mediaType, href, DataUtils.toByteArray(content));
    }

    /**
     * Creates new instance of Content
     *
     * @param href    the link to the content item
     * @param content the file byte array
     * @throws java.io.IOException if content could not be converted to byte
     *                             array
     */
    public Content(String href, InputStream content) throws IOException {
        this(MediaTypeUtil.getMediaTypeFromFilename(href), href, DataUtils.toByteArray(content));
    }

    /**
     * Creates a new instance of Content
     *
     * @param mediaType  the mime type
     * @param href       the link to the content item
     * @param id         used as the id attribute value in the OPF manifest items and
     *                   the id ref in the spine
     * @param properties the properties attribute value in the OPF
     * @param content    the file byte array
     * @throws java.io.IOException if content could not be converted to byte
     *                             array
     */
    public Content(String mediaType, String href, String id, String properties, InputStream content) throws IOException {
        this(mediaType, href, id, properties, DataUtils.toByteArray(content));
    }

    /**
     * Creates a new instance of Content
     *
     * @param href       the link to the content item
     * @param id         used as the id attribute value in the OPF manifest items and
     *                   the id ref in the spine
     * @param properties the properties attribute value in the OPF
     * @param content    the file byte array
     * @throws java.io.IOException if content could not be converted to byte
     *                             array
     */
    public Content(String href, String id, String properties, InputStream content) throws IOException {
        this(MediaTypeUtil.getMediaTypeFromFilename(href), href, id, properties, DataUtils.toByteArray(content));
    }

    /**
     * Indicates whether a fallback content has been set
     *
     * @return the fallback flag
     */
    public boolean hasFallBack() {
        return fallBack != null;
    }

    /**
     * @return the mediaType
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * @param mediaType the mediaType to set
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * @return the HREF
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the HREF to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the properties
     */
    public String getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return the linear
     */
    public boolean isLinear() {
        return linear;
    }

    /**
     * @param linear the linear to set
     */
    public void setLinear(boolean linear) {
        this.linear = linear;
    }

    /**
     * @return the spine
     */
    public boolean isSpine() {
        return spine;
    }

    /**
     * @param spine the spine to set
     */
    public void setSpine(boolean spine) {
        this.spine = spine;
    }

    /**
     * @return the TOC
     */
    public boolean isToc() {
        return toc;
    }

    /**
     * @param toc the TOC to set
     */
    public void setToc(boolean toc) {
        this.toc = toc;
    }

    /**
     * @return the fallBack
     */
    public Content getFallBack() {
        return fallBack;
    }

    /**
     * The spine parameter will be set to false as we do not want to add the
     * fallback content to the spine. To override default behavior call
     * getFallBack to set spine property.
     *
     * @param fallBack the fallBack to set
     */
    public void setFallBack(Content fallBack) {
        fallBack.setSpine(false);
        this.fallBack = fallBack;
    }
}
