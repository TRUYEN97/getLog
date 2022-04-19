/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest;

import SQT.Model.ResultTest.Sheet.AbsSheet;
import SQT.Model.ResultTest.Sheet.AbsSheetType;
import SQT.Model.ResultTest.baseResult.ResultTest;
import SQT.Model.ServiceClass.Service;
import SQT.View.LogAnalysis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import SQT.Model.ResultTest.Sheet.Proxy.ISheetProxy;

/**
 *
 * @author 21AK22
 */
public class Excell {

    private XSSFWorkbook workbook;
    private final LogAnalysis ui;
    private final Map<List<String>, AbsSheet> listSheet;
    private AbsSheetType sheetType;
    private ISheetProxy sheetProxy;

    public Excell(LogAnalysis ui) {
        this.workbook = new XSSFWorkbook();
        this.ui = ui;
        this.listSheet = new HashMap<>();
    }

    public void start(ISheetProxy sheetProxy) {
        this.workbook = new XSSFWorkbook();
        this.listSheet.clear();
        this.sheetProxy = sheetProxy;
        this.ui.setEnabledBtSave(false);
    }

    public void end() {
        if (listSheet.isEmpty()) {
            return;
        }
        new Service().saveExcell(workbook, "Source/temp.xlsx");
        listSheet.clear();
        workbook = null;
        this.ui.setEnabledBtSave(true);
        this.ui.chooseFileToSave();
    }

    public void setSheetType(AbsSheetType sheetType) {
        this.sheetType = sheetType;
    }

    public AbsSheet createSheet(String nameSheet) {
        return sheetProxy.getSheet(workbook, nameSheet);
    }

    public void addRow(ResultTest resultTest) {
        List<String> title = resultTest.getCode();
        if (title == null || title.isEmpty()) {
            return;
        }
        AbsSheet sheet = this.sheetType.getSheet(title, listSheet);
        if (sheet == null) {
            sheet = createSheet("Sheet " + listSheet.size());
            listSheet.put(title, sheet);
        }
        sheet.addRow(resultTest);
    }
}
