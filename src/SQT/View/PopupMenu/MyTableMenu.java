/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.PopupMenu;

import SQT.View.LogAnalysis;
import SQT.View.MyMenuItem.RefeshFile;
import SQT.View.MyMenuItem.deleteFile;
import SQT.View.MyMenuItem.copyFile;
import SQT.View.iMenu;
import SQT.View.MyMenuItem.SearchTable;
import SQT.View.MyMenuItem.openFile;
import SQT.View.TableService;
import javax.swing.JPopupMenu;

/**
 *
 * @author 21AK22
 */
public class MyTableMenu implements iMenu<JPopupMenu> {

    private final JPopupMenu menu;
    private final TableService tableService;

    public MyTableMenu(LogAnalysis analysis) {
        this.menu = new JPopupMenu();
        this.tableService = analysis.getTableService();
        init();
    }

    private void init() {
        this.menu.add(new RefeshFile(tableService).getMenu());
        this.menu.add(new openFile(tableService).getMenu());
        this.menu.add(new SearchTable(tableService).getMenu());
        this.menu.add(new deleteFile(tableService).getMenu());
        this.menu.add(new copyFile(tableService).getMenu());
    }

    @Override
    public JPopupMenu getMenu() {
        return this.menu;
    }
}
