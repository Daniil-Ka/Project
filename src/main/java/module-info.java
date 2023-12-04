module org.basicprogramming {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.web;
    requires javafx.swing;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.jfree.jfreechart;
    requires commons.math3;
    requires sdk;

    exports org.basicprogramming;
    opens org.basicprogramming to javafx.fxml;
    opens org.basicprogramming.db.models;
    opens org.basicprogramming.db;
    opens org.basicprogramming.loaders;
}