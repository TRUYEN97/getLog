/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.Sheet.Proxy;

import SQT.Model.ResultTest.Sheet.AbsSheet;
import SQT.Model.ResultTest.Sheet.UnitSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class UnitProxy implements ISheetProxy {

    @Override
    public AbsSheet getSheet(XSSFWorkbook workbook, String nameSheet) {
        return new UnitSheet(workbook, nameSheet);
    }

}
