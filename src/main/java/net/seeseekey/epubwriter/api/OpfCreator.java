package net.seeseekey.epubwriter.api;

import net.seeseekey.epubwriter.model.EpubBook;

/**
 * Service to create the Package Document that carries bibliographic and
 * structural metadata about an EPUB Publication
 */
public interface OpfCreator {

    /**
     * Creates the OPF file text from the EpubBook data
     *
     * @param book the ePub book to generate the OPF for
     * @return the generated OPF markup
     */
    String createOpfString(EpubBook book);
}
