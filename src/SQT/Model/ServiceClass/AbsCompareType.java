/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SQT.Model.ServiceClass;

import java.util.List;

/**
 *
 * @author 21AK22
 */
public abstract class AbsCompareType extends Contain {

    protected String keyWord;

    protected AbsCompareType(String keyWord) {
        this.keyWord = keyWord;
    }

    public boolean isKeyWord(String key) {
        if (key == null || this.keyWord == null) {
            return false;
        }
        return this.keyWord.equals(key);
    }

    private boolean paramNotAvail(String str, List<String> contain) {
        return str == null
                || contain == null
                || contain.isEmpty()
                || !isKeyWord(contain.get(0));
    }

    public boolean compare(String str, List<String> contain) {
        if (paramNotAvail(str, contain) || notOk(str, contain)) {
            return false;
        }
        return containAll(str, contain.listIterator(1));
    }

    protected abstract boolean notOk(String str, List<String> contain);
}
