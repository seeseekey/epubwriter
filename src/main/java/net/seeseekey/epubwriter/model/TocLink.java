package net.seeseekey.epubwriter.model;

import java.util.List;

/**
 * Represents a link in the table of contents, this object can have nested child
 * links
 */
public class TocLink {

    /**
     * The href
     */
    private String href;

    /**
     * The displayed text
     */
    private String title;

    /**
     * Can be set for accessibility if the pronunciation of a link heading may
     * be ambiguous due to embedded images, math content, or other content
     * without intrinsic text. Will be included as an 'title' attribute
     */
    private String altTitle;

    /**
     * Any nested links
     */
    private List<TocLink> tocChildLinks;

    public TocLink(String href, String title, String altTitle) {
        this.href = href;
        this.title = title;
        this.altTitle = altTitle;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the altTitle
     */
    public String getAltTitle() {
        return altTitle;
    }

    /**
     * @param altTitle the altTitle to set
     */
    public void setAltTitle(String altTitle) {
        this.altTitle = altTitle;
    }

    /**
     * @return the tocChildLinks
     */
    public List<TocLink> getTocChildLinks() {
        return tocChildLinks;
    }

    /**
     * @param tocChildLinks the tocChildLinks to set
     */
    public void setTocChildLinks(List<TocLink> tocChildLinks) {
        this.tocChildLinks = tocChildLinks;
    }
}
