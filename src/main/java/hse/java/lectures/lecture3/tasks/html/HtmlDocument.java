package hse.java.lectures.lecture3.tasks.html;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HtmlDocument {

    private static final Set<String> SUPPORTED_TAGS = Set.of("html", "head", "body", "div", "p");

    public HtmlDocument(String filePath) {
        this(Path.of(filePath));
    }

    public HtmlDocument(Path filePath) {
        String content = readFile(filePath);
        validate(content);
    }

    private String readFile(Path filePath) {
        try {
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

    private void validate(String content) {
        ArrayList<String> tagStack = new ArrayList<>();
        int position = 0;
        boolean hasHtmlTag = false;
        boolean hasHeadTag = false;
        boolean hasBodyTag = false;
        boolean headFinished = false;

        while (position < content.length()) {
            if (content.charAt(position) != '<') {
                position++;
                continue;
            }

            int tagStart = position;
            int tagEnd = content.indexOf('>', tagStart);

            if (tagEnd == -1) {
                throw new InvalidStructureException("Unclosed tag bracket at position " + tagStart);
            }

            String tagContent = content.substring(tagStart + 1, tagEnd).trim();
            position = tagEnd + 1;

            boolean isClosingTag = false;
            if (tagContent.startsWith("/")) {
                isClosingTag = true;
                tagContent = tagContent.substring(1).trim();
            }

            String tagName = extractTagName(tagContent);

            if (isClosingTag) {
                handleClosingTag(tagName, tagStack);

                if (tagName.equals("head")) {
                    headFinished = true;
                }
            } else {
                handleOpeningTag(tagName, tagStack);
                switch (tagName) {
                    case "html" -> {
                        if (hasHtmlTag || tagStack.size()!= 1) {
                            throw new InvalidStructureException("html");
                        }
                        hasHtmlTag = true;
                    }
                    case "head" -> {
                        if (hasHeadTag || hasBodyTag || !hasHtmlTag) {
                            throw new InvalidStructureException("head");
                        }
                        hasHeadTag = true;
                    }
                    case "body" -> {
                        if (hasBodyTag || !hasHtmlTag || hasHeadTag && !headFinished) {
                            throw new InvalidStructureException("body");
                        }

                        hasBodyTag = true;
                    }
                    default -> {
                        if (!(hasBodyTag || hasHeadTag)){
                            throw new InvalidStructureException("дефолт");
                        }
                    }
                }

            }
        }

        if (!tagStack.isEmpty()) {
            throw new UnclosedTagException("Незакрытый тег");
        }

        if (!hasHtmlTag) {
            throw new InvalidStructureException("Нет html");
        }
    }

    private String extractTagName(String tagContent) {
        int spaceIndex = tagContent.indexOf(' ');
        if (spaceIndex != -1) {
            return tagContent.substring(0, spaceIndex).toLowerCase();
        }
        return tagContent.toLowerCase();
    }

    private void checkSupport(String tagName) {
        if (!SUPPORTED_TAGS.contains(tagName)) {
            throw new UnsupportedTagException("Тег фигня");
        }
    }

    private void handleOpeningTag(String tagName, ArrayList<String> tagStack) {
        checkSupport(tagName);
        tagStack.add(tagName);
    }

    private void handleClosingTag(String tagName, ArrayList<String> tagStack) {
        checkSupport(tagName);

        if (tagStack.isEmpty()) {
            throw new UnexpectedClosingTagException("Хочу закрыть а нет открытых");
        }

        String lastOpenedTag = tagStack.get(tagStack.size() - 1);

        if (!lastOpenedTag.equals(tagName)) {
            throw new MismatchedClosingTagException("Хочу закрыть а там не тот");
        }

        tagStack.remove(tagStack.size() - 1);
    }

}
