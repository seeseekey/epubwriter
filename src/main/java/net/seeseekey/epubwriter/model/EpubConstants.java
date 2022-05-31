package net.seeseekey.epubwriter.model;

/**
 * Constants used as default values in the EpubCreator and EpubBook. This is set
 * according to the EPUB 3 standards.
 */
public class EpubConstants {

    private EpubConstants() {
    }

    /**
     * Used to wrap plain text in a valid XHTML document
     */
    public static final String HTML_WRAPPER = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>{0}</title></head><body>{1}</body></html>";

    /**
     * Template of the OPF file
     */
    public static final String OPF_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <package xmlns="http://www.idpf.org/2007/opf" version="3.0" unique-identifier="uid">
               <metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
                   <dc:identifier id="uid"></dc:identifier>
                   <dc:title></dc:title>
                   <dc:language></dc:language>
                   <meta property="dcterms:modified"></meta>
               </metadata>
               <manifest>
               </manifest>
               <spine>
               </spine>
            </package>""";


    /**
     * Template of a valid table of contents navigation(TOC) XHTML document
     */
    public static final String TOC_XML = """
            <?xml version="1.0" encoding="utf-8"?>
            <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
                <head>
                    <meta charset="utf-8" />
                    <title>{0}</title>
                </head>
                <body>
                    <nav epub:type="toc" id="toc">
                        <ol></ol>
                    </nav>
                    <nav epub:type="landmarks" hidden="">
                        <ol></ol>
                    </nav>
                </body>
            </html>""";

    /**
     * Template of a valid EPUB3 container XML
     */
    public static final String CONTAINER_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <container xmlns="urn:oasis:names:tc:opendocument:xmlns:container" version="1.0">
               <rootfiles>
                  <rootfile full-path="{0}/book.opf" media-type="application/oebps-package+xml"/>
               </rootfiles>
            </container>""";

    /**
     * Default folder used to save content in
     */
    public static final String CONTENT_FOLDER = "content";

    /**
     * The default href of the toc file
     */
    public static final String TOC_FILE_NAME = "toc.xhtml";

    /**
     * The default href of the opf file
     */
    public static final String OPF_FILE_NAME = "book.opf";
}
