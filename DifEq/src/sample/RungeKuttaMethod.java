package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;

public class RungeKuttaMethod extends NumericalMethod {

    RungeKuttaMethod() { super(); }

    RungeKuttaMethod(LineChart<Number, Number> funcChart,
                     LineChart<Number, Number> errChart) {
        super(funcChart, errChart);
        methodSeries.setName("R-Kutta");
        localErrorSeries.setName("R-Kutta local error");
        globalErrorSeries.setName("R-Kutta global error");
    }

    @Override
    void calculateLocalError(double[] exactY) {
        this.exactY = exactY;

        for (int i = 0; i < N - 1; i++) {
            double k1 = f(x[i], exactY[i]);
            double k2 = f(x[i] + h/2, exactY[i] + h * k1/2);
            double k3 = f(x[i] + h/2, exactY[i] + h * k2/2);
            double k4 = f(x[i] + h, exactY[i] + h * k3);

            closestApprox[i + 1] = exactY[i] + h/6 * (k1 + 2 * k2 + 2 * k3 + k4);
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
            double k1 = f(x[i], y[i]);
            double k2 = f(x[i] + h/2, y[i] + h * k1/2);
            double k3 = f(x[i] + h/2, y[i] + h * k2/2);
            double k4 = f(x[i] + h, y[i] + h * k3);

            y[i + 1] = y[i] + h/6 * (k1 + 2 * k2 + 2 * k3 + k4);
           }

        methodSeries.getData().clear();
        for (int i = 0; i < N; i++)
            methodSeries.getData().add(new Data<Number, Number>(x[i], y[i]));
        return true;
    }
}