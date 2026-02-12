package hse.java.lectures.lecture3.tasks.html;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.Math.min;

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
        String lcontent = content.toLowerCase();
        boolean body = false;
        boolean head = false;
        Stack<String> st = new Stack<>();
        int l = -1, r = -1;
        for (int i = 0; i < lcontent.length(); i++) {
            if (lcontent.charAt(i) == '<') {
                l = i;
            } else if (lcontent.charAt(i) == '>') {
                r = i;
            }

            if (l != -1 && r != -1) {
                String parsed = parse(lcontent.substring(++l, min(r, l + 5)));
                l = -1; r = -1;
                if (parsed.charAt(0) != '/') {
                    if (st.isEmpty()) {
                        if (parsed.equals("html")) {
                            st.add(parsed);
                        }
                        else {
                            throw new InvalidStructureException("нарушены правила html/head/body.");
                        }
                    }
                    else {
                        if (parsed.equals("head")) {
                            if (body || head) {
                                throw new InvalidStructureException("нарушены правила html/head/body.");
                            }
                            head = true;
                            st.add(parsed);
                        }
                        else if (parsed.equals("body")) {
                            if (body) {
                                throw new InvalidStructureException("нарушены правила html/head/body.");
                            }
                            body = true;
                            st.add(parsed);
                        }
                    }
                }
                else {
                    if (st.isEmpty()) {
                        throw new UnexpectedClosingTagException("закрывающий тег без соответствующего открывающего.");
                    }
                    String last = st.pop();
                    if (!parsed.substring(1).equals(last)) {
                        throw new MismatchedClosingTagException("закрывается не тот тег.");
                    }
                }
            }
        }
    }

    private String parse(String substr) {
        String c = "";
        if (substr.charAt(0) == '/') {
            c = "/";
            substr = substr.substring(1);
        }
        if (substr.length() < 4) {
            if (substr.equals("p")) {
                return c + "p";
            }
            else if (substr.equals("div")) {
                return c + "div";
            }
        }
        else {
            if (substr.equals("html")) {
                return c + "html";
            }
            if (substr.equals("head")) {
                return c + "head";
            }
            if (substr.equals("body")) {
                return c + "body";
            }
        }
        throw new UnsupportedTagException("встретился тег вне списка разрешённых.");
    }
}
