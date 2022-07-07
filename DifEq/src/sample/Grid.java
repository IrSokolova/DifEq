package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;

public abstract class Grid {
    Series<Number, Number> methodSeries;
    LineChart<Number, Number> chart;
    double x[], y[];
    double x0, y0, X, h;
    int N;

    Grid() { methodSeries = new Series<Number, Number>(); }

    Grid(LineChart<Number, Number> chart) {
        this.chart = chart;
        methodSeries = new Series<Number, Number>();
    }

    void initialize() {
        h = (X - x0) / N;
        x = new double[N];
        x[0] = x0;
        for (int i = 1; i < N; i++)
            x[i] = x[i - 1] + h;
        y = new double[N];
        y[0] = y0;
    }

    abstract void display();

    abstract void hide();

    protected abstract boolean makeSeries();

}