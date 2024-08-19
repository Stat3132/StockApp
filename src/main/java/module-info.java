module csc180.perez.diego.stockappjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpcore;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    opens csc180.perez.diego.stockappjavafx to javafx.fxml;
    exports csc180.perez.diego.stockappjavafx.Controller;//test
    exports csc180.perez.diego.stockappjavafx;
    exports csc180.perez.diego.stockappjavafx.UTIL;
    opens csc180.perez.diego.stockappjavafx.Controller to javafx.fxml;
}