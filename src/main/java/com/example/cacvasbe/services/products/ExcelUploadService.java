package com.example.cacvasbe.services.products;

import com.example.cacvasbe.entities.ProductList;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ExcelUploadService {
    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<ProductList> getProductDataFromExcel(InputStream inputStream) throws IOException {
        List<ProductList> licencesList = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("VAS");

            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                ProductList productList = new ProductList();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1 -> {
                            productList.setProducts(cell.getStringCellValue());
                            System.out.println("Product name "+ productList.getProducts());
                        }
                        case 2 -> {
                            productList.setDescription(cell.getStringCellValue());
                            System.out.println("Description "+ productList.getDescription());
                        }
                        case 3 -> {
                            productList.setPrice_interval(cell.getStringCellValue());
                            System.out.println("Interval "+ productList.getPrice_interval());
                        }
                        case 4 -> {
                            productList.setProposed_price(BigDecimal.valueOf(cell.getNumericCellValue()));
                            System.out.println("Product price "+ productList.getProposed_price());
                        }
                        case 5 -> {
                            productList.setCAC(BigDecimal.valueOf(cell.getNumericCellValue()));
                            System.out.println("case 3 "+ productList.getCAC());
                        }
                        case 6 -> {
                            productList.setOASIS(BigDecimal.valueOf(cell.getNumericCellValue()));
                            System.out.println("case 4 "+ productList.getOASIS());
                        }
                        default -> {

                        }
                    }
                    cellIndex++;
                }
                licencesList.add(productList);
            }
        }catch (IOException e) {
            e.getStackTrace();
        }
        return licencesList;
    }

}
