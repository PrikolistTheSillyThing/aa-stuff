package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.text.DecimalFormat;

public class PowerMatrixMethod {
    public static long fibMatrix(int n) {
        if (n <= 1) return n;

        long[][] base = {{1,1},{1,0}};
        long[][] result = power(base, n - 1);
        return result[0][0];
    }

    static long[][] multiply(long[][] A, long[][] B) {
        return new long[][]{
                {A[0][0]*B[0][0] + A[0][1]*B[1][0],
                        A[0][0]*B[0][1] + A[0][1]*B[1][1]},
                {A[1][0]*B[0][0] + A[1][1]*B[1][0],
                        A[1][0]*B[0][1] + A[1][1]*B[1][1]}
        };
    }

    static long[][] power(long[][] M, int n) {
        if (n == 1) return M;
        if (n % 2 == 0) {
            long[][] half = power(M, n/2);
            return multiply(half, half);
        } else {
            return multiply(M, power(M, n-1));
        }
    }

    public static void main(String[] args) {

        int[] values = {
                501, 631, 794, 1000, 1259, 1585, 1995, 2512, 3162,
                3981, 5012, 6310, 7943, 10000, 12589, 15849
        };

        double[] times = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            long start = System.nanoTime();
            fibMatrix(values[i]);
            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000_000.0;
        }

        // Print table
        System.out.println("Fibonacci n:");
        for (int v : values) {
            System.out.print(v + "\t  ");
        }
        System.out.println();
        for (double t : times) {
            System.out.printf("%.5f\t", t);
        }


        XYSeries series = new XYSeries("Dynamic Programming Fibonacci");

        for (int i = 0; i < values.length; i++) {
            series.add(values[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Fibonacci Execution Time",
                "n",
                "Time (seconds)",
                dataset
        );

        JFrame frame = new JFrame("Time vs n");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("0.00000"));

    }

}

