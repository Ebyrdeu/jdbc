package dev.ebyrdeu;

import dev.ebyrdeu.ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        Menu.printMenu(scanner);
    }
}