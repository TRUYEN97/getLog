/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.Sheet;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 21AK22
 */
public abstract class AbsSheetType {
    private final String name;

    public AbsSheetType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public abstract AbsSheet getSheet(List<String> code, Map<List<String>, AbsSheet> listSheet);
}
