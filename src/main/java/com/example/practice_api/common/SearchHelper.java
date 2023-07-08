package com.example.practice_api.common;

public class SearchHelper {
    public static long calculateNumOfPage(Long numOfItems, Integer pageSize) {
        long num = numOfItems / pageSize.longValue();
        return num % 2 != 0 ? (long) Math.ceil(num) : num;
    }
}
