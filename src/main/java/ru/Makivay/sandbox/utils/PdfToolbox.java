package ru.Makivay.sandbox.utils;

import com.google.common.collect.Lists;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.util.Matrix;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class PdfToolbox {

    public static List<String> getFieldNamesList(final PDDocument document) {
        return document.getDocumentCatalog().getAcroForm().getFields()
                .stream()
                .map(PdfToolbox::getFieldNamesList)
                .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    public static List<String> getFieldNamesList(final PDField field) {
        if (field instanceof PDNonTerminalField) {
            return ((PDNonTerminalField) field).getChildren()
                    .stream()
                    .map(PdfToolbox::getFieldNamesList)
                    .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
        } else {
            return Lists.newArrayList(field.getFullyQualifiedName());
        }
    }

    public static void watermarkPDF(final @Nonnull PDDocument document) throws IOException {
        try (final PDDocument overlayPdf = PDDocument.load(new FileLoader().getFile("pdf/DoNotFileBackground-1.pdf"))) {
            Overlay overlay = new Overlay();
            overlay.setInputPDF(document);
            overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
            overlay.setAllPagesOverlayPDF(overlayPdf);
            overlay.overlay(new HashMap<>());
        }
    }

    public static void stampPDF(final @Nonnull PDDocument document, final @Nonnull PDDocument stamp, final float x, final float y) throws IOException {
        for (PDPage page : document.getPages()) {
            final PDFormXObject xobject = importAsXObject(stamp, stamp.getPages().get(0));
            try (final PDPageContentStream content = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, false, false)) {
                content.saveGraphicsState();
                content.transform(Matrix.getTranslateInstance(x, y));
                content.drawForm(xobject);
                content.restoreGraphicsState();
            }
        }
    }

    private static PDFormXObject importAsXObject(final @Nonnull PDDocument target, final @Nonnull PDPage page) throws IOException {
        try (final InputStream is = page.getContents()) {
            final PDFormXObject xobject = new PDFormXObject(target);
            try (OutputStream os = xobject.getStream().createOutputStream()) {
                IOUtils.copy(is, os);
                xobject.setResources(page.getResources());
                xobject.setBBox(page.getBBox());
                return xobject;
            }
        }
    }

    public static void flatten(PDDocument document) throws IOException {
        final PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        final List<PDField> fields = acroForm.getFields();
        if(acroForm.xfaIsDynamic()) {
            System.out.println("Flatten for a dynamix XFA form is not supported");
        } else {
//            if(refreshAppearances) {
//                this.refreshAppearances(fields);
//            }

            Map<COSDictionary, Integer> annotationToPageRef = null;
            for( PDField page : fields){

                for (PDAnnotationWidget annotationWidget : page.getWidgets()) {
                    if (annotationWidget.getNormalAppearanceStream() != null) {
                        PDPage annotation = annotationWidget.getPage();
                        if (annotationWidget.getPage() == null) {
                            if (annotationToPageRef == null) {
                                annotationToPageRef = buildAnnotationToPageRef(document);
                            }

                            Integer appearanceStream = annotationToPageRef.get(annotationWidget.getCOSObject());
                            if (appearanceStream != null) {
                                annotation = document.getPage(appearanceStream);
                            }
                        }

                        try(PDPageContentStream contentStream = new PDPageContentStream(document, annotation, PDPageContentStream.AppendMode.APPEND, true)) {
                            final PDAppearanceStream appearanceStream1 = annotationWidget.getNormalAppearanceStream();
                            final PDFormXObject fieldObject = new PDFormXObject(appearanceStream1.getCOSObject());
                            contentStream.saveGraphicsState();

                            final boolean needsTransformation = isNeedsTransformation(appearanceStream1);
                            if (needsTransformation) {
                                final Matrix translationMatrix = Matrix.getTranslateInstance(annotationWidget.getRectangle().getLowerLeftX(), annotationWidget.getRectangle().getLowerLeftY());
                                contentStream.transform(translationMatrix);
                            }

                            contentStream.drawForm(fieldObject);
                            contentStream.restoreGraphicsState();
                        }
                    }
                }
            }

            for(PDPage page : document.getPages()){
                final ArrayList<PDAnnotation> annotations1 = new ArrayList<>();

                for (PDAnnotation annotation1 : page.getAnnotations()) {
                    if (!(annotation1 instanceof PDAnnotationWidget)) {
                        annotations1.add(annotation1);
                    }
                }

                page.setAnnotations(annotations1);
            }

            acroForm.setFields(Collections.emptyList());
            acroForm.getCOSObject().removeItem(COSName.XFA);
        }
    }

    private static Map<COSDictionary, Integer> buildAnnotationToPageRef(PDDocument document) {
        final HashMap<COSDictionary, Integer> annotationToPageRef = new HashMap<>();
        int idx = 0;

        for(Iterator<PDPage> i$ = document.getPages().iterator(); i$.hasNext(); ++idx) {
            final PDPage page = i$.next();

            try {

                for (PDAnnotation annotation : page.getAnnotations()) {
                    if (annotation instanceof PDAnnotationWidget) {
                        annotationToPageRef.put(annotation.getCOSObject(), idx);
                    }
                }
            } catch (IOException var7) {
                System.out.println("Can\'t retrieve annotations for page " + idx);
            }
        }

        return annotationToPageRef;
    }

    private static boolean isNeedsTransformation(PDAppearanceStream appearanceStream) {
        return appearanceStream.getResources() == null || !appearanceStream.getResources().getXObjectNames().iterator().hasNext();
    }
}
