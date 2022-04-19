/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SQT.Model.ResultTest.Sheet.Proxy;

import SQT.Model.ResultTest.Sheet.AbsSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public interface ISheetProxy {
    AbsSheet getSheet(XSSFWorkbook workbook, String nameSheet) ;
}
