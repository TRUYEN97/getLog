/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem;

import SQT.View.MyMenuItem.Abstract.FileMenu;
import SQT.Controller.FileScans;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author 21AK22
 */
public class ScanFile extends FileMenu {

    public ScanFile(FileScans scans) {
        super(scans, "Scan file");
    }

    @Override
    protected ActionListener setAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scans.scanAllFle();
            }
        };
    }

    @Override
    protected KeyStroke setKeyStroke() {
        return KeyStroke.getKeyStroke("ctrl q");
    }

    @Override
    protected ImageIcon setIcon() {
        return new ImageIcon("icon\\searching.png");
    }
}
