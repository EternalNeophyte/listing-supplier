//your package here

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ListingSupplier {

    public static void main(String... args) throws IOException {
        var directory = Path.of("src").toAbsolutePath();
        var targetPath = Path.of("src//listing.txt");
        try(var stream = Files.walk(directory);
            var writer = Files.newBufferedWriter(targetPath,
                                                StandardOpenOption.APPEND,
                                                StandardOpenOption.CREATE))
        {
            stream.filter(path -> {
                        String fileName = path.getFileName().toString();
                        return !fileName.equals("ListingSupplier.java") && fileName.endsWith(".java");
                    })
                    .forEach(path -> {
                        try {
                            String content = """
                                             Листинг файла %s
     
                                             %s
                                             
                                             """.formatted(path.getFileName(), Files.readString(path))
                                                .replaceAll("[ ]+(?=[\\.,;])", "")
                                                .replaceAll("[ ]{3,}", "  ");
                            writer.write(content);
                            writer.flush();
                        }
                        catch (IOException ignored) { }
                    });
        }
    }
}
