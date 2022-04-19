/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ServiceClass;

import java.util.List;

/**
 *
 * @author 21AK22
 */
public class HasStartAndEndWith extends AbsCompareType {

    public HasStartAndEndWith(String keyWord) {
        super(keyWord);
    }

    @Override
    public boolean notOk(String str, List<String> contain) {
        return !str.startsWith(contain.get(1)) ||
                !str.endsWith(contain.get(contain.size() - 1));
    }

}
