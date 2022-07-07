package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;

public class EulerMethod extends NumericalMethod {

    EulerMethod() { super(); }

    EulerMethod(LineChart<Number, Number> funcChart,
                LineChart<Number, Number> errChart) {
        super(funcChart, errChart);
        methodSeries.setName("Euler");
        localErrorSeries.setName("Euler local error");
        globalErrorSeries.setName("Euler global error");
    }

    @Override
    void calculateLocalError(double[] exactY) {
        this.exactY = exactY;

        for (int i = 0; i < N - 1; i++) {
            closestApprox[i + 1] = exactY[i] + h * f(x[i], exactY[i]);
        }

        this.exactY = exactY;
        localErrorSeries.getData().clear();
        double error;

        for (int i = 0; i < N; i++) {
            error = Math.abs(exactY[i] - closestApprox[i]);
            localErrorSeries.getData().add(new Data<Number, Number>(x[i], error));
        }
    }

    @Override
    protected boolean makeSeries() {
        initialize();

        for (int i = 0; i < N - 1; i++) {
            y[i + 1] = y[i] + h * f(x[i], y[i]);
        }

        methodSeries.getData().clear();
        for (int i = 0; i < N; i++)
            methodSeries.getData().add(new Data<Number, Number>(x[i], y[i]));
        return true;
    }
}
