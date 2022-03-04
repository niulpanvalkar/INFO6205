package edu.neu.coe.info6205;

import edu.neu.coe.info6205.util.Utilities;
import java.util.Random;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.Arrays;

// import javax.swing.text.Utilities;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.TimeLogger;
import edu.neu.coe.info6205.sort.linearithmic.*;

public class CallBenchmark {

    private Config config;
    public static int size = 100;
    public CallBenchmark(Config config) {
        this.config = config;
    }

    public static void main(String[] args) throws IOException {
        Config config = Config.load(CallBenchmark.class);
        CallBenchmark callbenchmark = new CallBenchmark(config);
        // int n = 100;
        // final Supplier<int[]> intsSupplier = () -> {
        //     int[] result = (int[]) Array.newInstance(int.class, n);
        //     for (int i = 0; i < n; i++)
        //         result[i] = rd.nextInt(100000);
        //     return result;
        // };
        // for sorted array
        // final double t1 = new Benchmark_Timer<int[]>(
        //         "intArraysorter",
        //         (xs) -> Arrays.copyOf(xs, xs.length),
        //         Arrays::sort,
        //         null).runFromSupplier(intsSupplier, 100);

        // Utilities.formatDecimal3Places(t1);

        // Run benchmark for sorted array

        System.out.print("\nRun for reverse ordered array\n");
        for (int i = 0 ; i < 5 ; i++) {
            Helper<Integer> helper = new BaseHelper<>("Insertion Sort - Reverse ordered", size, config);
            final GenericSort<Integer> sort = new InsertionSort<>(helper);
            final Supplier<Integer[]> supplier = () -> createSortedArray(size, false);
            System.out.print("size : " + size);
            final double t1 = new Benchmark_Timer<>(
                "intArraysorter",
                (xs) -> Arrays.copyOf(xs, xs.length),
                sort::mutatingSort,
                null).runFromSupplier(supplier, 100);
                Utilities.formatDecimal3Places(t1);

            size = size * 2;
        }

        System.out.print("\nRun for ordered array\n");
        for (int i = 0 ; i < 5 ; i++) {
            Helper<Integer> helper = new BaseHelper<>("Insertion Sort - Ordered", size, config);
            final GenericSort<Integer> sort = new InsertionSort<>(helper);
            final Supplier<Integer[]> supplier = () -> createSortedArray(size, true);
            System.out.print("size : " + size);
            final double t1 = new Benchmark_Timer<>(
                "intArraysorter",
                (xs) -> Arrays.copyOf(xs, xs.length),
                sort::mutatingSort,
                null).runFromSupplier(supplier, 100);
                Utilities.formatDecimal3Places(t1);

            size = size * 2;
        }

        System.out.print("\nRun for partially ordered array\n");
        for (int i = 0 ; i < 5 ; i++) {
            Helper<Integer> helper = new BaseHelper<>("Insertion Sort - Partially ordered", size, config);
            final GenericSort<Integer> sort = new InsertionSort<>(helper);
            final Supplier<Integer[]> supplier = () -> createPartiallyOrderedArray(size);
            System.out.print("size : " + size);
            final double t1 = new Benchmark_Timer<>(
                "intArraysorter",
                (xs) -> Arrays.copyOf(xs, xs.length),
                sort::mutatingSort,
                null).runFromSupplier(supplier, 100);
                Utilities.formatDecimal3Places(t1);

            size = size * 2;
        }
        

        //  For random ordered array
        System.out.print("\nRun for random ordered array\n");
        for (int i = 0 ; i < 5 ; i++) {
            Helper<Integer> helper = new BaseHelper<>("Insertion Sort - Unordered", size, config);
            final GenericSort<Integer> sort = new InsertionSort<>(helper);
            final Supplier<Integer[]> supplier = () -> createUnsortedArray(size);
            System.out.print("size : " + size);
            final double t1 = new Benchmark_Timer<>(
                "intArraysorter",
                (xs) -> Arrays.copyOf(xs, xs.length),
                sort::mutatingSort,
                null).runFromSupplier(supplier, 100);
                Utilities.formatDecimal3Places(t1);

            size = size * 2;
        }
    }

    // Run benchmark for sorted array 


    // private void sortIntegers(int n) {
    //     final Random random = new Random();

    //     final Supplier<int[]> intsSupplier = () -> {
    //         int[] result = (int[]) Array.newInstance(int.class, n);
    //         for (int i = 0; i < n; i++)
    //             result[i] = random.nextInt(10000);
    //         return result;
    //     };

    //     final double t1 = new Benchmark_Timer<int[]>(
    //             "intArraysorter",
    //             (xs) -> Arrays.copyOf(xs, xs.length),
    //             Arrays::sort, // Array sorting function
    //             null).runFromSupplier(intsSupplier, 100);

    //     Utilities.formatDecimal3Places(t1);
    // }

    private static Integer[] createPartiallyOrderedArray(int size) {
        final Integer []orderedArr = new Integer[size];
        for(int i = 1; i<size/2; i++){
            Random random = new Random();
            orderedArr[i-1] = random.nextInt(size/2) + 1;
        }
        for(int i = size/2; i<=size; i++) {
            orderedArr[i - 1] = i;
        }
        return orderedArr;
    }

    // create an ordered and a reverse-ordered array
    private static Integer[] createSortedArray(int size, boolean sortOrder) {
        final Integer[] arr = new Integer[size];
            for (int i = 0; i < size; i++) {
                if(sortOrder == true) {
                    arr[i] = i + 1;
                } else {
                    arr[i] = size-i;
                }   
            }
            return arr;
    }

    private static Integer[] createUnsortedArray(int size) {
        Random random = new Random();
        final Integer[] resultArr= new Integer[size];
        for(int i = 0; i < size ; i++) {
            resultArr[i] = random.nextInt(10000);
        }
        return resultArr;
    }
}