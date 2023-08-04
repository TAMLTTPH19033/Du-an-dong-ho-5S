package com.datn.dongho5s.Controller.Imei;

import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Entity.Seri;
import com.datn.dongho5s.Service.SeriService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/seri")
public class ImeiController {

    private final String UPLOAD_DIR = "./uploads/";

    @Autowired
    SeriService seriService;

    @GetMapping("")
    public String init(){
        return "admin/imei/imei";
    }

    @PostMapping("/save")
    public String upTrangThaiImei(Seri seri, RedirectAttributes redirectAttributes){
//        seriService.update(seri);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/imei";
    }

    @PostMapping ("/importExcept")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/imei";
        }

        try {
            String filePath = file.getOriginalFilename();
            System.out.println(filePath);
            if (filePath != null && (filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))) {
                redirectAttributes.addFlashAttribute("message", "Vui lòng chọn file excel");
                return "redirect:/imei";
            } else {
                readExcel(filePath);
            }
            redirectAttributes.addFlashAttribute("message", "Đã nhập thành công Imei vào trong DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/imei";
    }

    public static List<Seri> readExcel(String excelFilePath) throws IOException {
        List<Seri> listSeris = new ArrayList<>();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            Seri seri = new Seri();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        seri.setIdImei(String.valueOf(cellValue));
                        break;
                    default:
                        break;
                }

            }
            listSeris.add(seri);
        }

        workbook.close();
        inputStream.close();
        listSeris.forEach(item->{
            System.out.println(item.toString());
        });
        return listSeris;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }

}
