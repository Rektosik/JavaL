package ua.university;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Project works");
        
        System.out.println("Sum and average: " + Arrays.toString(BasicOperators.sumAndAverage(3, 6, 9)));
        System.out.println("Max of three: " + BasicOperators.maxOfThree(5, 9, 7));
        System.out.println("Grade from 85: " + BasicOperators.gradeFromScore(85));
        System.out.println("Day 4: " + BasicOperators.dayOfWeek(4));
        System.out.println("Countdown: " + Arrays.toString(BasicOperators.countdown(5)));
        System.out.println("Factorial(5): " + BasicOperators.factorial(5));
        System.out.println("Is palindrome (Level): " + BasicOperators.isPalindrome("Level"));
        System.out.println("Fibonacci(7): " + Arrays.toString(BasicOperators.fibonacci(7)));
        System.out.println("Even up to 10: " + Arrays.toString(BasicOperators.evenNumbersUpToN(10)));
    }
}
