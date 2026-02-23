package SortingStuff;

import java.util.Random;

public class MergeSortDemo {

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

        mergeSort(arr, 0, arr.length - 1);

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

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}