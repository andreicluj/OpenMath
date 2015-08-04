/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andrei.openmath;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Trinitas
 */
public class C1 {

    private static Map<Integer, Long> memo = new HashMap<>();

    static {
        memo.put(0, 0L); //fibonacci(0)
        memo.put(1, 1L); //fibonacci(1)
    }

    public static long fibonacci(int x) {
        return memo.computeIfAbsent(x, n -> fibonacci(n - 1) + fibonacci(n - 2));
    }

    public static int f(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return f(n - 1) + f(n - 2);
        }

    }

    public static void main(String args[]) {
        int n  = 55;
        long start  = System.currentTimeMillis();
        long j = fibonacci(n);
        long end  = System.currentTimeMillis();
        long elapsedTime  = end - start;
        System.out.println("Fibonacci 1 " + + elapsedTime);
         start  = System.currentTimeMillis();
        int i  = f(n);
        end  = System.currentTimeMillis();
        elapsedTime  = end - start;
        System.out.println("Fibonacci 2 " + + elapsedTime);
        System.out.println("F1: " + j + "  F2: " + j);

    }
}
