/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model;

import SQT.Model.ServiceClass.Service;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author 21AK22
 */
public class MyFileFilter {

    private final HashSet<String> contain;
    private final HashSet<String> match;

    public MyFileFilter() {
        this.contain = new HashSet<>();
        this.match = new HashSet<>();
    }

    public void setContain(String contain) {
        this.contain.clear();
        this.contain.addAll(Arrays.asList(contain.split(",")));
    }

    public void setMatch(String match) {
        this.match.clear();
        this.match.addAll(Arrays.asList(match.split(",")));
    }

    private boolean isFile(File pathname) {
        return pathname.isFile() && !pathname.isHidden();
    }

    private boolean isFolder(File pathname) {
        return pathname.isDirectory()&& !pathname.isHidden();
    }

    private boolean isContain(File pathname, HashSet<String> contain) {
        String name = pathname.getName();
        return new Service().containOneOf(name, contain);
    }

    private boolean isContainAll(File pathname, HashSet<String> contain) {
        String name = pathname.getName();
        return new Service().containAll(name, contain);
    }

    public FileFilter getFilterDirs() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return isFolder(pathname) || (isFile(pathname) && isContain(pathname, contain)
                        && isContainAll(pathname, match));
            }
        };
    }

    public FileFilter getFilterJustFile() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return isFile(pathname)
                        && isContain(pathname, contain)
                        && isContainAll(pathname, match);
            }
        };
    }

}
