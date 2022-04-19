/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem.Abstract;

import SQT.View.TableService;

/**
 *
 * @author 21AK22
 */
public abstract class TableMenuItem extends AbsMenuItem{
    
    protected final TableService tableService;

    protected TableMenuItem(TableService tableService,String text) {
        super(text);
        this.tableService = tableService;
    }
    
}
