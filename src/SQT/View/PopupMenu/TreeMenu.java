/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.PopupMenu;

import SQT.View.MyMenuItem.stopScanFile;
import SQT.Controller.FileScans;
import SQT.Controller.FolderScan;
import SQT.View.MyMenuItem.DeleteFolder;
import SQT.View.MyMenuItem.RefeshNodeFolder;
import SQT.View.MyMenuItem.ScanFile;
import SQT.View.MyMenuItem.stopScanFolder;
import SQT.View.iMenu;
import javax.swing.JPopupMenu;

/**
 *
 * @author 21AK22
 */
public class TreeMenu implements iMenu<JPopupMenu> {

    private final JPopupMenu menu;
    private final FileScans fileScans;
    private final FolderScan folderScan;

    public TreeMenu(FileScans fileScans, FolderScan folderScan) {
        this.menu = new JPopupMenu();
        this.fileScans = fileScans;
        this.folderScan = folderScan;
        init();
    }

    private void init() {
        this.menu.add(new RefeshNodeFolder(this.folderScan).getMenu());
        this.menu.add(new ScanFile(this.fileScans).getMenu());
        this.menu.add(new DeleteFolder(this.folderScan).getMenu());
        this.menu.add(new stopScanFile(this.fileScans).getMenu());
        this.menu.add(new stopScanFolder(this.folderScan).getMenu());
    }

    @Override
    public JPopupMenu getMenu() {
        return this.menu;
    }
}
