import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.SqlDepartmentsDao;
import models.Departments;
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
        SqlDepartmentsDao sqlDepartmentDao = new SqlDepartmentsDao(sql2o);

        testConnection(sql2o);
        if(connectionStatus){
            System.out.print("Connected");
        }else{
            System.out.print("not connected!");
        }


        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //model.put("categories", allCategories);
            //model.put("tasks", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        /**
         * departments
         */
        //department home
        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());

        //add a department
        post("/departments/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            String name = req.queryParams("departmentName");

            Departments dept = new Departments(name);
            sqlDepartmentDao.add(dept);
            int addedID = dept.getId();
            model.put("name",addedID);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());

//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            //model.put("key", listValue);
//            //model.put("",);
//            return new ModelAndView(model, "");
//        }, new HandlebarsTemplateEngine());

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
