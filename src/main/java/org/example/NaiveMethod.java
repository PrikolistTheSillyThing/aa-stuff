package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

public class NaiveMethod {
    static long fib(int n) {
        if (n <= 1)
            return n;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {

        int[] values = {
                5, 7, 10, 12, 15, 17, 20, 22, 25,
                27, 30, 32, 35, 37, 40, 42, 45,
        };

        double[] times = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            long start = System.nanoTime();
            fib(values[i]);
            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000_000.0;
        }

        // Print table
        System.out.println("Fibonacci n:");
        for (int v : values) {
            System.out.print(v + "\t\t");
        }
        System.out.println();
        for (double t : times) {
            System.out.printf("%.2f\t", t);
        }

        XYSeries series = new XYSeries("Naive Fibonacci");

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

    }

}
