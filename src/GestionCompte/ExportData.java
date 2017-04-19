package GestionCompte;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nejoua & Myriam on 30/03/2017.
 */
public class ExportData {

    private static final char DEFAULT_SEPARATOR = ',';

    private DBConnection dbc;
    private String basePath;

    public ExportData(DBConnection dbc){
        this.dbc = dbc;
        this.basePath = System.getProperty("user.dir") + "/Export";
        File theDir = new File(System.getProperty("user.dir") + "/Export");

        System.out.println(theDir.getPath());
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                System.out.println(se.getMessage());
            }
            if(result) {
                System.out.println("DIR created");
            }
        }
    }

    public void exportHasCsv(String name, ArrayList<List<String>> export){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();
            String today = formatter.format(now);

            String fullPathFileName = this.basePath + "/" + name + "_" + today +".csv";
            PrintWriter writer = new PrintWriter(fullPathFileName, "UTF-8");

            System.out.println(export);

            for (List<String> anExport : export) {
                System.out.println(anExport);
                this.writeLine(writer, anExport);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    private String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }
}
