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
public class SimpleSheet extends AbsSheet {

    public SimpleSheet(XSSFWorkbook workbook, String nameSheet) {
        super(workbook, nameSheet);
    }

    @Override
    protected void createNewTitle(NodeResult title) {
        String titleName = title.getTitleNameExcell();
        this.titles.add(titleName);
        int colIndex = this.titles.indexOf(titleName);
        setValue(title.getTitleNameExcell(), createCell(0, colIndex));
    }
    
}
