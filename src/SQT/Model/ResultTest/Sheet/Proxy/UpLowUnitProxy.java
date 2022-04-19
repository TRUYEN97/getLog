/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.Sheet.Proxy;

import SQT.Model.ResultTest.Sheet.UpLowUnitSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class UpLowUnitProxy implements ISheetProxy{

    @Override
    public UpLowUnitSheet getSheet(XSSFWorkbook workbook, String nameSheet) {
        return new UpLowUnitSheet(workbook, nameSheet);
    }
}
