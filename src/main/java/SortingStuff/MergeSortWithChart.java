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

public class MergeSortWithChart {

    public static long comparisons = 0;

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {

            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            comparisons++;

            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
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
            mergeSort(arr, 0, arr.length - 1);
            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000_000.0;
        }

        XYSeries series = new XYSeries("MergeSort Random Input");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "MergeSort Execution Time",
                "Array Size (n)",
                "Time (seconds)",
                dataset
        );

        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("0.00000"));

        JFrame frame = new JFrame("MergeSort Time Complexity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}