package hse.java.lectures.lecture3.tasks.html;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

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

    private void validate(String content){
        if (content == null) throw new InvalidStructureException("content is null");

        Deque<String> open_tags = new ArrayDeque<>();
        boolean html_seen = false,
                head_seen = false,
                head_closed = false,
                body_seen = false;

        for (int index = 0; (index = content.indexOf('<', index)) >= 0; ) {
            int tag_end = content.indexOf('>', index + 1);
            if (tag_end < 0) throw new InvalidStructureException("expected '>' after position " + index);

            String inside = content.substring(index + 1, tag_end).trim();
            if (inside.isEmpty()) throw new InvalidStructureException("tag is empty at position " + index);

            boolean closing = inside.charAt(0) == '/';
            if (closing) inside = inside.substring(1).trim();

            int name_end = 0;
            while (name_end < inside.length()) {
                char ch = inside.charAt(name_end);
                if (ch <= ' ' || ch == '/') break;
                name_end += 1;
            }

            String tag_name = inside.substring(0, name_end).toLowerCase();
            if (tag_name.isEmpty()) throw new InvalidStructureException("tag name is missing at position " + index);

            if (!(tag_name.equals("html") || tag_name.equals("head") || tag_name.equals("body") || tag_name.equals("div") || tag_name.equals("p"))) {
                throw new UnsupportedTagException("tag <" + tag_name + "> is not supported");
            }

            if (closing) {
                if (open_tags.isEmpty()) throw new UnexpectedClosingTagException("unexpected closing tag </" + tag_name + ">");

                String opened = open_tags.pop();
                if (!opened.equals(tag_name)) throw new MismatchedClosingTagException("closing tag </" + tag_name + "> does not match <" + opened + ">");
                if (tag_name.equals("head")) head_closed = true;

            } else {
                if (tag_name.equals("html")) {
                    if (html_seen || !open_tags.isEmpty()) throw new InvalidStructureException("more than one <html> tag found");
                    html_seen = true;

                } else {
                    if (!html_seen || open_tags.isEmpty()) throw new InvalidStructureException("root element must be <html>");
                    if (tag_name.equals("head")) {
                        if (head_seen || body_seen || open_tags.size() != 1 || !open_tags.peek().equals("html")) {
                            throw new InvalidStructureException("<head> must be a direct child of <html> and come before <body>");
                        }
                        head_seen = true;

                    } else if (tag_name.equals("body")) {
                        if (body_seen || open_tags.size() != 1 || !open_tags.peek().equals("html") || (head_seen && !head_closed)) {
                            throw new InvalidStructureException("<body> must be a direct child of <html> and come after </head>");
                        }
                        body_seen = true;
                    }
                }
                open_tags.push(tag_name);
            }

            index = tag_end + 1;
        }

        if (!html_seen) throw new InvalidStructureException("<html> tag is missing");
        if (!open_tags.isEmpty()) throw new UnclosedTagException("unclosed tags remaining: " + open_tags);
    }
}
