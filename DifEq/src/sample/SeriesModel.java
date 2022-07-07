package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class SeriesModel {
    private LineChart<Number, Number> errorDepChart;
    private ExactSolution exactSolution;
    private EulerMethod euler;
    private ImprovedEulerMethod improvedEuler;
    private RungeKuttaMethod rungeKutta;

    Series<Number, Number> eulerDep = new Series<Number, Number>();
    Series<Number, Number> imEulerDep = new Series<Number, Number>();
    Series<Number, Number> rKuttaDep = new Series<Number, Number>();

    private double x0 = 1, y0 = 2, X = 6;
    private int N = 50, N0 = 10, N1 = 100;

    private final String normalColor = "#000000";
    private final String errorColor = "#F50505";

    SeriesModel(LineChart<Number, Number> functionChart,
                LineChart<Number, Number> errorChart,
                LineChart<Number, Number> errorDepChart) {

        this.errorDepChart = errorDepChart;

        exactSolution = new ExactSolution(functionChart);
        exactSolution.setFields(x0,y0,X,N);
        euler = new EulerMethod(functionChart, errorChart);
        euler.setFields(x0,y0,X,N,exactSolution.getY());
        improvedEuler = new ImprovedEulerMethod(functionChart, errorChart);
        improvedEuler.setFields(x0,y0,X,N,exactSolution.getY());
        rungeKutta = new RungeKuttaMethod(functionChart, errorChart);
        rungeKutta.setFields(x0,y0,X,N,exactSolution.getY());

        updateValues(x0, y0, X, N);

        eulerDep.setName("Euler error");
        imEulerDep.setName("Im.Euler error");
        rKuttaDep.setName("R-Kutta error");
    }

    void displayExact(CheckBox cb) {
        if (cb.isSelected()) exactSolution.display();
        else exactSolution.hide();
    }

    void displayApproximation(Label l, NumericalMethod method, CheckBox cb) {
        l.setTextFill(Color.web(normalColor));
        boolean methodFailed = method.isFailed();
        if (cb.isSelected() && !methodFailed)
            method.display();
        else {
            if (methodFailed) {
                cb.setSelected(false);
                l.setTextFill(Color.web(errorColor));
            }
            method.hide();
        }
    }

    void displayLocalError(Label l, NumericalMethod method, CheckBox cb) {
        l.setTextFill(Color.web(normalColor));
        boolean methodFailed = method.isFailed();
        if (cb.isSelected() && !methodFailed)
            method.displayError();
        else {
            if (methodFailed) {
                cb.setSelected(false);
                l.setTextFill(Color.web(errorColor));
            }
            method.hideError();
        }
    }

    void displayErrorDep(NumericalMethod method,
                         Series<Number, Number> errorDep,
                         Pair<Integer, Integer> range) {
        ExactSolution exact = new ExactSolution();

        if (range != null) {
            hideErrorDep(errorDep);
            errorDep.getData().clear();
            for (int i = range.getKey(); i <= range.getValue(); i++) {
                exact.setFields(x0, y0, X, i);
                method.setFields(x0, y0, X, i, exact.getY());
                double iError = method.getMaxError();
                errorDep.getData().add(new Data<Number, Number>(i, iError));
            }
            errorDepChart.getData().add(errorDep);
        }
    }

    void hideErrorDep(Series<Number, Number> errorDep) {
        errorDepChart.setAnimated(false);
        errorDepChart.getData().remove(errorDep);
        errorDepChart.setAnimated(true);
    }

     void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error occurred");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    void updateValues(double x0, double y0, double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.N = N;

        exactSolution.setFields(x0, y0, X, N);
        double[] exact = exactSolution.getY();

        euler.setFields(x0, y0, X, N, exact);
        improvedEuler.setFields(x0, y0, X, N, exact);
        rungeKutta.setFields(x0, y0, X, N, exact);
    }

    int getN0() {
        return N0;
    }

    void setN0(int n0) {
        N0 = n0;
    }

    int getN1() {
        return N1;
    }

    void setN1(int n1) {
        N1 = n1;
    }

    double getX0() {
        return x0;
    }

    double getY0() {
        return y0;
    }

    double getX() {
        return X;
    }

    int getN() {
        return N;
    }

    EulerMethod getEuler() {
        return euler;
    }

    ImprovedEulerMethod getImprovedEuler() {
        return improvedEuler;
    }

    RungeKuttaMethod getRungeKutta() {
        return rungeKutta;
    }
}