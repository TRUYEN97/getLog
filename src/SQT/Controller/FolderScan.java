/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller;

import SQT.Model.MyNodeTree;
import SQT.View.LogAnalysis;
import java.awt.Cursor;
import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author 21AK22
 */
public final class FolderScan {

    private Thread scanFolder;
    private final JTree treeFolder;
    private DefaultTreeModel modeTree;
    private boolean stopScan;
    private final LogAnalysis ui;

    public FolderScan(LogAnalysis ui) {
        this.treeFolder = ui.getTree();
        this.modeTree = (DefaultTreeModel) this.treeFolder.getModel();
        this.ui = ui;
        reset();
    }

    public void scanFolder(File[] folders) {
        if (folders == null || folders.length <= 0) {
            return;
        }
        this.scanFolder = new Thread() {
            private File[] folders;

            @Override
            public void run() {
                if (ui.getCursor().getType() != Cursor.WAIT_CURSOR) {
                    ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                }
                MyNodeTree<File> nodeTree;
                reset();
                if (folders.length == 1) {
                    nodeTree = new MyNodeTree<>(folders[0], folders[0].getName());
                    insertFolder(nodeTree);
                } else {
                    nodeTree = new MyNodeTree<>(folders[0], folders[0].getParent());
                    for (File folder : folders) {
                        MyNodeTree<File> nodeTreeChilder;
                        nodeTreeChilder = new MyNodeTree<>(folder, folder.getName());
                        nodeTree.add(nodeTreeChilder);
                        insertFolder(nodeTreeChilder);
                    }
                }
                modeTree = new DefaultTreeModel(nodeTree);
                treeFolder.setModel(modeTree);
                modeTree.reload();
                ui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            private Thread setFolders(File[] folders) {
                this.folders = folders;
                return this;
            }
        }.setFolders(folders);
        this.scanFolder.start();
    }

    public void reset() {
        stopScan = false;
    }

    public void insertFolder(MyNodeTree<File> node) {
        if (ui.getCursor().getType() != Cursor.WAIT_CURSOR) {
            ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        }
        if (node.getValue().listFiles().length <= 0 || (node.getChildCount() > 0
                && ((MyNodeTree<File>) node.getFirstChild()).getValue() != null)) {
            return;
        }
        reloadNode(node);
        ui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void reloadNode(MyNodeTree<File> node) {
        File[] files;
        try {
            files = node.getValue().listFiles();
            node.removeAllChildren();
            MyNodeTree<File> nodeChilder;
            for (File file : files) {
                if (stopScan) {
                    break;
                }
                if (file.isDirectory() && !file.isHidden()) {
                    nodeChilder = new MyNodeTree<>(file, file.getName());
                    if (file.isDirectory() && !file.isHidden()) {
                        nodeChilder.add(new MyNodeTree(null, ""));
                    }
                    node.add(nodeChilder);
                }
            }
            if (node.getChildCount() == 0) {
                node.add(new MyNodeTree(null, "Files"));
            }
            this.modeTree.reload(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFolder() {
        if (this.treeFolder.getSelectionCount() > 0) {
            if (ui.getCursor().getType() != Cursor.WAIT_CURSOR) {
                ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            }
            DefaultTreeModel model = (DefaultTreeModel) this.treeFolder.getModel();
            TreePath[] treePath = this.treeFolder.getSelectionPaths();
            for (TreePath treePath1 : treePath) {
                MyNodeTree selectedNode = (MyNodeTree) treePath1.getLastPathComponent();
                if (selectedNode != null && !selectedNode.isRoot() && selectedNode.getValue() != null) {
                    model.removeNodeFromParent(selectedNode);
                    model.reload(selectedNode.getParent());
                }
            }
            ui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void stopScanFolder() {
        if (this.isFolderScanning()) {
            this.stopScan = true;
        }
    }

    public boolean isFolderScanning() {
        return this.scanFolder != null && this.scanFolder.isAlive();
    }

    public void refesh() {
        if (this.treeFolder.getSelectionCount() > 0) {
            if (ui.getCursor().getType() != Cursor.WAIT_CURSOR) {
                ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            }
            TreePath[] treePath = this.treeFolder.getSelectionPaths();
            for (TreePath treePath1 : treePath) {
                MyNodeTree selectedNode = (MyNodeTree) treePath1.getLastPathComponent();
                if (selectedNode != null && selectedNode.getValue() != null) {
                    reloadNode(selectedNode);
                }
            }
            ui.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
