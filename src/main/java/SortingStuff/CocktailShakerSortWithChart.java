package SortingStuff;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Random;

public class CocktailShakerSortWithChart {

    public static long comparisons = 0;

    public static void cocktailShakerSort(int[] arr) {

        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;

        while (swapped) {

            swapped = false;

            for (int i = start; i < end; i++) {
                comparisons++;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped)
                break;

            swapped = false;
            end--;

            for (int i = end; i > start; i--) {
                comparisons++;
                if (arr[i] < arr[i - 1]) {
                    swap(arr, i, i - 1);
                    swapped = true;
                }
            }

            start++;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        int[] sizes = {1000, 2000, 4000, 8000, 12000, 16000, 20000};
        double[] times = new double[sizes.length];

        Random rand = new Random();

        for (int i = 0; i < sizes.length; i++) {

            int[] arr = new int[sizes[i]];

            for (int j = 0; j < sizes[i]; j++) {
                arr[j] = rand.nextInt(100000);
            }

            comparisons = 0;

            long start = System.nanoTime();
            cocktailShakerSort(arr);
            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000_000.0;
        }

        XYSeries series = new XYSeries("Cocktail Shaker Sort Random Input");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Cocktail Shaker Sort Execution Time",
                "Array Size (n)",
                "Time (seconds)",
                dataset
        );

        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("0.00000"));

        JFrame frame = new JFrame("Cocktail Shaker Sort Time Complexity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}