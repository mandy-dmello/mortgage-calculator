package com.codewithmandy;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    private static final byte MONTHS_IN_YEAR = 12;
    private static final float PERCENT = 100;

    public static void main(String[] args) {
        int principal = (int) readInput("Principal ($1k - $1M): ", 1000, 1000000);
        float annualInterest = (float) readInput("Annual Interest Rate: ", 0, 30);
        byte years = (byte) readInput("Period (Years): ", 1, 30);

        printMortgage(principal, annualInterest, years);
        printPaymentSchedule(principal, annualInterest, years);
    }
    public static double readInput(String text, double min, double max) {
        double value;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            value = scanner.nextDouble();
            if (value > min && value <= max) {
                break;
            }
            System.out.println("Enter a value greater than " + min + " and less than or equal to " + max);
        }
        return value;
    }
    private static double calculateMortgage(
            int principal,
            float annualInterest,
            byte years) {
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        int numberOfPayments = years * MONTHS_IN_YEAR;
        // calculating interest  p * (r * (1+r)^n) / (1+r)^n - 1
        double mortgage = principal * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return mortgage;
    }
    private static void printMortgage(int principal, float annualInterest, byte years) {
        //converting into currency number format
        double mortgage = calculateMortgage(principal, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println("\nMORTGAGE\n-------- \nMonthly Payments: " + mortgageFormatted);
    }
    public static double calculateBalance(
            int principal,
            float annualInterest,
            byte years,
            short numberOfPaymentsMade) {
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = years * MONTHS_IN_YEAR;

        //formula for calculating remaining balance  B = L[(1 + c)n - (1 + c)p]/[(1 + c)n - 1]
        double balance = principal
                * (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return balance;
    }
    private static void printPaymentSchedule(int principal, float annualInterest, byte years) {
        System.out.println("\nPAYMENT SCHEDULE\n----------------");
        for (short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }
}