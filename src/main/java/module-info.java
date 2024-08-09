module csc180.perez.diego.stockappjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpcore;
    requires java.sql;


    opens csc180.perez.diego.stockappjavafx to javafx.fxml;
    exports csc180.perez.diego.stockappjavafx;//test
}