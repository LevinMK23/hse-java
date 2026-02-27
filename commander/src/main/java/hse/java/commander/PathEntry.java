package hse.java.commander;

import java.nio.file.Files;
import java.nio.file.Path;

public final class PathEntry {

    private final Path path;
    private final String display_name;
    private final boolean parent;

    private PathEntry(Path path, String display_name, boolean parent) {
        this.path = path;
        this.display_name = display_name;
        this.parent = parent;
    }

    public static PathEntry parent(Path parent_dir) {
        return new PathEntry(parent_dir, "..", true);
    }

    public static PathEntry of(Path path) {
        String name = path.getFileName() == null ? path.toString() : path.getFileName().toString();
        if (Files.isDirectory(path)) name += "/";
        return new PathEntry(path, name, false);
    }

    public Path path() {return path;}

    public boolean isParent() {return parent;}

    public boolean isDirectory() {return Files.isDirectory(path);}

    @Override
    public String toString() {return display_name;}
}