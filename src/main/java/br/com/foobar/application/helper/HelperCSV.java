package br.com.foobar.application.helper;

import br.com.foobar.adapter.controller.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@Component
public class HelperCSV {

    public static final String SEPARATOR = ";";
    private static final Logger LOGGER =
            LoggerFactory.getLogger(HelperCSV.class);

    private HelperCSV() {
    }

    public static Set<ProductDTO> parseProductsDTOList(final byte[] bytes) throws IOException {

        Set<ProductDTO> productDTOS = new HashSet<>();

        File fileTmp = new File(
                String.format("/tmp/file-%s.tmp", System.currentTimeMillis()));

        InputStream inputStream = new ByteArrayInputStream(bytes);
        OutputStream outStream = new FileOutputStream(fileTmp);

        try {
            readFileContent(inputStream, outStream);
            extractContent(fileTmp, productDTOS);
        } catch (FileNotFoundException e) {
            LOGGER.error(
                    String.valueOf(String.format("An error was encountered while processing the file: %s",
                            e.getMessage())));
        } finally {
            outStream.close();
            inputStream.close();
            Files.delete(fileTmp.toPath());
        }
        return productDTOS;
    }

    private static void readFileContent(InputStream inputStream, OutputStream outStream)
            throws IOException {

        byte[] buffer = new byte[8 * 1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

    }

    private static void extractContent(File file, Set<ProductDTO> productDTOS)
            throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(SEPARATOR);

                Long code = Long.parseLong(values[0]);
                String name = String.valueOf(values[1]);
                boolean freeShiping = !String.valueOf(values[2]).equals("0");
                String description = String.valueOf(values[3]);
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(values[4]));
                Long category = Long.parseLong(values[5]);

                productDTOS.add(new ProductDTO(code, name,
                        freeShiping, description,
                        price, category));

            }
        }
    }

}
