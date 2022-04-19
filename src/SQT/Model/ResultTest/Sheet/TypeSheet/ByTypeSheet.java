/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.Sheet.TypeSheet;

import SQT.Model.ResultTest.Sheet.AbsSheet;
import SQT.Model.ResultTest.Sheet.AbsSheetType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 21AK22
 */
public class ByTypeSheet extends AbsSheetType{

    public ByTypeSheet(String name) {
        super(name);
    }

    @Override
    public AbsSheet getSheet(List<String> code, Map<List<String>, AbsSheet> listSheet) {
        for (List<String> keyCode : listSheet.keySet()) {
            if (keyCode.containsAll(code)) {
                return listSheet.get(keyCode);
            }
        }
        return null;
    }
    
}
