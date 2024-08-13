package com.siripireddy.tdd;

public class FizzBuzz {
//    public static String compute(int n) {
//
//        if(n%3==0&&n%5==0) return "FizzBuzz";
//        else if(n%3==0) return "Fizz";
//        else if(n%5==0) return "Buzz";
//
//        return Integer.toString(n);
//    }
    public static String compute(int n){
        StringBuilder str=new StringBuilder();
        if(n%3==0)
            str.append("Fizz");
        if(n%5==0)
            str.append("Buzz");
        if(str.length()==0)
            str.append(n);
        return str.toString();
    }
}
