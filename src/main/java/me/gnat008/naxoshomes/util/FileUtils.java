package me.gnat008.naxoshomes.util;

import me.gnat008.naxoshomes.ConsoleLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility methods for handling files.
 */
public final class FileUtils
{

    private FileUtils()
    {}

    /**
     * Writes the given data to the provided file.
     *
     * @param file The file to write to.
     * @param data The data to write.
     */
    public static void writeData(File file, String data) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException ex) {
            ConsoleLogger.severe("Could not write data to file '" + file + "':", ex);
        }
    }

    /**
     * Creates the given file if it doesn't exist.
     *
     * @param file The file to create if necessary.
     * @return The given file (allows inline use).
     * @throws IOException if file could not be created
     */
    public static File createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                boolean success = file.getParentFile().mkdirs();
                if (!success) {
                    ConsoleLogger.warning("Could not create parent directories while trying to create '" + file + "'");
                }
            }

            boolean success = file.createNewFile();
            if (!success) {
                ConsoleLogger.warning("Could not create file '" + file + "'");
            }
        }
        return file;
    }
}
