import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static Boolean connectionStatus = false;
    public static void main(String[] args) {


        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/technology";      //connect to todolist, not todolist_test!
        Sql2o sql2o = new Sql2o(connectionString, "admin", "admin");
        testConnection(sql2o);
        if(connectionStatus){
            System.out.print("Connected");
        }else{
            System.out.print("not connected!");
        }
    }

    public static Boolean testConnection(Sql2o sqlConn){

        try(Connection conn =  sqlConn.open()){
            System.out.print("Connected to the data base");
            connectionStatus = true;
        }catch (Sql2oException e){
            System.out.print("error connecting to the data base");
            connectionStatus = false;
        }
        return connectionStatus;
    }
}
