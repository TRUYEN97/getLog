/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller;

import SQT.Model.ServiceClass.Service;
import SQT.View.LogAnalysis;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author 21AK22
 */
public class InitSource {

    private static final String DIR_KEY_WORDS = "Source\\keyWords.json";
    private static final String TITLE = "title";
    private static final String ITEM = "item";
    private static final String CONTAIN = "contain";
    private static final String MATCH = "match";
    private static final String DIR_SHOOSER = "dir";
    private static final String DIR_TREE_FOLDER= "dirTree";
    private final Service service;
    private final JSONObject keyJson;

    public InitSource() {
        this.service = new Service();
        String strSuorce = service.readFile(DIR_KEY_WORDS);
        if (strSuorce == null || strSuorce.isEmpty()) {
            keyJson = new JSONObject();
        } else {
            keyJson = new JSONObject(strSuorce);
        }
    }

    public void saveKeyWords() {
        service.saveFile(DIR_KEY_WORDS, keyJson.toString());
    }

    private static String getValue(JSONObject json, String key, String defaultV) throws JSONException {
        if (json.isNull(key)) {
            return defaultV;
        }
        return json.getString(key);
    }

    public void setDir(String value) {
        keyJson.put(DIR_SHOOSER, value);
    }
    
    public void setDirTre(String value) {
        keyJson.put(DIR_TREE_FOLDER, value);
    }

    public void setTitle(String value) {
        keyJson.put(TITLE, value);
    }

    public void setItem(String value) {
        keyJson.put(ITEM, value);
    }

    public void setContain(String value) {
        keyJson.put(CONTAIN, value);
    }

    public void setMacth(String value) {
        keyJson.put(MATCH, value);
    }
    
    public String getDir() {
        return getValue(keyJson, DIR_SHOOSER, "..");
    }
    
    public String getDirTree() {
        return getValue(keyJson, DIR_TREE_FOLDER, "..");
    }

    public String getTitle() {
        return getValue(keyJson, TITLE, "");
    }

    public String getItem() {
        return getValue(keyJson, ITEM, "");
    }

    public String getContain() {
        return getValue(keyJson, CONTAIN, "");
    }

    public String getMatch() {
        return getValue(keyJson, MATCH, "");
    }
}
