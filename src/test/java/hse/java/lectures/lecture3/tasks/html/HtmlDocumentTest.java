package hse.java.lectures.lecture3.tasks.html;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
class HtmlDocumentTest {

    @Test
    void validDocumentPasses() throws IOException {
        String html = "<html><head></head><body><div><p>Hello</p></div></body></html>";
        Path path = writeTemp(html);
        assertDoesNotThrow(() -> new HtmlDocument(path));
    }

    @Test
    void validDocumentWithAttributesAndCasePasses() throws IOException {
        String html = "<HTML><Head></Head><Body><div class=\"x\"><p>Ok</p></div></Body></HTML>";
        Path path = writeTemp(html);
        assertDoesNotThrow(() -> new HtmlDocument(path));
    }

    @Test
    void unsupportedTagFails() throws IOException {
        String html = "<html><body><span></span></body></html>";
        Path path = writeTemp(html);
        assertThrows(UnsupportedTagException.class, () -> new HtmlDocument(path));
    }

    @Test
    void unexpectedClosingTagFails() throws IOException {
        String html = "</div><html></html>";
        Path path = writeTemp(html);
        assertThrows(UnexpectedClosingTagException.class, () -> new HtmlDocument(path));
    }

    @Test
    void mismatchedClosingTagFails() throws IOException {
        String html = "<html><body><div><p></div></p></body></html>";
        Path path = writeTemp(html);
        assertThrows(MismatchedClosingTagException.class, () -> new HtmlDocument(path));
    }

    @Test
    void unclosedTagFails() throws IOException {
        String html = "<html><body><div>";
        Path path = writeTemp(html);
        assertThrows(UnclosedTagException.class, () -> new HtmlDocument(path));
    }

    @Test
    void invalidStructureBodyBeforeHeadFails() throws IOException {
        String html = "<html><body></body><head></head></html>";
        Path path = writeTemp(html);
        assertThrows(InvalidStructureException.class, () -> new HtmlDocument(path));
    }

    @Test
    void invalidStructureMultipleHtmlFails() throws IOException {
        String html = "<html></html><html></html>";
        Path path = writeTemp(html);
        assertThrows(InvalidStructureException.class, () -> new HtmlDocument(path));
    }

    @Test
    void invalidStructureHeadNotDirectChildFails() throws IOException {
        String html = "<html><div><head></head></div></html>";
        Path path = writeTemp(html);
        assertThrows(InvalidStructureException.class, () -> new HtmlDocument(path));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("fileCases")
    void fileCases(String resourcePath, Class<? extends RuntimeException> expected) throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource(resourcePath).toURI());
        if (expected == null) {
            assertDoesNotThrow(() -> new HtmlDocument(path));
        } else {
            assertThrows(expected, () -> new HtmlDocument(path));
        }
    }

    static Stream<Arguments> fileCases() {
        return Stream.of(
                Arguments.of("html/valid_basic.html", null),
                Arguments.of("html/valid_attrs_case.html", null),
                Arguments.of("html/invalid_unsupported_tag.html", UnsupportedTagException.class),
                Arguments.of("html/invalid_unexpected_closing.html", UnexpectedClosingTagException.class),
                Arguments.of("html/invalid_mismatched.html", MismatchedClosingTagException.class),
                Arguments.of("html/invalid_unclosed.html", UnclosedTagException.class),
                Arguments.of("html/invalid_structure_body_before_head.html", InvalidStructureException.class),
                Arguments.of("html/invalid_structure_multiple_html.html", InvalidStructureException.class),
                Arguments.of("html/invalid_structure_head_not_direct_child.html", InvalidStructureException.class)
        );
    }

    private Path writeTemp(String content) throws IOException {
        Path path = Files.createTempFile("html-doc", ".html");
        Files.writeString(path, content);
        path.toFile().deleteOnExit();
        return path;
    }
}
