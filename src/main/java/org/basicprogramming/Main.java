package org.basicprogramming;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(1);
        var fileLocation = "H:\\Download\\basicprogramming.xlsx";

        var loader = new XLSLLoader(fileLocation);
        loader.load();
    }
}