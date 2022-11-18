/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.baseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author 21AK22
 */
public class ResultTest implements Iterator<NodeResult>, Serializable {

    private final List<NodeResult> results;
    private final String dirFile;
    private int currIndex;
    private String currTitle;
    private final List<String> code;

    public ResultTest(String dirFile) {
        this.results = new ArrayList<>();
        this.currIndex = 0;
        this.dirFile = dirFile;
        this.code = new ArrayList<>();
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public void add(NodeResult nodeResult) {
        if (nodeResult != null && nodeResult.isAvailable()) {
            this.results.add(nodeResult);
            String titleName = nodeResult.getTitleName();
            if (this.code.contains(titleName)) {
                return;
            }
            this.code.add(titleName);
        }
    }

    public NodeResult get(int index) {
        if (index < 0 || index >= results.size()) {
            return null;
        }
        return results.get(index);
    }

    @Override
    public boolean hasNext() {
        return currIndex < this.results.size();
    }

    @Override
    public NodeResult next() {
        return get(currIndex++);
    }

    @Override
    public void remove() {
        currIndex = 0;
    }

    public void setCurrTitle(String title) {
        this.currTitle = title;
    }

    public String getCurrTitle() {
        return currTitle;
    }

    public String getFileName() {
        return this.dirFile;
    }

    public List<String> getCode() {
        if (this.code.isEmpty()) {
            return null;
        }
        return this.code;
    }

}
