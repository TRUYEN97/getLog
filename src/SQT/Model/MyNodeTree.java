/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author 21AK22
 */
public class MyNodeTree<T> extends DefaultMutableTreeNode {

    private T value;

    public MyNodeTree(T value, Object userObject) {
        super(userObject);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (value == null) {
            return;
        }
        this.value = value;
    }

}
