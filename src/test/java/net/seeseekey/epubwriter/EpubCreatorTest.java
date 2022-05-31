package net.seeseekey.epubwriter;

import net.seeseekey.epubwriter.model.EpubBook;
import net.seeseekey.epubwriter.utils.Logging;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpubCreatorTest {

    private static final Logger log = Logging.getLogger();

    @Test
    void testEpubCreate() {

        try (FileOutputStream file = new FileOutputStream("test.epub")) {

            // Preparation
            EpubBook book = new EpubBook("en", UUID.randomUUID().toString(), "Der Fall der Welt", "Avonia");

            book.addContent(this.getClass().getResourceAsStream("/chapter-1.xhtml"), "application/xhtml+xml", "xhtml/chapter-1.xhtml", true, true).setId("Lorem");
            book.addContent(this.getClass().getResourceAsStream("/chapter-2.xhtml"), "application/xhtml+xml", "xhtml/chapter-2.xhtml", true, true).setId("Ipsum");

            book.addContent(this.getClass().getResourceAsStream("/style.css"), "text/css", "xhtml/css/style.css", false, false);

            book.addTextContent("TestHtml", "xhtml/loren.xhtml", "Lorem ipsum dolor sit amet, consectetur, adipisci velit.").setToc(true);
            book.addTextContent("TestHtml", "xhtml/ipsum.xhtml", "Lorem ipsum dolor sit amet, consectetur, adipisci velit.").setToc(true);

            book.addCoverImage(IOUtils.toByteArray(this.getClass().getResourceAsStream("/cover.png")), "image/png", "xhtml/images/cover.png");

            book.writeToStream(file);

            // Tests
            assertEquals("Der Fall der Welt", book.getTitle());
            assertEquals("Avonia", book.getAuthor());
            assertEquals("en", book.getLanguage());

            assertEquals(7, book.getContents().size());
            assertEquals(6, book.getUniqueHrefs().size());

        } catch (Exception e) {
            log.error("Throw exception due test", e);
        }
    }
}
