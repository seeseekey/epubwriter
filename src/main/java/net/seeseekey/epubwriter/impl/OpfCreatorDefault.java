package net.seeseekey.epubwriter.impl;

import net.seeseekey.epubwriter.EpubConstants;
import net.seeseekey.epubwriter.api.OpfCreator;
import net.seeseekey.epubwriter.model.Content;
import net.seeseekey.epubwriter.model.EpubBook;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.TagNode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Default implementation of the OpfCreator. This follows EPUB3 standards to
 * create the OPF file content.
 */
public class OpfCreatorDefault implements OpfCreator {

    /**
     * The template XML used to create the OPF file. This is settable if a
     * different template needs to be used.
     */
    private String opfXML = EpubConstants.OPF_XML;

    /**
     * HtmlCleaner used to clean the XHTML document
     */
    private final HtmlCleaner cleaner;

    /**
     * XmlSerializer used to format to XML String output
     */
    private final Serializer htmlSetdown;

    public OpfCreatorDefault() {

        cleaner = new HtmlCleaner();
        CleanerProperties htmlProperties = cleaner.getProperties();

        htmlProperties.setOmitHtmlEnvelope(true);
        htmlProperties.setAdvancedXmlEscape(false);
        htmlProperties.setUseEmptyElementTags(true);

        htmlSetdown = new PrettyXmlSerializer(htmlProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOpfString(EpubBook book) {

        TagNode tagNode = cleaner.clean(opfXML);

        addMetaDataTags(tagNode, book);
        addManifestTags(tagNode, book);
        addSpineTags(tagNode, book);

        return htmlSetdown.getAsString(tagNode);
    }

    /**
     * Add the required meta data
     *
     * @param tagNode the HTML tagNode of the OPF template
     * @param book    the EpubBook
     */
    private void addMetaDataTags(TagNode tagNode, EpubBook book) {

        TagNode metaNode = tagNode.findElementByName("metadata", true);

        addNodeData(metaNode, "dc:identifier", book.getId());
        addNodeData(metaNode, "dc:title", book.getTitle());
        addNodeData(metaNode, "dc:language", book.getLanguage());
        addNodeData(metaNode, "meta", new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").format(new Date()));

        if (book.getAuthor() != null) {

            TagNode creatorNode = new TagNode("dc:creator");
            creatorNode.addChild(new ContentNode(book.getAuthor()));
            metaNode.addChild(creatorNode);
        }
    }

    /**
     * Adds a item tag to the manifest for each Content object.
     * <p>
     * The manifest contains all Content that will be added to the EPUB as files
     *
     * @param tagNode the HTML tagNode of the OPF template
     * @param book    the EpubBook
     */
    private void addManifestTags(TagNode tagNode, EpubBook book) {

        TagNode manifestNode = tagNode.findElementByName("manifest", true);

        for (Content content : book.getContents()) {
            manifestNode.addChild(buildItemNode(content));
        }
    }

    /**
     * Builds an item tag from the Content object
     */
    private TagNode buildItemNode(Content content) {

        TagNode itemNode = new TagNode("item");

        itemNode.addAttribute("href", content.getHref());
        itemNode.addAttribute("id", content.getId());
        itemNode.addAttribute("media-type", content.getMediaType());

        if (content.getProperties() != null) {
            itemNode.addAttribute("properties", content.getProperties());
        }

        if (content.hasFallBack()) {
            itemNode.addAttribute("fallback", content.getFallBack().getId());
        }

        return itemNode;
    }

    /**
     * Adds item ref tags for all Content objects that must be added to the
     * spine.
     * <p>
     * The spine contains all the resources that will be shown when reading the
     * book from start to end
     *
     * @param tagNode the HTML tagNode of the OPF template
     * @param book    the EpubBook
     */
    private void addSpineTags(TagNode tagNode, EpubBook book) {

        TagNode spineNode = tagNode.findElementByName("spine", true);

        for (Content content : book.getContents()) {
            if (content.isSpine()) {
                spineNode.addChild(buildItemrefNode(content));
            }
        }
    }

    /**
     * Builds an item ref tag from the Content object
     */
    private TagNode buildItemrefNode(Content content) {

        TagNode itemNode = new TagNode("itemref");

        itemNode.addAttribute("idref", content.getId());

        if (!content.isLinear()) {
            itemNode.addAttribute("linear", "no");
        }

        return itemNode;
    }

    /**
     * Adds a ContentNode (value) with to a child element of the TagNode
     *
     * <elementName>{value}<elementName>
     */
    private void addNodeData(TagNode tagNode, String elementName, String value) {

        TagNode editNode = tagNode.findElementByName(elementName, true);
        editNode.addChild(new ContentNode(value));
    }

    /**
     * The base XML used for the OPF file.
     *
     * @return the OPF XML text
     */
    public String getOpfXML() {
        return opfXML;
    }

    /**
     * The base XML used for the OPF file. This is optional as there is a EPUB3
     * standard default, but it can be overridden.
     *
     * @param opfXML the OPF XML to set
     */
    public void setOpfXML(String opfXML) {
        this.opfXML = opfXML;
    }
}
