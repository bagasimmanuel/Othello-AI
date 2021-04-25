package com.belajar;

import sun.security.util.ArrayUtil;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan size yang diinginkan");
        Othello.boardSize = sc.nextInt();

        Othello othello = new Othello(Othello.boardSize);

        othello.play();


    }
}
