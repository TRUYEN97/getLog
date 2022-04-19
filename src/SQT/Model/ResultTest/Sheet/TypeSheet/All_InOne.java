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
public class All_InOne extends AbsSheetType {

    public All_InOne(String name) {
        super(name);
    }

    @Override
    public AbsSheet getSheet(List<String> list, Map<List<String>, AbsSheet> map) {
        for (List<String> keyCode : map.keySet()) {
                return map.get(keyCode);
        }
        return null;
    }

}
