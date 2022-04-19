/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SQT.Model.ServiceClass;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author 21AK22
 */
public interface IContain {

    boolean containOneOf(String str, Collection<String> contain);

    int containAt(String str, Collection<String> contain);

    String containElement(String str, Collection<String> contain);

    boolean containAll(String str, Collection<String> contain);
    
    boolean containAll(String str, Iterator<String> contain);
    
    boolean isNumeric(String value);
}
