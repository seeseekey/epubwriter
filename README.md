# epubwriter

Library to write EPUB files in version 3.

## Features

1. Build valid EPUB3
2. Add files or text as content
3. Set TOC inclusion
4. Set spine inclusion
5. Add text as content / pages

## Usage

The library can be used via Maven. Add the dependency:

```
<dependency>
    <groupId>net.seeseekey</groupId>
    <artifactId>epubwriter</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Code example

```java
try (FileOutputStream file = new FileOutputStream("test.epub")) {
    
  EpubBook book = new EpubBook("en", UUID.randomUUID().toString(), "Der Fall der Welt", "Avonia");

  book.addContent(this.getClass().getResourceAsStream("/chapter-1.xhtml"), "application/xhtml+xml", "xhtml/chapter-1.xhtml", true, true).setId("Lorem");
  book.addContent(this.getClass().getResourceAsStream("/chapter-2.xhtml"), "application/xhtml+xml", "xhtml/chapter-2.xhtml", true, true).setId("Ipsum");

  book.addContent(this.getClass().getResourceAsStream("/style.css"), "text/css", "xhtml/css/style.css", false, false);

  book.addTextContent("TestHtml", "xhtml/loren.xhtml", "Lorem ipsum dolor sit amet, consectetur, adipisci velit.").setToc(true);
  book.addTextContent("TestHtml", "xhtml/ipsum.xhtml", "Lorem ipsum dolor sit amet, consectetur, adipisci velit.").setToc(true);

  book.addCoverImage(IOUtils.toByteArray(this.getClass().getResourceAsStream("/cover.png")), "image/png", "xhtml/images/cover.png");

  book.writeToStream(file);
}
```

## Authors

* seeseekey - [seeseekey.net](https://seeseekey.net)
* OpenCollab

## License

epubwriter is licensed under MIT.