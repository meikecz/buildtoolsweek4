package gradle;

import java.io.File; // Import hinzugefügt für java.io.File
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.elements.PseudoText;

public class App {
    public String getGreeting() {
        return "Hello Meike!";
    }

    public static void main(String[] args) throws IOException {

        //Ascii render ausprobieren mit PseudoText Code
        IRender render = new Render();
        IContextBuilder builder = render.newBuilder();
        builder.width(120).height(18);
        builder.element(new PseudoText("Meike"));
        ICanvas canvas = render.render(builder.build());
        String s = canvas.getText();
        System.out.println(s);

        //PDFBox ausprobieren
        PDDocument helloPdf = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		helloPdf.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(helloPdf, page);
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 36);
        contentStream.newLineAtOffset(5, 400);
        contentStream.showText("Meike");
        contentStream.endText();
        contentStream.close();
            helloPdf.save(new File("C:\\DevOpsTest\\meinDokument.pdf")); 
            helloPdf.close(); 


        //poi-ooxml-lite ausprobieren 
        // Erstelle eine neue Arbeitsmappe
        Workbook wbHSSF = new HSSFWorkbook();
        try  (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
        wbHSSF.write(fileOut);
        wbHSSF.close();
            }
        Workbook wbXSSF = new XSSFWorkbook();
        try (OutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
         wbXSSF.write(fileOut);
         wbXSSF.close();
        }
  
        System.out.println(new App().getGreeting());

        //dependency ooxml testen
        // Spezifizieren des Pfades und Dateinamen, unter dem die Datei gespeichert werden soll
        File file = new File("C:\\DevOpsTest\\meinDokument.txt");
        
        // Text in die Textdatei schreiben
        String data = "Dies ist ein Testtext!";

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes());
            System.out.println("Die Datei wurde erfolgreich unter " + file.getPath() + " gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
