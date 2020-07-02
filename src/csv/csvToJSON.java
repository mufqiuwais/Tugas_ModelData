package csv;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;

//import com.opencsv.CSVWriter;
public class csvToJSON {
	public static void main(String[] args) throws Exception {
        File input = new File("3000articles.csv");
        File output = new File("3000articles.json");

        List<Map<?, ?>> data = readObjectsFromCsv(input);
        writeAsJson(data, output);
        System.out.println("Done converting...\n");
    }

    public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);

        return mappingIterator.readAll();
    }

    public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, data);
    }
}