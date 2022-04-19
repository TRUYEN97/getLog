/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.Sheet;

import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.Model.ResultTest.baseResult.ResultTest;
import SQT.Model.ServiceClass.Contain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public abstract class AbsSheet implements Cloneable {

    protected static final String TITLE = "Title";
    protected XSSFSheet sheet;
    protected List<String> titles;
    protected int rowNum;
    protected Contain contain;
    private final XSSFWorkbook workbook;
    private final List<String> DateFormat;

    protected AbsSheet(XSSFWorkbook workbook, String nameSheet) {
        this.workbook = workbook;
        this.rowNum = 1;
        this.titles = new ArrayList<>();
        this.contain = new Contain();
        this.titles.add(TITLE);
        if (workbook.getSheet(nameSheet) == null) {
            this.sheet = workbook.createSheet(nameSheet);
        } else {
            this.sheet = workbook.getSheet(nameSheet);
        }
        createCell(0, 0).setCellValue(TITLE);
        this.DateFormat = new ArrayList<>();
        this.DateFormat.add("yyyy/MM/dd HH:mm:ss");
        this.DateFormat.add("yyyy-MM-dd HH:mm:ss");
    }

    public void addRow(ResultTest result) {
        createCell(rowNum, this.titles.indexOf(TITLE)).
                setCellValue(result.getFileName());
        while (result.hasNext()) {
            NodeResult title = result.next();
            addTitle(title);
        }
        rowNum++;
    }

    protected Cell createCell(int rowIndex, int colIndex) {
        XSSFRow row = rowAt(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            return cell;
        }
        return row.createCell(colIndex);
    }

    private XSSFRow rowAt(int rowIndex) {
        XSSFRow row = this.sheet.getRow(rowIndex);
        if (row != null) {
            return row;
        }
        return this.sheet.createRow(rowIndex);
    }

    private void addTitle(NodeResult title) {
        try {
            String titleName = title.getTitleNameExcell();
            if (isNewTitleName(titleName)) {
                createNewTitle(title);
            }
            setValue(title.getValue(), 
                    createCell(rowNum, titles.indexOf(titleName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void createNewTitle(NodeResult title);

    protected void setValue(String title, Cell cell) throws NumberFormatException {
        if (title == null) {
            cell.setCellValue("");
        } else {
            if (title.matches("[0-9]+,[0-9]+")) {
                title = title.replace(',', '.');
            }
            if (this.contain.isNumeric(title)) {
                if (title.contains(".")) {
                    cell.setCellValue(Double.parseDouble(title));
                } else {
                    cell.setCellValue(Long.parseLong(title));
                }
            } else if (isDateTime(title)) {
                Date date;
                date = getDateTime(title);
                if (date == null) {
                    cell.setCellValue("");
                } else {
                    CellStyle cellStyle = this.workbook.createCellStyle();
                    cellStyle.setDataFormat((short) 14);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(date);
                }
            } else {
                cell.setCellValue(title);
            }
        }
    }

    private Date getDateTime(String value) {
        for (String format : this.DateFormat) {
            try {
                return new SimpleDateFormat(format).parse(value);
            } catch (ParseException ex) {
            }
        }
        return null;
    }

    private boolean isNewTitleName(String titleName) {
        return !this.titles.contains(titleName);
    }

    private boolean isDateTime(String value) {
        return value.matches("^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$")
                || value.matches("^(\\d{4})/(\\d{2})/(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$");
    }
}
