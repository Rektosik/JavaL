package ua.university;

import java.util.Arrays;

public class BasicOperators {

    public static double[] sumAndAverage(int a, int b, int c) {
        double sum = a + b + c;
        double avg = sum / 3.0;
        return new double[]{sum, avg};
    }

    public static int maxOfThree(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static char gradeFromScore(int score) {
        if (score < 0 || score > 100)
            throw new IllegalArgumentException("Score must be between 0 and 100");

        if (score >= 90) return 'A';
        if (score >= 80) return 'B';
        if (score >= 70) return 'C';
        if (score >= 60) return 'D';
        if (score >= 50) return 'E';
        return 'F';
    }

    public static String dayOfWeek(int day) {
        switch (day) {
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: throw new IllegalArgumentException("Day must be between 1 and 7");
        }
    }

    public static int[] countdown(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");
        int[] result = new int[n];
        for (int i = 0; i < n; i++)
            result[i] = n - i;
        return result;
    }

    public static long factorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n must be non-negative");
        long fact = 1;
        for (int i = 2; i <= n; i++)
            fact *= i;
        return fact;
    }

    public static int[] reverseArray(int[] arr) {
        if (arr == null)
            throw new IllegalArgumentException("Array cannot be null");
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            res[i] = arr[arr.length - 1 - i];
        return res;
    }

    public static int sumMatrix(int[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null");
        int sum = 0;
        for (int[] row : matrix) {
            if (row == null)
                throw new IllegalArgumentException("Matrix rows cannot be null");
            for (int val : row)
                sum += val;
        }
        return sum;
    }

    public static boolean isPalindrome(String s) {
        if (s == null)
            throw new IllegalArgumentException("String cannot be null");
        String clean = s.replaceAll("\\s+", "").toLowerCase();
        return clean.equals(new StringBuilder(clean).reverse().toString());
    }

    public static int[] findMinMax(int[] arr) {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException("Array cannot be null or empty");
        int min = arr[0], max = arr[0];
        for (int n : arr) {
            if (n < min) min = n;
            if (n > max) max = n;
        }
        return new int[]{min, max};
    }

    public static int[][] multiplicationTable(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");
        int[][] table = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                table[i][j] = (i + 1) * (j + 1);
        return table;
    }

    public static int[] evenNumbersUpToN(int n) {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        int count = n / 2;
        int[] result = new int[count];
        for (int i = 0; i < count; i++)
            result[i] = 2 * (i + 1);
        return result;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0)
                return false;
        return true;
    }

    public static int countVowels(String s) {
        if (s == null)
            throw new IllegalArgumentException("String cannot be null");
        int count = 0;
        for (char c : s.toLowerCase().toCharArray())
            if ("aeiou".indexOf(c) >= 0)
                count++;
        return count;
    }

    public static int[] fibonacci(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");
        int[] fib = new int[n];
        fib[0] = 0;
        if (n > 1) fib[1] = 1;
        for (int i = 2; i < n; i++)
            fib[i] = fib[i - 1] + fib[i - 2];
        return fib;
    }

    public static int[][] transpose(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            throw new IllegalArgumentException("Matrix cannot be null or empty");
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[j][i] = matrix[i][j];
        return result;
    }

    public static int[] sortArray(int[] arr) {
        if (arr == null)
            throw new IllegalArgumentException("Array cannot be null");
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        return sorted;
    }
}
