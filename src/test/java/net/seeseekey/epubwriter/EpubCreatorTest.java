package net.seeseekey.epubwriter;

import net.seeseekey.epubwriter.model.EpubBook;
import net.seeseekey.epubwriter.model.Landmark;
import net.seeseekey.epubwriter.model.TocLink;
import net.seeseekey.epubwriter.utils.DataUtils;
import net.seeseekey.epubwriter.utils.Logging;
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

            // Create book and set information
            EpubBook book = new EpubBook("de", UUID.randomUUID().toString(), "Der Fall der Welt", "Avonia");

            book.setPublisher("ACME");
            book.setRights("© 2023 by Avonia");

            // Set ISBN
            book.setIsbn("9781566199094"); // Example ISBN (13 digits)

            // Add content
            book.addContent(this.getClass().getResourceAsStream("/chapter-1.xhtml"),
                            "application/xhtml+xml", "chapter-1.xhtml",
                            true,
                            true)
                    .setId("Lorem");

            book.addContent(this.getClass().getResourceAsStream("/chapter-2.xhtml"),
                            "application/xhtml+xml",
                            "chapter-2.xhtml",
                            true,
                            true)
                    .setId("Ipsum");

            // CSS
            book.addContent(this.getClass().getResourceAsStream("/style.css"),
                    "text/css", "css/style.css",
                    false,
                    false);

            // Cover XHTML
            book.addContent(this.getClass().getResourceAsStream("/cover.xhtml"),
                            "application/xhtml+xml",
                            "cover.xhtml",
                            true,
                            true)
                    .setId("Cover");

            // Cover Image
            InputStream coverResourceStream = this.getClass().getResourceAsStream("/cover.png");

            if (coverResourceStream != null) {
                book.addCoverImage(DataUtils.toByteArray(coverResourceStream), "image/png", "images/cover.png");
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

            // Create Landmarks
            List<Landmark> landmarks = new ArrayList<>();

            // Start of real content (bodymatter)
            Landmark landmark = new Landmark();

            landmark.setType("bodymatter");
            landmark.setHref("chapter-1.xhtml");
            landmark.setTitle("Start of Content");

            landmarks.add(landmark);

            // Start of cover
            landmark = new Landmark();

            landmark.setType("cover");
            landmark.setHref("cover.xhtml");
            landmark.setTitle("Cover");

            landmarks.add(landmark);

            // TOC
            landmark = new Landmark();

            landmark.setType("toc");
            landmark.setHref("toc.xhtml#toc");
            landmark.setTitle("Table of Contents");

            landmarks.add(landmark);

            // Set landmarks
            book.setLandmarks(landmarks);

            // Write book
            book.writeToStream(file);

            // Tests
            assertEquals("Der Fall der Welt", book.getTitle());
            assertEquals("Avonia", book.getAuthor());
            assertEquals("de", book.getLanguage());

            assertEquals(6, book.getContents().size());
            assertEquals(5, book.getUniqueHrefs().size());

            assertNotNull(coverResourceStream);

        } catch (Exception e) {
            log.error("Throw exception due test", e);
            fail();
        }
    }
}
