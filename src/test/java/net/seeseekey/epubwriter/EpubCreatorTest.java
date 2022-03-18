package net.seeseekey.epubwriter;

import net.seeseekey.epubwriter.model.EpubBook;
import net.seeseekey.epubwriter.utils.Logging;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpubCreatorTest {

    private static final Logger log = Logging.getLogger();

    @Test
    public void testEpubCreate() {

        try (FileOutputStream file = new FileOutputStream("test.epub")) {

            EpubBook book = new EpubBook("en", UUID.randomUUID().toString(), "Samuel Test Book", "Samuel Holtzkampf");

            book.addContent(this.getClass().getResourceAsStream("/epub30-overview.xhtml"), "application/xhtml+xml", "xhtml/epub30-overview.xhtml", true, true).setId("Overview");
            book.addContent(this.getClass().getResourceAsStream("/idpflogo_web_125.jpg"), "image/jpeg", "img/idpflogo_web_125.jpg", false, false);
            book.addContent(this.getClass().getResourceAsStream("/epub-spec.css"), "text/css", "css/epub-spec.css", false, false);

            book.addTextContent("TestHtml", "xhtml/samuelTest2.xhtml", "Samuel test one two four!!!!!\nTesting two").setToc(true);
            book.addTextContent("TestHtml", "xhtml/samuelTest.xhtml", "Samuel test one two three\nTesting two").setToc(true);

            book.addCoverImage(IOUtils.toByteArray(this.getClass().getResourceAsStream("/P1010832.jpg")), "image/jpeg", "images/P1010832.jpg");

            book.writeToStream(file);

            // TODO real tests to see if document correct, this is just to test that creation is successful
            assertEquals("test", "test");
        } catch (Exception e) {
            log.error("Throw exception due test", e);
            assertEquals("test", "test1");
        }
    }
}
