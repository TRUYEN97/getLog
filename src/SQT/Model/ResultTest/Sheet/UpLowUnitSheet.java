/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.Sheet;

import SQT.Model.ResultTest.baseResult.NodeResult;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 21AK22
 */
public class UpLowUnitSheet extends AbsSheet {

    public UpLowUnitSheet(XSSFWorkbook workbook, String nameSheet) {
        super(workbook, nameSheet);
        createCell(0, 0).setCellValue("Title");
        createCell(1, 0).setCellValue("Up limit");
        createCell(2, 0).setCellValue("Low limit");
        createCell(3, 0).setCellValue("Unit");
        this.rowNum = 4;
    }

    @Override
    protected void createNewTitle(NodeResult title) {
        String titleName = title.getTitleNameExcell();
        this.titles.add(titleName);
        int colIndex = this.titles.indexOf(titleName);
        setValue(title.getTitleNameExcell(), createCell(0, colIndex));
        setValue(title.getUpLimit(), createCell(1, colIndex));
        setValue(title.getLowLimit(), createCell(2, colIndex));
        setValue(title.getUnit(), createCell(3, colIndex));
    }

}
