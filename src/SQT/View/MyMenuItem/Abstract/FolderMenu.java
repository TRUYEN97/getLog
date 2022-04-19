/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem.Abstract;

import SQT.Controller.FolderScan;

/**
 *
 * @author 21AK22
 */
public abstract class FolderMenu extends AbsMenuItem{
    protected final FolderScan scans;
    public FolderMenu(FolderScan scans, String text) {
        super(text);
        this.scans = scans;
    }
}
