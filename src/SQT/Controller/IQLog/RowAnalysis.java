/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.IQLog;

import SQT.Model.ServiceClass.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author 21AK22
 */
class RowAnalysis {

    private final HashSet<String> unit;
    private final Service service;

    public RowAnalysis() {
        this.unit = new HashSet<>(
                Arrays.asList(
                        new String[]{" MHz", " dBm", " dB", " %", " s",
                            " us", " ms", " sec", " msec", " secs", " CEL",
                            " ppm", " Degree", " times", " octets", " packets"}
                )
        );
        this.service = new Service();
    }

    public boolean isTitle(String line) {
        if (line == null) {
            return false;
        }
        return line.matches("^\\d+\\..+_{3,}$");
    }

    public boolean isLogPass(String line) {
        return line != null && line.contains(" *  P A S S  *");
    }

    public String subAndTrim(String data, String rg1, String rg2) {
        String result = service.subString(data, rg1, rg2);
        if (result == null) {
            return "";
        }
        return result.trim();
    }

    public boolean isContainOneOf(List<List<String>> listKeys, String line) {
        if (listKeys.isEmpty()) {
            return true;
        }
        for (List<String> keys : listKeys) {
            if (service.compareContain(line, keys)) {
                return true;
            }
        }
        return false;
    }

    public String getUnit(String str) {
        if (hasUnit(str)) {
            return service.containElement(str, unit);
        }
        return "";
    }

    public String getLowLimit(String str) {
        if (hasUnit(str)) {
            if (!str.contains("(,)")) {
                return subAndTrim(str,"(", ",");
            }
        }
        return "";
    }

    public String getUpLimit(String str) {
        if (hasUnit(str)) {
            if (!str.contains("(,)")) {
                return subAndTrim(str,",", ")");
            }
        }
        return "";
    }

    public boolean hasUnit(String str) {
        return service.containOneOf(str, unit);
    }

    public String getValue(String str) {
        if (hasUnit(str)) {
            return service.findStringGroup(str, "^\\S+(?=\\s)");
        }
        return str;
    }

    public String getTitle(String line) {
        return subAndTrim(line, null, " ___");
    }

}
