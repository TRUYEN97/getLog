/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.IQLogVersion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author 21AK22
 */
public class TitleNode implements Iterator<MyEntry<Integer, String>> {

    private String title;
    private int titleIndex;
    private final List<Entry<Integer, String>> format;
    private int entryIndex = 0;

    public TitleNode(String title) {
        this.title = title;
        this.format = new ArrayList<>();
    }

    public int getTitleIndex() {
        return titleIndex;
    }

    public void setTitleIndex(int rowIndex) {
        this.titleIndex = rowIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean addFormatRow(int index, String item) {
        return this.format.add(new Entry<>(index, item));
    }

    @Override
    public boolean hasNext() {
        return entryIndex < this.format.size();
    }

    @Override
    public Entry<Integer, String> next() {
        if (!hasNext()) {
            return null;
        }
        return this.format.get(entryIndex++);
    }

    @Override
    public void remove() {
        this.entryIndex = 0;
    }

    public boolean isNotEmpty() {
        return !format.isEmpty();
    }

    public class Entry<row, item> implements MyEntry<row, item> {

        private final row row;
        private item item;

        public Entry(row row, item item) {
            this.row = row;
            this.item = item;
        }

        @Override
        public row getRow() {
            return row;
        }

        @Override
        public item getItem() {
            return item;
        }

        @Override
        public item setItem(item value) {
            item oldValue = this.item;
            this.item = value;
            return oldValue;
        }

    }
}
