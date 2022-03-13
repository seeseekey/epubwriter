package net.seeseekey.epubwriter.api;

import net.seeseekey.epubwriter.model.Content;
import net.seeseekey.epubwriter.model.EpubBook;

/**
 * Service to create the EPUB navigation document
 */
public interface TocCreator {

    /**
     * Creates the EPUB TOC navigation document Content object
     *
     * @param book the EpubBook to create the TOC for
     * @return the TOC Content object
     */
    Content createTocFromBook(EpubBook book);
}
