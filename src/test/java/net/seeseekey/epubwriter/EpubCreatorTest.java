package net.seeseekey.epubwriter;

import net.seeseekey.epubwriter.model.EpubBook;
import net.seeseekey.epubwriter.model.TocLink;
import net.seeseekey.epubwriter.utils.Logging;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class EpubCreatorTest {

    private static final Logger log = Logging.getLogger();

    @Test
    void testEpubCreate() {

        try (FileOutputStream file = new FileOutputStream("test.epub")) {

            // Create book
            EpubBook book = new EpubBook("en", UUID.randomUUID().toString(), "Der Fall der Welt", "Avonia");

            book.addContent(this.getClass().getResourceAsStream("/chapter-1.xhtml"), "application/xhtml+xml", "chapter-1.xhtml", true, true).setId("Lorem");
            book.addContent(this.getClass().getResourceAsStream("/chapter-2.xhtml"), "application/xhtml+xml", "chapter-2.xhtml", true, true).setId("Ipsum");

            book.addContent(this.getClass().getResourceAsStream("/style.css"), "text/css", "css/style.css", false, false);

            book.addTextContent("TestHtml", "loren.xhtml", "Lorem ipsum dolor sit amet, consectetur, adipisci velit.").setToc(true);
            book.addTextContent("TestHtml", "ipsum.xhtml", "Lorem ipsum dolor sit amet, consectetur, adipisci velit.").setToc(true);

            // Cover
            InputStream coverResourceStream = this.getClass().getResourceAsStream("/cover.png");

            if (coverResourceStream != null) {
                book.addCoverImage(IOUtils.toByteArray(coverResourceStream), "image/png", "images/cover.png");
            }

            // Create toc
            List<TocLink> tocLinks = new ArrayList<>();

            // Chapter 1
            TocLink tocChapter1 = new TocLink("chapter-1.xhtml", "Chapter 1", null);

            List<TocLink> tocChapter1Sections = new ArrayList<>();

            tocChapter1Sections.add(new TocLink("chapter-1.xhtml#section-1.1", "Section 1.1", null));
            tocChapter1Sections.add(new TocLink("chapter-1.xhtml#section-1.2", "Section 1.2", null));

            tocChapter1.setTocChildLinks(tocChapter1Sections);
            tocLinks.add(tocChapter1);

            // Chapter 2
            tocLinks.add(new TocLink("chapter-2.xhtml", "Chapter 2", null));

            // Set toc options
            book.setAutoToc(false);
            book.setTocLinks(tocLinks);

            // Write book
            book.writeToStream(file);

            // Tests
            assertEquals("Der Fall der Welt", book.getTitle());
            assertEquals("Avonia", book.getAuthor());
            assertEquals("en", book.getLanguage());

            assertEquals(7, book.getContents().size());
            assertEquals(6, book.getUniqueHrefs().size());

            assertNotNull(coverResourceStream);

        } catch (Exception e) {
            log.error("Throw exception due test", e);
            fail();
        }
    }
}
