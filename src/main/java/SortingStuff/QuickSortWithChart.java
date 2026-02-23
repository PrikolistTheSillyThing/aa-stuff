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

public class QuickSortWithChart {

    public static long comparisons = 0;

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
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

            long start = System.nanoTime();
            quickSort(arr, 0, arr.length - 1);
            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000_000.0;
        }

        XYSeries series = new XYSeries("QuickSort Random Input");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "QuickSort Execution Time",
                "Array Size (n)",
                "Time (seconds)",
                dataset
        );

        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("0.00000"));

        JFrame frame = new JFrame("QuickSort Time Complexity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
