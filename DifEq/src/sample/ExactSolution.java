package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;

public class ExactSolution extends Grid {
    private double C;

    ExactSolution() { super(); }

    ExactSolution(LineChart<Number, Number> chart) {
        super(chart);
        methodSeries.setName("Exact");
    }

    private double y(double x) {
        return Math.pow((C * Math.exp(2*x) + 2 * x + 1) / 6 ,1.5);
    }

    private void calculateConstant() {
        C = (6 * Math.pow(y0, ((double) 2 /(double) 3 )) - 2*x0 - 1) / Math.exp(2*x0);
    }

    void display() {
        hide();
        chart.getData().add(methodSeries);
    }

    void hide() {
        chart.setAnimated(false);
        chart.getData().remove(methodSeries);
        chart.setAnimated(true);
    }

    @Override
    protected boolean makeSeries() {
        initialize();

        for (int i = 0; i < N; i++)
            y[i] = y(x[i]);

        methodSeries.getData().clear();
        for (int i = 0; i < N ; i++)
            methodSeries.getData().add(new Data<Number, Number>(x[i], y[i]));
        return true;
    }

    double[] getY() {
        return y;
    }

    void setFields(double x0, double y0, double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.N = N;
        calculateConstant();
        makeSeries();
    }

}