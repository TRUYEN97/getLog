/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ServiceClass;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author 21AK22
 */
public class Contain implements IContain {

    @Override
    public boolean containOneOf(String str, Collection<String> contain) {
        if (paramNotAvail(str, contain)) {
            return false;
        }
        return containAt(str, contain) != -1;
    }

    @Override
    public int containAt(String str, Collection<String> contain) {
        int index = -1;
        for (String element : contain) {
            index++;
            if (str.contains(element)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public String containElement(String str, Collection<String> contain) {
        for (String element : contain) {
            if (str.contains(element)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public boolean containAll(String str, Collection<String> contain) {
        if (paramNotAvail(str, contain)) {
            return false;
        }
        for (String element : contain) {
            if (!str.contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containAll(String str, Iterator<String> iter) {
        if (str == null || iter == null) {
            return false;
        }
        while (iter.hasNext()) {
            if (!str.contains(iter.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isNumeric(String value) {
        return value.matches("^-?[0-9]+\\.?[0-9]*$");
    }

    private boolean paramNotAvail(String str, Collection<String> contain) {
        return str == null
                || contain == null
                || contain.isEmpty();
    }

}
