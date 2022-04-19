/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem;

import SQT.Controller.FileScans;
import SQT.View.MyMenuItem.Abstract.FileMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author 21AK22
 */
public class stopScanFile extends FileMenu{

    public stopScanFile(FileScans scans) {
        super(scans, "Stop scan file");
    }

    @Override
    protected ActionListener setAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scans.stopScanFile();
            }
        };
    }

    @Override
    protected KeyStroke setKeyStroke() {
        return KeyStroke.getKeyStroke("ctrl h");
    }

    @Override
    protected ImageIcon setIcon() {
        return new ImageIcon("icon\\stoppingBlack.png");
    }
    
}
