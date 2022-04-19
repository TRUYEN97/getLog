/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem.Abstract;

import SQT.Controller.FileScans;

/**
 *
 * @author 21AK22
 */
public abstract class FileMenu extends AbsMenuItem{
    
    protected final FileScans scans;
    public FileMenu(FileScans scans, String text) {
        super(text);
        this.scans = scans;
    }
    
}
