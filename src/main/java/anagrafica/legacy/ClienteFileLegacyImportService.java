package anagrafica.legacy;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

@Service
public class ClienteFileLegacyImportService {

	 // -----------------------------------------------------------------------
    // Configurazione
    // -----------------------------------------------------------------------

    /** Separatore CSV — cambia in "," se il tuo file usa la virgola. */
    private String  csvDelimiter = ";";

    /**
     * Encoding del CSV.
     * Usa Charset.forName("windows-1252") se il file viene da Excel su Windows.
     */
    private Charset csvCharset   = StandardCharsets.UTF_8;

    /** Dimensione default dei batch. */
    private int     batchSize    = 1000;

    public void setCsvDelimiter(String csvDelimiter) { this.csvDelimiter = csvDelimiter; }
    public void setCsvCharset(Charset csvCharset)    { this.csvCharset   = csvCharset; }
    public void setBatchSize(int batchSize)          { this.batchSize    = batchSize; }

    // -----------------------------------------------------------------------
    // Indici colonne
    // -----------------------------------------------------------------------

    private static final int COL_REC_ID          = 0;
    private static final int COL_CODICE           = 1;
    private static final int COL_TIPO_CLIENTE     = 2;
    private static final int COL_RAGIONE_SOCIALE  = 3;
    private static final int COL_INDIRIZZO        = 4;
    private static final int COL_CITTA            = 5;
    private static final int COL_PROV             = 6;
    private static final int COL_CAP              = 7;
    private static final int COL_PIVA             = 8;
    private static final int COL_ZONA             = 9;
    private static final int COL_CAT_LISTINO      = 10;
    private static final int COL_TELEFONO1        = 11;
    private static final int COL_TELEFONO2        = 12;

    // -----------------------------------------------------------------------
    // ① Lettura COMPLETA (tutti i record in memoria)
    //    Comoda per file piccoli, ma per 50.000 righe preferisci i metodi batch.
    // -----------------------------------------------------------------------

    /** Auto-detect .csv / .xlsx in base all'estensione. */
    public List<ClienteLegacyDTO> readClienti(String filePath) throws IOException {
        if (filePath.toLowerCase().endsWith(".csv")) {
            return readClientiFromCsv(filePath);
        }
        return readClientiFromXlsx(filePath);
    }

    public List<ClienteLegacyDTO> readClientiFromCsv(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath)) {
            return readClientiFromCsv(is);
        }
    }

    public List<ClienteLegacyDTO> readClientiFromCsv(InputStream inputStream) throws IOException {
        List<ClienteLegacyDTO> result = new ArrayList<>();
        processCsvInBatches(inputStream, batch -> result.addAll(batch), Integer.MAX_VALUE);
        return result;
    }

    public List<ClienteLegacyDTO> readClientiFromXlsx(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath);
             Workbook wb = new XSSFWorkbook(is)) {
            return parseSheet(wb.getSheetAt(0));
        }
    }

    public List<ClienteLegacyDTO> readClientiFromXlsx(InputStream inputStream) throws IOException {
        try (Workbook wb = new XSSFWorkbook(inputStream)) {
            return parseSheet(wb.getSheetAt(0));
        }
    }

    // -----------------------------------------------------------------------
    // ② Lettura A BATCH con Consumer  ← CONSIGLIATA per file grandi
    //
    //    Il Consumer viene chiamato una volta ogni <batchSize> record.
    //    Ogni batch è una lista nuova: puoi salvarla su DB e poi buttarla via,
    //    così la memoria rimane costante indipendentemente dalle dimensioni del file.
    //
    //    Esempio d'uso:
    //
    //      service.readClientiInBatches("/var/data/clienti.csv", batch -> {
    //          clienteRepository.saveAll(batch);   // salva 1000 record
    //          log.info("Salvati {} record", batch.size());
    //      });
    // -----------------------------------------------------------------------

    /**
     * Legge il file a batch e invoca il consumer per ogni gruppo di record.
     *
     * @param filePath  path assoluto del file (.csv o .xlsx, auto-detect)
     * @param consumer  lambda/callback che riceve ogni batch di ClienteLegacyDTO
     */
    public void readClientiInBatches(String filePath, Consumer<List<ClienteLegacyDTO>> consumer)
            throws IOException {
        if (filePath.toLowerCase().endsWith(".csv")) {
            readCsvInBatches(filePath, consumer);
        } else {
            readXlsxInBatches(filePath, consumer);
        }
    }

    // --- CSV batch ---

    public void readCsvInBatches(String filePath, Consumer<List<ClienteLegacyDTO>> consumer)
            throws IOException {
        try (InputStream is = new FileInputStream(filePath)) {
            processCsvInBatches(is, consumer, batchSize);
        }
    }

    public void readCsvInBatches(InputStream inputStream, Consumer<List<ClienteLegacyDTO>> consumer)
            throws IOException {
        processCsvInBatches(inputStream, consumer, batchSize);
    }

    private void processCsvInBatches(InputStream inputStream,
                                     Consumer<List<ClienteLegacyDTO>> consumer,
                                     int size) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, csvCharset))) {

            List<ClienteLegacyDTO> batch = new ArrayList<>(size);
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    line = stripBom(line);
                    firstLine = false;
                    continue;               // salta intestazione
                }
                if (line.isBlank()) continue;

                batch.add(mapArrayToDto(splitCsvLine(line, csvDelimiter)));

                if (batch.size() == size) {
                    consumer.accept(Collections.unmodifiableList(batch));
                    batch = new ArrayList<>(size);   // nuovo batch pulito
                }
            }

            // flush dell'eventuale batch finale (< batchSize record)
            if (!batch.isEmpty()) {
                consumer.accept(Collections.unmodifiableList(batch));
            }
        }
    }

    // --- XLSX batch ---

    public void readXlsxInBatches(String filePath, Consumer<List<ClienteLegacyDTO>> consumer)
            throws IOException {
        try (InputStream is = new FileInputStream(filePath);
             Workbook wb = new XSSFWorkbook(is)) {
            processSheetInBatches(wb.getSheetAt(0), consumer);
        }
    }

    public void readXlsxInBatches(InputStream inputStream, Consumer<List<ClienteLegacyDTO>> consumer)
            throws IOException {
        try (Workbook wb = new XSSFWorkbook(inputStream)) {
            processSheetInBatches(wb.getSheetAt(0), consumer);
        }
    }

    private void processSheetInBatches(Sheet sheet, Consumer<List<ClienteLegacyDTO>> consumer) {
        List<ClienteLegacyDTO> batch = new ArrayList<>(batchSize);
        boolean firstRow = true;

        for (Row row : sheet) {
            if (firstRow) { firstRow = false; continue; }
            if (isRowEmpty(row)) continue;

            batch.add(mapRowToDto(row));

            if (batch.size() == batchSize) {
                consumer.accept(Collections.unmodifiableList(batch));
                batch = new ArrayList<>(batchSize);
            }
        }

        if (!batch.isEmpty()) {
            consumer.accept(Collections.unmodifiableList(batch));
        }
    }

    // -----------------------------------------------------------------------
    // Parsing Excel: sheet → lista completa (usato da readClientiFromXlsx)
    // -----------------------------------------------------------------------

    private List<ClienteLegacyDTO> parseSheet(Sheet sheet) {
        List<ClienteLegacyDTO> clienti = new ArrayList<>();
        boolean firstRow = true;
        for (Row row : sheet) {
            if (firstRow) { firstRow = false; continue; }
            if (isRowEmpty(row)) continue;
            clienti.add(mapRowToDto(row));
        }
        return clienti;
    }

    // -----------------------------------------------------------------------
    // Mapping
    // -----------------------------------------------------------------------

    private ClienteLegacyDTO mapRowToDto(Row row) {
        ClienteLegacyDTO dto = new ClienteLegacyDTO();
        dto.setRecId(parseIntegerCell(row, COL_REC_ID));
        dto.setCodice(parseStringCell(row, COL_CODICE));
        dto.setTipoCliente(parseStringCell(row, COL_TIPO_CLIENTE));
        dto.setRagioneSociale(parseStringCell(row, COL_RAGIONE_SOCIALE));
        dto.setIndirizzo(parseStringCell(row, COL_INDIRIZZO));
        dto.setCitta(parseStringCell(row, COL_CITTA));
        dto.setProv(parseStringCell(row, COL_PROV));
        dto.setCap(parseStringCell(row, COL_CAP));
        dto.setPiva(parseStringCell(row, COL_PIVA));
        dto.setZona(parseStringCell(row, COL_ZONA));
        dto.setCatListino(parseStringCell(row, COL_CAT_LISTINO));
        dto.setTelefono1(parseStringCell(row, COL_TELEFONO1));
        dto.setTelefono2(parseStringCell(row, COL_TELEFONO2));
        return dto;
    }

    private ClienteLegacyDTO mapArrayToDto(String[] f) {
        ClienteLegacyDTO dto = new ClienteLegacyDTO();
        dto.setRecId(parseIntegerField(get(f, COL_REC_ID)));
        dto.setCodice(get(f, COL_CODICE));
        dto.setTipoCliente(get(f, COL_TIPO_CLIENTE));
        dto.setRagioneSociale(get(f, COL_RAGIONE_SOCIALE));
        dto.setIndirizzo(get(f, COL_INDIRIZZO));
        dto.setCitta(get(f, COL_CITTA));
        dto.setProv(get(f, COL_PROV));
        dto.setCap(get(f, COL_CAP));
        dto.setPiva(get(f, COL_PIVA));
        dto.setZona(get(f, COL_ZONA));
        dto.setCatListino(get(f, COL_CAT_LISTINO));
        dto.setTelefono1(get(f, COL_TELEFONO1));
        dto.setTelefono2(get(f, COL_TELEFONO2));
        return dto;
    }

    // -----------------------------------------------------------------------
    // Utility CSV
    // -----------------------------------------------------------------------

    private String[] splitCsvLine(String line, String delimiter) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        sb.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (line.startsWith(delimiter, i)) {
                    tokens.add(sb.toString().trim());
                    sb.setLength(0);
                    i += delimiter.length() - 1;
                } else {
                    sb.append(c);
                }
            }
        }
        tokens.add(sb.toString().trim());
        return tokens.toArray(new String[0]);
    }

    private String get(String[] fields, int index) {
        if (index >= fields.length) return null;
        String val = fields[index].trim();
        return val.isEmpty() ? null : val;
    }

    private Integer parseIntegerField(String val) {
        if (val == null || val.isEmpty()) return null;
        try { return Integer.parseInt(val.replaceAll("[^\\d-]", "")); }
        catch (NumberFormatException e) { return null; }
    }

    private String stripBom(String line) {
        if (line != null && !line.isEmpty() && line.charAt(0) == '\uFEFF') {
            return line.substring(1);
        }
        return line;
    }

    // -----------------------------------------------------------------------
    // Utility Excel
    // -----------------------------------------------------------------------

    private String parseStringCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default:      return null;
        }
    }

    private Integer parseIntegerCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case NUMERIC: return (int) cell.getNumericCellValue();
                case STRING:  return Integer.parseInt(cell.getStringCellValue().trim());
                default:      return null;
            }
        } catch (NumberFormatException e) { return null; }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }
}
