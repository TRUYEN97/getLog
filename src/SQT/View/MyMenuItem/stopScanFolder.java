/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem;

import SQT.Controller.FolderScan;
import SQT.View.MyMenuItem.Abstract.FolderMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author 21AK22
 */
public class stopScanFolder extends FolderMenu{

    public stopScanFolder(FolderScan scans) {
        super(scans, "Stop scan folder");
    }

    @Override
    protected ActionListener setAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scans.stopScanFolder();
            }
        };
    }

    @Override
    protected KeyStroke setKeyStroke() {
        return KeyStroke.getKeyStroke("ctrl g");
    }

    @Override
    protected ImageIcon setIcon() {
        return new ImageIcon("icon\\stopping.png");
    }
    
}
