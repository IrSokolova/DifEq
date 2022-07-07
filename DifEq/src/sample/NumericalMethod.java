package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

public abstract class NumericalMethod extends Grid {
    Series<Number, Number> globalErrorSeries;
    Series<Number, Number> localErrorSeries;

    private LineChart<Number, Number> errChart;
    private double maxError;
    private boolean isFailed = false;
    protected double[] exactY;
    protected double[] closestApprox;

    NumericalMethod() {
        super();
        globalErrorSeries = new Series<Number, Number>();
        localErrorSeries = new Series<Number, Number>();
    }

    NumericalMethod(LineChart<Number, Number> funcChart,
                        LineChart<Number, Number> errChart) {
        super(funcChart);
        this.errChart = errChart;
        globalErrorSeries = new Series<Number, Number>();
        localErrorSeries = new Series<Number, Number>();
    }

    @Override
    void initialize() {
        h = (X - x0) / N;
        x = new double[N];
        x[0] = x0;
        for (int i = 1; i < N; i++)
            x[i] = x[i - 1] + h;
        y = new double[N];
        y[0] = y0;
        closestApprox = new double[N];
        closestApprox[0] = y0;
    }

    @Override
    void display() {
        hide();
        chart.getData().add(methodSeries);
    }

    void displayError(){
        hide();
        errChart.getData().add(localErrorSeries);
    }

    @Override
    void hide() {
        chart.setAnimated(false);
        chart.getData().remove(methodSeries);
        chart.setAnimated(true);
    }

    void hideError(){
        errChart.setAnimated(false);
        errChart.getData().remove(localErrorSeries);
        errChart.setAnimated(true);
    }

    double f(double x, double y) {
        return (3 * y - x * Math.pow(y, ((double) 1/ (double)  3) )) ;
    }


    void calculateGlobalError(double[] exactY) {
        this.exactY = exactY;
        maxError = 0;
        globalErrorSeries.getData().clear();
        double error;

        for (int i = 0; i < N; i++) {
            error = Math.abs(exactY[i] - y[i]);
            globalErrorSeries.getData().add(new Data<Number, Number>(x[i], error));
        }

        maxError = Math.abs(exactY[N-1] - y[N-1]);
    }

    abstract void calculateLocalError(double[] exactY);

    double getMaxError() {
        return maxError;
    }

    void setFields(double x0, double y0, double X, int N, double[] exactY) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.N = N;
        isFailed = !makeSeries();
        calculateGlobalError(exactY);
        calculateLocalError(exactY);
    }

    public boolean isFailed() {
        return isFailed;
    }
}

