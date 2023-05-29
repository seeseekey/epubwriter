package net.seeseekey.epubwriter.impl;

import net.seeseekey.epubwriter.api.TocCreator;
import net.seeseekey.epubwriter.model.Content;
import net.seeseekey.epubwriter.model.EpubBook;
import net.seeseekey.epubwriter.model.EpubConstants;
import net.seeseekey.epubwriter.model.Landmark;
import net.seeseekey.epubwriter.model.TocLink;
import net.seeseekey.epubwriter.utils.DataUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the TocCreator. This follows EPUB3 standards to
 * create the Navigation Document file content.
 */
public class TocCreatorDefault implements TocCreator {

    private static final String EPUB_TYPE = "epub:type";

    /**
     * HtmlCleaner used to alter the XHTML document
     */
    private final HtmlCleaner cleaner;

    private String href = EpubConstants.TOC_FILE_NAME;

    private String tocHtml = EpubConstants.TOC_XML;

    /**
     * XmlSerializer used to format to XML String output
     */
    private final Serializer htmlSetdown;

    public TocCreatorDefault() {

        cleaner = new HtmlCleaner();
        CleanerProperties htmlProperties = cleaner.getProperties();

        htmlProperties.setOmitHtmlEnvelope(false);
        htmlProperties.setAdvancedXmlEscape(false);
        htmlProperties.setUseEmptyElementTags(true);

        htmlSetdown = new PrettyXmlSerializer(htmlProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Content createTocFromBook(EpubBook book) {

        List<TocLink> links = book.getTocLinks();

        if (book.isAutoToc()) {
            links = generateAutoLinks(book);
        }

        List<Landmark> landmarks = book.getLandmarks();
        String tocString = createTocHtml(links, landmarks, getTocHtml());

        Content toc = new Content("application/xhtml+xml", getHref(), tocString.getBytes());
        toc.setProperties("nav");
        toc.setId("toc");

        return toc;
    }

    /**
     * Builds the TOC HTML content from the EpubBook TocLinks
     *
     * @return the TOC HTML String
     */
    private String createTocHtml(List<TocLink> links, List<Landmark> landmarks, String tocHtml) {

        TagNode tagNode = cleaner.clean(tocHtml);

        // Add TOC links, if not empty
        addTocLinks(tagNode, links);

        // Add landmarks, if not empty
        addLandmarks(tagNode, landmarks);

        return htmlSetdown.getAsString(tagNode);
    }

    /**
     * Prepare and write TOC
     */
    private void addTocLinks(TagNode tagNode, List<TocLink> links) {

        TagNode navNode = tagNode.findElementByAttValue(EPUB_TYPE, "toc", true, false);

        if (DataUtils.isEmpty(links)) {
            navNode.removeFromTree();
            return;
        }

        addTocLinksSub(navNode, links);
    }

    /**
     * Recursive method adding links and sub links to the TOC navigation document
     */
    private void addTocLinksSub(TagNode tagNode, List<TocLink> links) {

        TagNode parentNode = tagNode.findElementByName("ol", true);

        for (TocLink toc : links) {

            TagNode linkNode = buildLinkNode(toc);

            if (!DataUtils.isEmpty(toc.getTocChildLinks())) {

                TagNode olNode = new TagNode("ol");
                linkNode.addChild(olNode);

                addTocLinksSub(linkNode, toc.getTocChildLinks());
            }

            parentNode.addChild(linkNode);
        }
    }

    /**
     * Adds landmarks to the Navigation Document
     */
    private void addLandmarks(TagNode tagNode, List<Landmark> landmarks) {

        TagNode navNode = tagNode.findElementByAttValue(EPUB_TYPE, "landmarks", true, false);

        if (DataUtils.isEmpty(landmarks)) {
            navNode.removeFromTree();
            return;
        }

        TagNode parentNode = navNode.findElementByName("ol", true);

        for (Landmark landmark : landmarks) {

            TagNode landmarkNode = buildLandMarkNode(landmark);
            parentNode.addChild(landmarkNode);
        }
    }

    /**
     * Builds an link tag for the TOC
     */
    private TagNode buildLinkNode(TocLink link) {

        TagNode linkNode = new TagNode("li");
        TagNode aNode = new TagNode("a");

        aNode.addAttribute("href", link.getHref());
        aNode.addChild(new ContentNode(link.getTitle()));

        if (link.getAltTitle() != null) {
            aNode.addAttribute("title", link.getHref());
        }

        linkNode.addChild(aNode);
        return linkNode;
    }

    /**
     * Builds an link tag for the TOC landmarks
     */
    private TagNode buildLandMarkNode(Landmark landmark) {

        TagNode linkNode = new TagNode("li");
        TagNode aNode = new TagNode("a");

        aNode.addAttribute("href", landmark.getHref());
        aNode.addAttribute(EPUB_TYPE, landmark.getType());
        aNode.addChild(new ContentNode(landmark.getTitle()));

        linkNode.addChild(aNode);
        return linkNode;
    }

    /**
     * Generates a list of TocLinks for all Content that should be included in
     * the Navigation Document. This will only be used when auto TOC is set
     */
    private List<TocLink> generateAutoLinks(EpubBook book) {

        List<TocLink> links = new ArrayList<>();

        for (Content content : book.getContents()) {

            if (content.isToc()) {
                links.add(new TocLink(content.getHref(), content.getId(), null));
            }
        }

        return links;
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
     * @return the tocHtml
     */
    public String getTocHtml() {
        return tocHtml;
    }

    /**
     * @param tocHtml the tocHtml to set
     */
    public void setTocHtml(String tocHtml) {
        this.tocHtml = tocHtml;
    }
}
