package hse.java.commander;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public final class FileOperations {

    private FileOperations() {
    }

    public static Path detectProjectRoot() {
        Path dir = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();
        if (dir.getFileName() != null && "commander".equals(dir.getFileName().toString()) && dir.getParent() != null)
            return dir.getParent().toAbsolutePath().normalize();
        return dir;
    }

    public static List<PathEntry> list(Path dir, Path root_dir) throws IOException {
        List<PathEntry> entries = new ArrayList<>();
        if (dir == null || !Files.isDirectory(dir)) return entries;

        Path parent = dir.getParent();
        if (parent != null && root_dir != null && parent.startsWith(root_dir) && !dir.equals(root_dir)) entries.add(PathEntry.parent(parent));

        try (var ds = Files.newDirectoryStream(dir)) {
            for (Path p : ds) entries.add(PathEntry.of(p));
        }

        sort(entries);
        return entries;
    }

    public static void move(Path source, Path destination) throws IOException {
        Files.move(source, destination, REPLACE_EXISTING);
    }

    public static void rename(Path source, String new_name) throws IOException {
        Files.move(source, source.getParent().resolve(new_name).normalize(), REPLACE_EXISTING);
    }

    public static void deleteRecursively(Path path) throws IOException {
        if (Files.notExists(path)) return;

        if (Files.isDirectory(path)) {
            try (var ds = Files.newDirectoryStream(path)) {
                for (Path child : ds) deleteRecursively(child);
            }
        }

        Files.deleteIfExists(path);
    }

    private static void sort(List<PathEntry> entries) {
        for (int i = 1; i < entries.size(); i++) {
            PathEntry key = entries.get(i);
            int j = i - 1;

            while (j >= 0 && compare(entries.get(j), key) > 0) {
                entries.set(j + 1, entries.get(j));
                j -= 1;
            }

            entries.set(j + 1, key);
        }
    }

    private static int compare(PathEntry a, PathEntry b) {
        if (a.isDirectory() != b.isDirectory()) return a.isDirectory() ? -1 : 1;
        return String.CASE_INSENSITIVE_ORDER.compare(a.toString(), b.toString());
    }
}