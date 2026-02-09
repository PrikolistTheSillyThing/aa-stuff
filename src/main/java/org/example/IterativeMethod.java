package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.text.DecimalFormat;

public class IterativeMethod {
    public static long fib(int n) {
        if (n <= 1)
            return n;

        long prev2 = 0;
        long prev1 = 1;
        long curr = 0;

        for (int i = 2; i <= n; i++) {
            curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return curr;
    }

    public static void main(String[] args) {

        int[] values = {
                501, 631, 794, 1000, 1259, 1585, 1995, 2512, 3162,
                3981, 5012, 6310, 7943, 10000, 12589, 15849
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
