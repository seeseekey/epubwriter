package net.seeseekey.epubwriter.utils;

import net.seeseekey.epubwriter.model.EpubConstants;
import net.seeseekey.epubwriter.api.OpfCreator;
import net.seeseekey.epubwriter.api.TocCreator;
import net.seeseekey.epubwriter.impl.OpfCreatorDefault;
import net.seeseekey.epubwriter.impl.TocCreatorDefault;
import net.seeseekey.epubwriter.model.Content;
import net.seeseekey.epubwriter.model.EpubBook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The EpubWriter creates the EPUB zip bundle.
 */
public class EpubWriter {

    private String containerXML = EpubConstants.CONTAINER_XML;

    private String contentFolder = EpubConstants.CONTENT_FOLDER;

    private String opfFileName = EpubConstants.OPF_FILE_NAME;

    private OpfCreator opfCreator = new OpfCreatorDefault();

    private TocCreator tocCreator = new TocCreatorDefault();

    /**
     * Writes the EPUB book zip container and contents to a file
     *
     * @param book     the EpubBook
     * @param fileName name of the file to be written
     * @throws IOException if file could not be written
     */
    public void writeEpubToFile(EpubBook book, String fileName) throws IOException {

        try (FileOutputStream file = new FileOutputStream(fileName)) {
            writeEpubToStream(book, file);
        }
    }

    /**
     * Writes the EPUB book zip container and contents to an OutputStream
     *
     * @param book the EpubBook
     * @param out  the OutputStream to write to
     * @throws IOException if file could not be written
     */
    public void writeEpubToStream(EpubBook book, OutputStream out) throws IOException {

        try (ZipOutputStream resultStream = new ZipOutputStream(out)) {

            List<Content> contents = book.getContents();

            addMimeType(resultStream);
            contents.add(0, getTocCreator().createTocFromBook(book));

            addStringToZip(resultStream, "META-INF/container.xml", MessageFormat.format(containerXML, contentFolder));
            addStringToZip(resultStream, contentFolder + "/" + getOpfFileName(), getOpfCreator().createOpfString(book));

            addContent(resultStream, contents);
        }
    }

    /**
     * Adds the zip/EPUB mime type to the EPUB zip file
     */
    private void addMimeType(ZipOutputStream resultStream) throws IOException {

        ZipEntry mimetypeZipEntry = new ZipEntry("mimetype");
        mimetypeZipEntry.setMethod(ZipEntry.STORED);

        byte[] mimetypeBytes = "application/epub+zip".getBytes(StandardCharsets.UTF_8);
        mimetypeZipEntry.setSize(mimetypeBytes.length);
        mimetypeZipEntry.setCrc(calculateCrc(mimetypeBytes));

        resultStream.putNextEntry(mimetypeZipEntry);
        resultStream.write(mimetypeBytes);
    }

    /**
     * Calculates the CRC32 of data for the zip entry
     */
    private long calculateCrc(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    /**
     * Adds string content as a zip entry with the specified file name
     */
    private void addStringToZip(ZipOutputStream resultStream, String fileName, String content) throws IOException {

        resultStream.putNextEntry(new ZipEntry(fileName));

        Writer out = new OutputStreamWriter(resultStream, StandardCharsets.UTF_8);
        out.write(content);
        out.flush();
    }

    /**
     * Adds the content objects zip entries
     */
    private void addContent(ZipOutputStream resultStream, List<Content> contents) throws IOException {

        for (Content content : contents) {

            resultStream.putNextEntry(new ZipEntry(contentFolder + "/" + content.getHref()));
            resultStream.write(content.getContent());
        }
    }

    /**
     * @return the CONTAINER_XML
     */
    public String getContainerXML() {
        return containerXML;
    }

    /**
     * @param containerXML the CONTAINER_XML to set
     */
    public void setContainerXML(String containerXML) {
        this.containerXML = containerXML;
    }

    /**
     * @return the CONTENT_FOLDER
     */
    public String getContentFolder() {
        return contentFolder;
    }

    /**
     * @param contentFolder the CONTENT_FOLDER to set
     */
    public void setContentFolder(String contentFolder) {
        this.contentFolder = contentFolder;
    }

    /**
     * @return the OPF_FILE_NAME
     */
    public String getOpfFileName() {
        return opfFileName;
    }

    /**
     * @param opfFileName the OPF_FILE_NAME to set
     */
    public void setOpfFileName(String opfFileName) {
        this.opfFileName = opfFileName;
    }

    /**
     * @return the opfCreator
     */
    public OpfCreator getOpfCreator() {
        return opfCreator;
    }

    /**
     * @param opfCreator the opfCreator to set
     */
    public void setOpfCreator(OpfCreator opfCreator) {
        this.opfCreator = opfCreator;
    }

    /**
     * @return the tocCreator
     */
    public TocCreator getTocCreator() {
        return tocCreator;
    }

    /**
     * @param tocCreator the tocCreator to set
     */
    public void setTocCreator(TocCreator tocCreator) {
        this.tocCreator = tocCreator;
    }
}
