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
public class LogFormat {

    private static volatile LogFormat instance;
    private final List<Format> formats;
    private int CurrIndex;
    private int oldIndex;
    private boolean newRound;

    public LogFormat() {
        this.formats = new ArrayList<>();
        this.CurrIndex = 0;
        this.newRound = true;
    }

    public Format getFormat() {
        if (formats.isEmpty()) {
            return null;
        }
        if (!newRound) {
            nextIndex();
        }
        if (CurrIndex != oldIndex || newRound) {
            newRound = false;
            return formats.get(CurrIndex);
        }
        return null;
    }

    public void addFormat(Format format) {
        String newCode = format.getCode();
        if (checkCode(newCode)) {
            this.formats.add(format);
        }
    }

    private boolean checkCode(String newCode) {
        for (Format fm : formats) {
            String codeOld = fm.getCode();
            if (newCode.equals(codeOld)) {
                return false;
            }
            if (newCode.startsWith(codeOld)) {
                formats.remove(fm);
                return true;
            }
        }
        return true;
    }

    private int nextIndex() {
        if (CurrIndex < formats.size() - 1) {
            return CurrIndex++;
        }
        return (CurrIndex = 0);
    }

    public void markIndex() {
        newRound = true;
        this.oldIndex = CurrIndex;
    }

    public void clear() {
        this.formats.clear();
        this.CurrIndex = 0;
        this.oldIndex = 0;
    }
}
