package ru.Makivay.sandbox.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class FileLoader {
    public File getFile(final String src) throws FileNotFoundException {
        final URL resource = getClass().getClassLoader().getResource(src);
        if (resource != null) {
            return new File(resource.getFile());
        } else {
            throw new FileNotFoundException("File \"" + src + "\" not found");
        }
    }
}
