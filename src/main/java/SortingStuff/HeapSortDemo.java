package SortingStuff;

import java.util.Random;

public class HeapSortDemo {

    public static long comparisons = 0;

    public static void heapSort(int[] arr) {

        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {

            swap(arr, 0, i);

            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            comparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < n) {
            comparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
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

        heapSort(arr);

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