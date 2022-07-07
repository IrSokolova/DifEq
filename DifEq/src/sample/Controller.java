package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class Controller implements Initializable {

    @FXML private LineChart<Number, Number> functionChart;
    @FXML private LineChart<Number, Number> errorChart;
    @FXML private LineChart<Number, Number> errorDepChart;

    @FXML private CheckBox exactCheckBox;
    @FXML private CheckBox eulerCheckBox;
    @FXML private CheckBox imprEulerCheckBox;
    @FXML private CheckBox rKuttaCheckBox;

    @FXML private TextField NField;
    @FXML private TextField x0field;
    @FXML private TextField y0field;
    @FXML private TextField Xfield;

    @FXML private Button updateLocalErrorChart;
    @FXML private TextField NField1;
    @FXML private TextField x0field1;
    @FXML private TextField y0field1;
    @FXML private TextField Xfield1;

    @FXML private CheckBox eulerLocalCheckBox;
    @FXML private CheckBox imprEulerLocalCheckBox;
    @FXML private CheckBox rKuttaLocalCheckBox;

    @FXML private Label euErrorLabel;
    @FXML private Label imErrorLabel;
    @FXML private Label rkErrorLabel;

    @FXML private Label euLabel;
    @FXML private Label imLabel;
    @FXML private Label rkLabel;

    @FXML private CheckBox eulerErrorCheckBox;
    @FXML private CheckBox imprEulerErrorCheckBox;
    @FXML private CheckBox rKuttaErrorCheckBox;



    @FXML private TextField N0Field;
    @FXML private TextField N1Field;

    private SeriesModel model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new SeriesModel(functionChart, errorChart, errorDepChart);
        setFirstTabValues(1, 2, 6, 50);
        setSecondTabValues(1, 2, 6, 50);
        setThirdTabValues(10, 100);
        buildExact();
    }

    @FXML
    private void buildExact() {
        model.displayExact(exactCheckBox);
    }

    @FXML
    private void approxWithEuler() {
        model.displayApproximation(euLabel, model.getEuler(), eulerCheckBox);
    }

    @FXML
    private void approxWithImprovedEuler() {
        model.displayApproximation(imLabel, model.getImprovedEuler(), imprEulerCheckBox);
    }

    @FXML
    private void approxWithRungeKutta() {
        model.displayApproximation(rkLabel, model.getRungeKutta(), rKuttaCheckBox);
    }

    @FXML
    private void buildLocalEulerError() {
        model.displayLocalError(euErrorLabel, model.getEuler(), eulerLocalCheckBox);
    }

    @FXML
    private void buildLocalImpEulerError() {
        model.displayLocalError(imErrorLabel, model.getImprovedEuler(), imprEulerLocalCheckBox);
    }

    @FXML
    private void buildLocalRKuttaError() {
        model.displayLocalError(rkErrorLabel, model.getRungeKutta(), rKuttaLocalCheckBox);
    }

    @FXML
    private void buildEulerError() {
        if (eulerErrorCheckBox.isSelected())
            model.displayErrorDep(new EulerMethod(), model.eulerDep, getRange());
        else
            model.hideErrorDep(model.eulerDep);
    }

    @FXML
    private void buildImpEulerError() {
        if (imprEulerErrorCheckBox.isSelected())
            model.displayErrorDep(new ImprovedEulerMethod(), model.imEulerDep, getRange());
        else
            model.hideErrorDep(model.imEulerDep);
    }

    @FXML
    private void buildRKuttaError() {
        if (rKuttaErrorCheckBox.isSelected())
            model.displayErrorDep(new RungeKuttaMethod(), model.rKuttaDep, getRange());
        else
            model.hideErrorDep(model.rKuttaDep);
    }

    public void updateLocalError(){
        boolean errorOccurred = false;
        try {
            int N = Integer.parseInt(NField.getText());
            double x0 = Double.parseDouble(x0field.getText());
            double y0 = Double.parseDouble(y0field.getText());
            double X = Double.parseDouble(Xfield.getText());

            if (X < x0 || y0 < 0 ) {
                model.showError("Invalid range [x0, X] or y0 < 0 ");
                errorOccurred = true;
                throw new IllegalArgumentException("Invalid range [x0, X]");
            }
            model.updateValues(x0, y0, X, N);
        } catch (Exception e) {
            setSecondTabValues(model.getX0(), model.getY0(), model.getX(), model.getN());
            if (errorOccurred) return;
            model.showError("Incorrect type of input data!");
            return;
        }

        updateLocal();
    }

    public Pair<Integer, Integer> getRange() {
        int N0, N1;
        try {
            N0 = Integer.parseInt(N0Field.getText());
            N1 = Integer.parseInt(N1Field.getText());
            if (N0 > N1) throw new IllegalArgumentException("N0 > N1");
            model.setN0(N0);
            model.setN1(N1);
            return new Pair<Integer, Integer>(N0, N1);
        } catch (Exception e) {
            model.showError(e.getMessage());
            setThirdTabValues(model.getN0(), model.getN1());
            return null;
        }
    }

    @FXML
    public void update() {
        boolean errorOccurred = false;
        try {
            int N = Integer.parseInt(NField.getText());
            double x0 = Double.parseDouble(x0field.getText());
            double y0 = Double.parseDouble(y0field.getText());
            double X = Double.parseDouble(Xfield.getText());

            if (X < x0 || y0 < 0 ) {
                model.showError("Invalid range [x0, X] or y0 < 0 ");
                errorOccurred = true;
                throw new IllegalArgumentException("Invalid range [x0, X]");
            }
            model.updateValues(x0, y0, X, N);
        } catch (Exception e) {
            setFirstTabValues(model.getX0(), model.getY0(), model.getX(), model.getN());
            if (errorOccurred) return;
            model.showError("Incorrect type of input data!");
            return;
        }

        updateMethods();
    }

    private void updateMethods() {
        buildExact();
        approxWithEuler();
        approxWithImprovedEuler();
        approxWithRungeKutta();
    }

    @FXML
    private void updateLocal() {
        buildLocalEulerError();
        buildLocalImpEulerError();
        buildLocalRKuttaError();
    }

    @FXML
    private void updateError() {
        buildEulerError();
        buildImpEulerError();
        buildRKuttaError();
    }

    private void setFirstTabValues(double x0, double y0, double X, int N) {
        x0field.setText(Double.toString(x0));
        y0field.setText(Double.toString(y0));
        Xfield.setText(Double.toString(X));
        NField.setText(Integer.toString(N));
    }

    private void setSecondTabValues(double x0, double y0, double X, int N) {
        x0field1.setText(Double.toString(x0));
        y0field1.setText(Double.toString(y0));
        Xfield1.setText(Double.toString(X));
        NField1.setText(Integer.toString(N));
    }

    private void setThirdTabValues(int n0, int n1) {
        N0Field.setText(Integer.toString(n0));
        N1Field.setText(Integer.toString(n1));
    }

}