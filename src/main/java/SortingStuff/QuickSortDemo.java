package SortingStuff;

import java.util.Random;

public class QuickSortDemo {

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

        int size = 10000;

        System.out.println("ARRAY SIZE: " + size);

        testArray("Random Array", generateRandomArray(size));
        testArray("Already Sorted Array", generateSortedArray(size));
        testArray("Reverse Sorted Array", generateReverseSortedArray(size));
        testArray("Nearly Sorted Array", generateNearlySortedArray(size));
        testArray("Array With Duplicates", generateDuplicateArray(size));
    }

    private static void testArray(String name, int[] arr) {

        comparisons = 0;

        long startTime = System.nanoTime();

        quickSort(arr, 0, arr.length - 1);

        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        System.out.println(name);
        System.out.println("Execution time (ns): " + duration);
        System.out.println("Comparisons: " + comparisons);
        System.out.println();
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(100000);
        }

        return arr;
    }

    private static int[] generateSortedArray(int size) {
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }

        return arr;
    }

    private static int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }

        return arr;
    }

    private static int[] generateNearlySortedArray(int size) {
        int[] arr = generateSortedArray(size);
        Random rand = new Random();

        for (int i = 0; i < size / 100; i++) {
            int index1 = rand.nextInt(size);
            int index2 = rand.nextInt(size);
            swap(arr, index1, index2);
        }

        return arr;
    }

    private static int[] generateDuplicateArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10);
        }

        return arr;
    }

}