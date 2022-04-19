/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.Json;

import SQT.Controller.Core.AbsMode;
import SQT.Model.ResultTest.Sheet.Proxy.UpLowUnitProxy;
import SQT.View.LogAnalysis;
import java.io.BufferedReader;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class JsonData extends AbsMode {

    private final JSONObject jsonData;

    public JsonData(String name, LogAnalysis _ui) {
        super(name, _ui);
        this.jsonData = new JSONObject();
    }

    @Override
    protected boolean dataAnalysis(BufferedReader input, String nameFile) {
//        this.jsonData.
        return true;
    }

    @Override
    protected boolean init() {
        if (wareHouse.getItemKey().isEmpty() && wareHouse.getTittleKey().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Key word is empty!!");
            return false;
        }
        this.excell.start(new UpLowUnitProxy());
        return true;
    }

    @Override
    protected void end() {
        this.excell.end();
    }

}
