/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View.MyMenuItem.Abstract;

import SQT.View.iMenu;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author 21AK22
 */
public abstract class AbsMenuItem implements iMenu<JMenuItem> {

    private final JMenuItem menuItem;

    protected AbsMenuItem( String text) {
        this.menuItem = new JMenuItem();
        this.menuItem.setText(text);
        init();
    }

    private void init() {
        this.menuItem.setIcon(setIcon());
        this.menuItem.setAccelerator(setKeyStroke());
        this.menuItem.addActionListener(setAction());
    }

    protected abstract ActionListener setAction();

    protected abstract KeyStroke setKeyStroke();

    protected abstract ImageIcon setIcon();

    @Override
    public JMenuItem getMenu() {
        return menuItem;
    }

}
