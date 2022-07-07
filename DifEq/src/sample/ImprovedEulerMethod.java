package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;

public class ImprovedEulerMethod extends NumericalMethod {

    ImprovedEulerMethod() { super(); }

    ImprovedEulerMethod(LineChart<Number, Number> funcChart,
                        LineChart<Number, Number> errChart) {
        super(funcChart, errChart);
        methodSeries.setName("Im.Euler");
        localErrorSeries.setName("Im.Euler local error");
        globalErrorSeries.setName("Im.Euler global error");
    }

    @Override
    void calculateLocalError(double[] exactY) {
        for (int i = 0; i < N - 1; i++) {
            double m1 = f(x[i], exactY[i]);
            double m2 = f(x[i + 1], exactY[i] + h * m1);
            closestApprox[i + 1] = exactY[i] + h / 2 * (m1 + m2);
        }

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
            double m1 = f(x[i], y[i]);
            double m2 = f(x[i + 1], y[i] + h * m1);
            y[i + 1] = y[i] + h / 2 * (m1 + m2);
        }

        methodSeries.getData().clear();
        for (int i = 0; i < N; i++)
            methodSeries.getData().add(new Data<Number, Number>(x[i], y[i]));
        return true;
    }
}