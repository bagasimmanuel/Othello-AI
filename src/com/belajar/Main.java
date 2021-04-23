package com.belajar;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan size yang diinginkan");
        int size = sc.nextInt();

        Othello othello = new Othello(size);

        othello.printBoard(Othello.WHITE);
    }
}
