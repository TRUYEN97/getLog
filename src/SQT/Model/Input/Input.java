/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.Input;

import SQT.Model.ServiceClass.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
public class Input {

    private static volatile Input instance = new Input();
    private final List<List<String>> title;
    private final Set<String> titleLine;
    private final List<List<String>> item;
    private final Set<String> itemLine;
    private final Service service;

    private Input() {
        this.title = new ArrayList<>();
        this.titleLine = new HashSet<>();
        this.item = new ArrayList<>();
        this.itemLine = new HashSet<>();
        this.service = new Service();
    }

    public static Input getInstance() {
        return instance;
    }

    public boolean addTittle(String title) {
        if (title == null) {
            return false;
        }
        return this.titleLine.add(title) && this.title.add(this.service.slipKeyWord(title));
    }

    public boolean addItem(String itemLine) {
        if (itemLine == null) {
            return false;
        }
        return 
        this.itemLine.add(itemLine) && this.item.add(service.slipKeyWord(itemLine));
    }

    public List<List<String>> getTittleKey() {
        return title;
    }

    public Set<String> getLineTittleKey() {
        return titleLine;
    }

    public List<List<String>> getItemKey() {
        return item;
    }

    public Set<String> getLineItemKey() {
        return itemLine;
    }

    public void clearItem() {
        this.item.clear();
        this.itemLine.clear();
    }

    public void clearTittle() {
        this.title.clear();
        this.titleLine.clear();
    }

}
