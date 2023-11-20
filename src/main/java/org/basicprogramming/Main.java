package org.basicprogramming;
import org.basicprogramming.db.DB;
import org.basicprogramming.loaders.XLSLLoader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(1);
        var fileLocation = "H:\\Download\\basicprogramming.xlsx";

        var loader = new XLSLLoader(fileLocation);
        DB.init();
        loader.load();
    }
}
