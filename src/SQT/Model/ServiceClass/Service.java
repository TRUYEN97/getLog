/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model.ServiceClass;

import Base.Communicate.Cmd.Cmd;
import Base.ReadAble.AbstractStream.SubClass.ReadStreamOnTime;
import SQT.Model.IOFile;
import SQT.Model.WareHouse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 21AK22
 */
public class Service implements IContain {

    private final IOFile ioFile;
    private final Map<String, AbsCompareType> compareTypeList;
    private final Contain contain;

    public Service() {
        this.ioFile = new IOFile();
        this.compareTypeList = new HashMap<>();
        this.contain = new Contain();
        this.compareTypeList.put("*", new HasStartWith("*"));
        this.compareTypeList.put("$", new HasEndWith("$"));
        this.compareTypeList.put("*$", new HasStartAndEndWith("*$"));
    }

    @Override
    public boolean containOneOf(String str, Collection<String> contain) {
        return this.contain.containOneOf(str, contain);
    }

    @Override
    public int containAt(String str, Collection<String> contain) {
        return this.contain.containAt(str, contain);
    }

    @Override
    public String containElement(String str, Collection<String> contain) {
        return this.contain.containElement(str, contain);
    }

    @Override
    public boolean containAll(String str, Collection<String> contain) {
        return this.contain.containAll(str, contain);
    }

    @Override
    public boolean containAll(String string, Iterator<String> itrtr) {
        return this.contain.containAll(string, itrtr);
    }

    @Override
    public boolean isNumeric(String value) {
        return contain.isNumeric(value);
    }

    public IContain getContain() {
        return contain;
    }

    public boolean isContainOneOf(List<List<String>> listKeys, String line) {
        if (listKeys.isEmpty()) {
            return true;
        }
        for (List<String> keys : listKeys) {
            if (compareContain(line, keys)) {
                return true;
            }
        }
        return false;
    }

    public boolean compareContain(String str, List<String> contain) {
        if (str == null) {
            return false;
        }
        if (contain == null || contain.isEmpty() || compareType(str, contain)) {
            return true;
        }
        return containAll(str, contain);
    }

    public String getDate(long time) {
        try {
            SimpleDateFormat simFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            return simFormat.format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void openFile(File file) {
        if (file != null && file.exists()) {
            new Cmd(new ReadStreamOnTime()).sendCommand(file.getPath());
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    String.format("Can't open file: %s !", file));
        }
    }

    public String findStringGroup(String data, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public List<String> findStringGroups(String data, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    public void copyFile(File suorce, File newFile) {
        if (suorce == null || newFile == null) {
            return;
        }
        while (newFile.exists()) {
            newFile = new File(createNewName(newFile));
        }
        try {
            Files.copy(suorce.toPath(), newFile.toPath());
//            System.out.println(String.format("copy: %s -> %s", suorce, newFile));
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public boolean copyFileNotRename(File suorce, File newFile) {
        if (suorce == null || newFile == null) {
            return false;
        }
        if (newFile.exists() && !newFile.delete()) {
            System.out.println(String.format("copy: %s -> %s: false", suorce, newFile));
            return false;
        }
        try {
            Files.copy(suorce.toPath(), newFile.toPath());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString());
            return false;
        }
    }

    private String createNewName(File file) {
        String nameFile = file.getName();
        if (nameFile.contains(".")) {
            String originName = subString(nameFile, null, ".").trim();
            String duoi = subString(nameFile, ".", null);
            return String.format("%s%s%s.%s",
                    file.getParent(),
                    File.separator,
                    upNumbleName(originName), duoi);
        }
        return String.format("%s%s%s",
                file.getParent(),
                File.separator,
                upNumbleName(nameFile));
    }

    private String upNumbleName(String nameFile) throws NumberFormatException {
        /////////////
        String str, originName;
        if (nameFile.matches(".+_\\(-?[0-9]+\\)$")
                && (str = subString(nameFile, "(", ")")) != null
                && (originName = subString(nameFile, null, "_(")) != null) {
            Integer numble = Integer.valueOf(str) + 1;
            return String.format("%s_(%s)", originName, numble);
        }
        return String.format("%s_(1)", nameFile);
    }

    public String subString(String data, String rg1, String rg2) {
        if (data == null) {
            return null;
        }
        if (rg1 != null && rg2 != null) {
            if (!data.contains(rg2) || !data.contains(rg1)) {
                return null;
            }
            if (data.indexOf(rg1) >= data.lastIndexOf(rg2)) {
                return null;
            }
            return data.substring(data.indexOf(rg1) + rg1.length(), data.lastIndexOf(rg2));
        } else if (rg1 == null && rg2 != null) {
            if (!data.contains(rg2)) {
                return null;
            }
            return data.substring(0, data.lastIndexOf(rg2));
        } else if (rg1 != null) {
            if (!data.contains(rg1)) {
                return null;
            }
            return data.substring(data.indexOf(rg1) + rg1.length());
        } else {
            return null;
        }
    }

    public String subStringLastIndex(String data, String rg1, String rg2) {
        if (data == null) {
            return null;
        }
        if (rg1 != null && rg2 != null) {
            if (!data.contains(rg2) || !data.contains(rg1)) {
                return null;
            }
            if (data.lastIndexOf(rg1) >= data.lastIndexOf(rg2)) {
                return null;
            }
            return data.substring(data.lastIndexOf(rg1) + rg1.length(), data.lastIndexOf(rg2));
        } else if (rg1 == null && rg2 != null) {
            if (!data.contains(rg2)) {
                return null;
            }
            return data.substring(0, data.lastIndexOf(rg2));
        } else if (rg1 != null) {
            if (!data.contains(rg1)) {
                return null;
            }
            return data.substring(data.lastIndexOf(rg1) + rg1.length());
        } else {
            return null;
        }
    }

    public void saveFile(String dir, String data) {
        try {
            FileWriter fileWriter = ioFile.getFileWriter(dir);
            fileWriter.write(data);
            fileWriter.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public String readFile(String dir) {
        BufferedReader reader = ioFile.getBufferedReader(dir);
        String line;
        StringBuilder builder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\r\n");
            }
            return builder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean saveExcell(XSSFWorkbook workbook, String path) {
        if (workbook == null) {
            return false;
        }
        FileOutputStream output = new IOFile().getFileOutStream(path);
        try {
            workbook.write(output);
            WareHouse.getInstance().setPathExcellFileTemp(path);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean compareType(String str, List<String> contain) {
        String keyWord = contain.get(0);
        if (this.compareTypeList.containsKey(keyWord)) {
            return this.compareTypeList.get(keyWord).compare(str, contain);
        }
        return false;
    }

    public List<String> slipKeyWord(String data) {
        if (data == null) {
            return null;
        }
        List<String> newKeys = new ArrayList<>();
        newKeys.addAll(Arrays.asList(data.split(":")));
        return newKeys;
    }

}
