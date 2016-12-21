package ru.Makivay.sandbox.tests;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import ru.Makivay.sandbox.Constants;
import ru.Makivay.sandbox.utils.Counter;
import ru.Makivay.sandbox.utils.FileLoader;
import ru.Makivay.sandbox.utils.PdfToolbox;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfTests {
    public final static void mergeTest(){
        try {
            PDDocument result = new PDDocument();
            final PDFMergerUtility mergerUtility = new PDFMergerUtility();
            final PDDocument document = PDDocument.load(new FileLoader().getFile(Constants.path1095C));
            log("Start gathering");
            final Counter counter = new Counter();
            IntStream.range(0, 100).forEach(value -> {
                try {
                    log(counter.inc());
                    mergerUtility.appendDocument(result, document);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            log("Start saving");
            result.save(Constants.to);
            log("Closing document");
            document.close();
            log("Closing result");
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log(Runtime.getRuntime().totalMemory()/1073741824.0D);
        }
    }

    public static void testStampPdf() {
        try (final PDDocument document = PDDocument.load(new FileLoader().getFile(Constants.path1095B))) {
            try (final PDDocument stamp = PDDocument.load(new FileLoader().getFile(Constants.pathTestIndicia))) {
                PdfToolbox.stampPDF(document, stamp, 150, 100);
                PdfToolbox.stampPDF(document, stamp, 250, 100);
                document.save(Constants.to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void streamFileZiping() throws IOException {
        final int iterations = 25;

        final String tempFileName = "temp_" + UUID.randomUUID();
        final String resultFileName = "out.zip";

        final File temFile = File.createTempFile(tempFileName, "");
        try (FileOutputStream outputStream = new FileOutputStream(temFile)) {
            try (ZipOutputStream out = new ZipOutputStream(outputStream)) {
                log("zipOut");
                try (final PDDocument document = PDDocument.load(new FileInputStream("out.pdf"))) {
                    log("document");
                    try (final ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
                        log("tempByteArrayStream");
                        document.save(bytes);
                        for (int i = 0; i < iterations; i++) {
                            log("№ " + i);
                            out.putNextEntry(new ZipEntry("out" + i + ".pdf"));
                            out.write(bytes.toByteArray());
                            logMem();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (InputStream inputStream = new FileInputStream(temFile)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(resultFileName)) {
                org.apache.pdfbox.io.IOUtils.copy(inputStream, fileOutputStream);
                log("file size: " + new File(resultFileName).length());
                Files.deleteIfExists(temFile.toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void log(Object message) {
        System.out.println(new Date() + " " + String.valueOf(message));
    }

    private static void logMem() {
        log("mem: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + "mb");
    }
}
