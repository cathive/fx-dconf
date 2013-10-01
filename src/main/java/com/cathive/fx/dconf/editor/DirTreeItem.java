/*
 * Copyright © 2013 The Cat Hive Developers
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the licence, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.cathive.fx.dconf.editor;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.gnome.dconf.Client;

/**
 * @author Benjamin P. Jung
 */
public class DirTreeItem extends TreeItem<String> {

    final String dir;
    final Image image;
    final Client client;

    // We cache whether the File is a leaf or not. A File is a leaf if
    // it is not a directory and does not have any files contained within
    // it. We cache this as isLeaf() is called often, and doing the
    // actual check on File is expensive.
    private boolean isLeaf;

    // We do the children and leaf testing only once, and then set these
    // booleans to false so that we do not check again during this
    // run. A more complete implementation may need to handle more
    // dynamic file system situations (such as where a folder has files
    // added after the TreeView is shown). Again, this is left as an
    // exercise for the reader.
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public DirTreeItem(final Client client, final String dir, final Image image) {

        super();

        this.dir = dir;
        this.image = image;
        this.client = client;

        this.setValue(dir);
        this.setGraphic(new ImageView(image));

    }

    @Override
    public ObservableList<TreeItem<String>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;

            // First getChildren() call, so we actually go off and
            // determine the children of the File contained in this TreeItem.
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            isLeaf = false; // TODO Determine Leaf status by other means.
        }
        return isLeaf;
    }

    private ObservableList<TreeItem<String>> buildChildren(TreeItem<String> TreeItem) {
        String s = TreeItem.getValue();
        if (s!= null) {
            final List<String> dirs = client.listDirs(this.getDirPath());
            if (!dirs.isEmpty()) {
                ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();

                for (String dir : dirs) {
                    children.add(new DirTreeItem(client, dir, image));
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }

    protected String getDirPath() {
        final StringBuilder sb = new StringBuilder(this.getValue());
        TreeItem<String> parent = this.getParent();
        while (parent != null) {
            sb.insert(0, parent.getValue());
            parent = parent.getParent();
        }
        return sb.toString();
    }

}
