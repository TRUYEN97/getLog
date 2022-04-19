/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.IQLogVersion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 21AK22
 */
public class Format {

    private final List<TitleNode> titleNode;
    private final StringBuilder listIndexRow;

    public Format() {
        this.titleNode = new ArrayList<>();
        this.listIndexRow = new StringBuilder();
    }

    public boolean addNewNode(TitleNode nodeRow) {
        if (nodeRow == null) {
            return false;
        }
        if (this.titleNode.add(nodeRow)) {
            listIndexRow.append(nodeRow.getTitleIndex());
            while (nodeRow.hasNext()) {
                listIndexRow.append(nodeRow.next().getRow());
            }
            nodeRow.remove();
            return true;
        }
        return false;
    }

    public boolean haveIndex(int indexHave) {
        return indexHave < this.titleNode.size();
    }

    public TitleNode getNode(int index) {
        return this.titleNode.get(index);
    }

    public boolean isNotEmpty() {
        return !titleNode.isEmpty();
    }

    public String getCode() {
        return listIndexRow.toString();
    }

}
