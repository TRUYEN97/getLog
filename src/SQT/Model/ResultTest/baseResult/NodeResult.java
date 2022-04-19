/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ResultTest.baseResult;

/**
 *
 * @author 21AK22
 */
public class NodeResult {

    private final String title;
    private String item;
    private String value;
    private String unit;
    private String lowLimit;
    private String upLimit;
    private boolean isAvailable = false;

    public NodeResult(String title) {
        this.title = title;
    }

    public void setUpLimit(String upLimit) {
        this.upLimit = upLimit;
    }

    public String getUnit() {
        return unit;
    }

    public String getTitleNameExcell() {
        if (item.isBlank()) {
            return title;
        }
        return String.format("%s | %s", title, item);
    }

    public String getTitleName() {
        return title;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setLowLimit(String lowLimit) {
        this.lowLimit = lowLimit;
    }

    public String getValue() {
        return value;
    }

    public String getLowLimit() {
        return lowLimit;
    }

    public String getUpLimit() {
        return upLimit;
    }

    public void setItem(String item) {
        isAvailable = true;
        this.item = item;
    }

    public void setValue(String value) {
        if (value == null) {
            this.value = null;
            return;
        }
        this.value = value.trim();
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
