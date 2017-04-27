package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Main extends Application {
    private static boolean run = true;
    String test = "Hello";
    Button cut, copy, clean;
    ChoiceBox choicebox;
    String appendText ="\t";
    TextArea textArea;


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Clipboard List");
        primaryStage.setScene(new Scene(root, 700, 550));
        primaryStage.show();


        textArea = (TextArea) root.lookup("#textarea");
        choicebox = (ChoiceBox)  root.lookup("#choicebox");




        ChoiceBox choiceBox = (ChoiceBox) root.lookup("#choicebox");


        choiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String oldValue, String newValue) {
                if (newValue.equals("List")) {
                    appendText = "\n";
                } else if (newValue.equals("List")) {
                    appendText = "\t";
                }
            }
        });


        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();


        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override public void keyReleased(GlobalKeyEvent event) {

                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        if ((event.isControlPressed() == true) && (event.getVirtualKeyCode() == 67)){




                            Toolkit toolkit = Toolkit.getDefaultToolkit();
                            Clipboard clipboard = (Clipboard) toolkit.getSystemClipboard();
                            try {
                                String result = (String) clipboard.getData(DataFlavor.stringFlavor);
                                if (result != null)
                                textArea.appendText(result + appendText);
                            } catch (UnsupportedFlavorException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });




                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE)
                    run = false;
            }


        });


        cut = (Button) root.lookup("#cut");
        copy = (Button) root.lookup("#copy");
        clean = (Button) root.lookup("#clean");

        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StringSelection stringSelection = new StringSelection(textArea.getText().toString());
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);

                textArea.clear();
            }
        });

        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StringSelection stringSelection = new StringSelection(textArea.getText().toString());
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);


            }
        });

        clean.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.clear();
            }
        });


    }





    public static void main(String[] args) {
        launch(args);


    }


}
