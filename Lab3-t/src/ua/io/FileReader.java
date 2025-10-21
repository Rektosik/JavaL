package ua.io;

import ua.exception.InvalidDataException;
import ua.model.PersonRecord;
import ua.util.LoggerUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class FileReader {
    private final Logger log = LoggerUtil.getLogger();
    private final Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    public List<PersonRecord> readPersons(Path file) throws InvalidDataException, IOException {
        List<PersonRecord> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                row++;
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    log.warning("Malformed row " + row + " in " + file);
                    throw new InvalidDataException("Row " + row + " malformed");
                }
                String idS = parts[0].trim();
                String name = parts[1].trim();
                String email = parts[2].trim();
                int id;
                try {
                    id = Integer.parseInt(idS);
                } catch (NumberFormatException e) {
                    log.warning("Invalid id at row " + row);
                    throw new InvalidDataException("Invalid id at row " + row, e);
                }
                if (!emailPattern.matcher(email).matches()) {
                    log.warning("Invalid email at row " + row + ": " + email);
                    throw new InvalidDataException("Invalid email at row " + row);
                }
                try {
                    PersonRecord p = new PersonRecord(id, name, email);
                    out.add(p);
                    log.info("Created person " + p);
                } catch (IllegalArgumentException e) {
                    log.log(Level.WARNING, "Validation error at row " + row + ": " + e.getMessage());
                    throw new InvalidDataException("Validation error at row " + row + ": " + e.getMessage(), e);
                }
            }
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "File not found: " + file, e);
            throw e;
        } catch (IOException e) {
            log.log(Level.SEVERE, "IO error reading: " + file, e);
            throw e;
        }
        return out;
    }
}
