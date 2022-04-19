/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem;

import SQT.View.MyMenuItem.Abstract.TableMenuItem;
import SQT.View.TableService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author 21AK22
 */
public class RefeshFile extends TableMenuItem{
    public RefeshFile(final TableService tableService) {
        super(tableService, "Refresh");
    }
    @Override
    protected ActionListener setAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableService.refresh();
            }
        };
    }

    @Override
    protected KeyStroke setKeyStroke() {
        return KeyStroke.getKeyStroke("ctrl r");
    }

    @Override
    protected ImageIcon setIcon() {
       return  new ImageIcon("icon\\list.png");
    }
}
