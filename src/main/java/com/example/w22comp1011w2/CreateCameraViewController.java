package com.example.w22comp1011w2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;

public class CreateCameraViewController implements Initializable {

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Spinner<Integer> resolutionSpinner;

    @FXML
    private CheckBox slrCheckBox;

    @FXML
    private Label msgLabel;
/*
    public CreateCameraViewController(ComboBox<String> brandComboBox, TextField modelTextField, Label msgLabel, TextField priceTextField, Spinner<Integer> resolutionSpinner, CheckBox slrCheckBox) {
        this.brandComboBox = brandComboBox;
        this.modelTextField = modelTextField;
        this.msgLabel = msgLabel;
        this.priceTextField = priceTextField;
        this.resolutionSpinner = resolutionSpinner;
        this.slrCheckBox = slrCheckBox;
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgLabel.setText("");
        brandComboBox.getItems().addAll(Camera.getManufacturers());

        //configure the spinner to only accept realistic camera resolutions
        // we will use spinner value factory
        //the constructor is taking the minimum, maximum, default, step/increment
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5,100,20,5);

        resolutionSpinner.setValueFactory(spinnerValueFactory);
        TextField spinnerTextField = resolutionSpinner.getEditor();
        resolutionSpinner.setEditable(true);

        //creating a custom change listener class wasnt very effectibe - extra files and
        //didn't allow us to access JavaFX objects
        //SpinnerChangeListener scl = new SpinnerChangeListener();
        //spinnerTextField.textProperty().addListener(scl);
        // spinnerTextField.textProperty().addListener(new SpinnerChangeListener());

        //we can create an anonymous inner class
       /*     spinnerTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    try{
                        Integer.parseInt(newValue);
                        msgLabel.setText("");
                    }catch(Exception a) {
                        msgLabel.setText("Only whole numbers allowed for the resolution");
                        spinnerTextField.setText(oldValue);
                    }
                }
            });
        */
        //Lets use a lambda expression
        spinnerTextField.textProperty().addListener((obs, oldValue, newValue) -> {
            try{
                Integer.parseInt(newValue);
            }catch(Exception a)
            {
                spinnerTextField.setText(oldValue);
            }
        });

        //update the price text field such that it will only accept a double
        priceTextField.textProperty().addListener((obs, oldValue, newValue)->{
            try{
                Double.parseDouble(newValue);
            }
            catch (Exception a)
            {
                priceTextField.setText(oldValue);
            }
        });

    }

    @FXML
    private void createCamera()
    {

        String make = brandComboBox.getSelectionModel().getSelectedItem();
        String model = modelTextField.getText();
        boolean slr = slrCheckBox.isSelected();
        int res = -1;
        double price = -1;
        try {
            res = resolutionSpinner.getValue();
            price = Double.parseDouble(priceTextField.getText());

        }
        catch (Exception e)
        {
            msgLabel.setText("Resolution must be whole number");
        }
        if(res !=-1 && price!= -1)
        {
            try {
                Camera newCamera = new Camera(make, model, res, slr, price);
                Formatter formatter = new Formatter(new File("camera.txt"));
                formatter.format("new camera: %s\n",newCamera);
                formatter.close();
                DBUtility.insertCameraIntoDB(newCamera);
                msgLabel.setText(newCamera.toString());
            }catch (IllegalArgumentException e) {
                msgLabel.setText(e.getMessage());
            }catch (Exception e)
            {
                msgLabel.setText("Error writing to file "+e.getMessage());
            }

        }
    }
}