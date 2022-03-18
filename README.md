# epubwriter

Library to write EPUB files in version 3.

## Features

1. Build valid EPUB3
2. Add files or text as content
3. Set TOC inclusion
4. Set spine inclusion
5. Add text as content / pages

## Usage

The library can used via Maven. Add the dependency:

```
<dependency>
    <groupId>net.seeseekey</groupId>
    <artifactId>epubwriter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Code example

```java
try (FileOutputStream file = new FileOutputStream("test.epub")) {

  EpubBook book = new EpubBook("en", UUID.randomUUID().toString(), "Samuel Test Book", "Samuel Holtzkampf");

  book.addContent(this.getClass().getResourceAsStream("/epub30-overview.xhtml"), "application/xhtml+xml", "xhtml/epub30-overview.xhtml", true, true).setId("Overview");
  book.addContent(this.getClass().getResourceAsStream("/idpflogo_web_125.jpg"), "image/jpeg", "img/idpflogo_web_125.jpg", false, false);
  book.addContent(this.getClass().getResourceAsStream("/epub-spec.css"), "text/css", "css/epub-spec.css", false, false);

  book.addTextContent("TestHtml", "xhtml/samuelTest2.xhtml", "Samuel test one two four!!!!!\nTesting two").setToc(true);
  book.addTextContent("TestHtml", "xhtml/samuelTest.xhtml", "Samuel test one two three\nTesting two").setToc(true);

  book.addCoverImage(IOUtils.toByteArray(this.getClass().getResourceAsStream("/P1010832.jpg")), "image/jpeg", "images/P1010832.jpg");

  book.writeToStream(file);
}
```

## Authors

* seeseekey - [seeseekey.net](https://seeseekey.net)
* OpenCollab

## License

epubwriter is licensed under MIT.