package hse.java.lectures.lecture3.tasks.html;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class HtmlDocument {

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

    private final List<String> supportedTags = List.of("html", "head", "body", "div", "p");

    Deque<String> stack;

    private boolean tagsEnd, metHead, metBody, metHtml;
    private boolean  hasHead, hasBody ;


    private record Tag(String name, boolean closing) {}

    private Tag parse(String content, int pos, int end) {
        String sub_str = content.substring(pos + 1, end);
        boolean isClosing = false;

        if (sub_str.startsWith("/")) {
            sub_str = sub_str.substring(1);
            isClosing = true;
        }
        sub_str = sub_str.trim();

        sub_str =  sub_str.split("\\s+")[0].toLowerCase();

        return new Tag(sub_str, isClosing);
    }

    private void validateStack(Tag tag) {
        boolean isClosing = tag.closing;
        String sub_str = tag.name;
        if (isClosing) {
            if (stack.isEmpty()) {
                throw new UnexpectedClosingTagException("closing tag without the opening one\n");
            } else if (!stack.pop().equals(sub_str)) {
                throw new MismatchedClosingTagException("mismatched closing tag\n");
            } else {
                if (sub_str.equals("head")) {hasHead = false;}
                if (sub_str.equals("body")) {hasBody = false;}
            }
        } else {
            if ((sub_str.equals("div") || sub_str.equals("p")) && (hasHead || !hasBody)) {
                throw new InvalidStructureException("invalid structure\n");
            }
            stack.push(sub_str);
        }
    }

    private void validateHead(Tag tag) {
        boolean isClosing = tag.closing;
        String sub_str = tag.name;
        if (sub_str.equals("head")) {
            if (isClosing && !metHead) {
                metHead = true;
            } else if ((metHead || metBody) && !isClosing) {
                throw new InvalidStructureException("invalid structure\n");
            } else {
                hasHead = true;
            }
        }
    }

    private void validateBody(Tag tag) {
        boolean isClosing = tag.closing;
        String sub_str = tag.name;
        if (sub_str.equals("body")) {
            if (isClosing && !metBody) {
                metBody = true;
            } else if (metBody) {
                throw new InvalidStructureException("invalid structure\n");
            } else {
                hasBody = true;
            }
        }
    }

    private void validateStructure(Tag tag) {
        boolean isClosing = tag.closing;
        String sub_str = tag.name;

        if (sub_str.equals("html")) {
            metHtml = true;
            if (isClosing) {
                tagsEnd = true;
            }
        }

        if (sub_str.equals("html") && !isClosing && !stack.isEmpty()) {
            throw new InvalidStructureException("invalid structure\n");
        }

        if ((sub_str.equals("body") && hasHead) || (sub_str.equals("head") && hasBody)) {
            throw new InvalidStructureException("invalid structure\n");
        }
    }

    private void validate(String content) {
        stack = new ArrayDeque<>();
        tagsEnd = metHead = metBody = metHtml = hasHead = hasBody = false;

        int pos = 0;
        int end = 0;

        while ((pos = content.indexOf('<', pos)) != -1) {

            end = content.indexOf('>', pos + 1);

            if (end == -1) {
                throw new UnsupportedTagException("not a tag\n");
            }

            if (tagsEnd) {
                throw new InvalidStructureException("invalid structure\n");
            }

            Tag parsed_tag = parse(content, pos, end);

            if (!supportedTags.contains(parsed_tag.name)) {
                throw new UnsupportedTagException("unsupported tag\n");
            }

            validateHead(parsed_tag);
            validateBody(parsed_tag);
            validateStructure(parsed_tag);
            validateStack(parsed_tag);

            pos = end + 1;

        }

        if (!stack.isEmpty()) {
            throw new UnclosedTagException("unclosed tags left\n");
        }

        if (!metHtml) {
            throw new InvalidStructureException("invalid structure\n");
        }
    }
}