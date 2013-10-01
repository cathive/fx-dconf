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

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.gnome.dconf.Client;


/**
 * @author Benjamin P. Jung
 */
public class DConfEditor extends Application implements Initializable {

    private ResourceBundle resources;
    private Client client;

    // UI components
    @FXML private TreeView<String> dirTree;
    @FXML private TableView dirTable;
    @FXML private Image folderImage;

    private TreeItem<String> rootNode;


    @Override
    public void init() throws Exception {
        super.init();
        this.resources = ResourceBundle.getBundle(DConfEditor.class.getName());
        this.client = Client.create();
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        primaryStage.setTitle(this.resources.getString("app.title"));
        primaryStage.setResizable(true);
        primaryStage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("appicon-16x16.png")),
                new Image(getClass().getResourceAsStream("appicon-24x24.png")),
                new Image(getClass().getResourceAsStream("appicon-32x32.png")),
                new Image(getClass().getResourceAsStream("appicon-48x48.png")),
                new Image(getClass().getResourceAsStream("appicon-64x64.png")),
                new Image(getClass().getResourceAsStream("appicon-256x256.png"))
        );

        final FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(final Class<?> aClass) {
                return DConfEditor.this;
            }
        });
        loader.setLocation(getClass().getResource("DConfEditor.fxml"));
        loader.setResources(this.resources);
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        this.client.close();
        super.stop();
    }


    public static void main(final String... args) {
        Application.launch(args);
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {

        this.rootNode = new DirTreeItem(client, "/", folderImage);
        this.rootNode.setExpanded(true);
        this.dirTree.setShowRoot(false);
        this.dirTree.setRoot(rootNode);

        final List<String> dirs = client.listDirs("/");
        Collections.sort(dirs);

    }

}
