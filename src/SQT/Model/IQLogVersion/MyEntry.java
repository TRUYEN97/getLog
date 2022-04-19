/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SQT.Model.IQLogVersion;

/**
 *
 * @author 21AK22
 */
interface MyEntry<row, item> {
    row getRow();
    
    item getItem();
    
    item setItem(item value);
}
