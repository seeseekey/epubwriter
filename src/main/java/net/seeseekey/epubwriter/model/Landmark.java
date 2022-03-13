package net.seeseekey.epubwriter.model;

/**
 * Represents a landmark object in the Navigation document landmarks section
 */
public class Landmark {

    /**
     * The href of the referenced landmark
     */
    private String href;

    /**
     * The title to display
     */
    private String title;

    /**
     * The type (cover,title-page,frontmatter,bodymatter,backmatter,toc, loi,lot
     * (list of tables),preface,bibliography,index,glossary,acknowledgments
     */
    private String type;

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
