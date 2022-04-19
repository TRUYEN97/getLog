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
public class DeleteFolder extends FolderMenu{

    public DeleteFolder(FolderScan scans) {
        super(scans, "Delete");
    }

    @Override
    protected ActionListener setAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scans.deleteFolder();
            }
        };
    }

    @Override
    protected KeyStroke setKeyStroke() {
        return KeyStroke.getKeyStroke("ctrl z");
    }

    @Override
    protected ImageIcon setIcon() {
        return new ImageIcon("icon\\remove.png");
    }
}
