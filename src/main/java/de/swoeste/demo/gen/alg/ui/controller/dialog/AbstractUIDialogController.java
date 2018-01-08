/*-
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. I license this file to You under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swoeste.demo.gen.alg.ui.controller.dialog;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.demo.gen.alg.ui.GenAlgApplication;
import de.swoeste.demo.gen.alg.ui.controller.UIBackingBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author swoeste
 */
public class AbstractUIDialogController {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractUIDialogController.class);

    private UIBackingBean       backingBean;

    public AbstractUIDialogController() {
        this.backingBean = UIBackingBean.getInstance();
    }

    public void setBackingBean(final UIBackingBean backingBean) {
        this.backingBean = backingBean;
    }

    public UIBackingBean getBackingBean() {
        return this.backingBean;
    }

    protected static <T> T showDialog(final Window owner, final String fxml, final String title) {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GenAlgApplication.class.getResource(fxml));

            final Parent dialog = loader.load();

            final Scene scene = new Scene(dialog);
            scene.getStylesheets().add(GenAlgApplication.class.getResource("css/styles.css").toExternalForm()); //$NON-NLS-1$

            final Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(new Image(GenAlgApplication.class.getResourceAsStream("img/infinitum.png"))); //$NON-NLS-1$
            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.NONE);
            dialogStage.setScene(scene);
            dialogStage.show();

            return loader.getController();
        } catch (IOException ex) {
            final String msg = "Unable to create dialog"; //$NON-NLS-1$
            LOG.error(msg, ex);
            throw new IllegalStateException(msg, ex);
        }
    }

}
