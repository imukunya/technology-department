import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DepartmentDao;
import dao.SqlDepartmentsDao;
import models.Departments;
import org.sql2o.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static Boolean connectionStatus = false;

    public static Boolean createDepartmentFormView = false;
    public static Boolean createdDept = false;
    public static Boolean editDepartmentFormView = false;
    public static Boolean saveEditDepartmentMsg =false;
    public static Boolean deleteDepartmentMsg = false;

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
            createDepartmentFormView = true;
            model.put("createDepartmentFormView = true;", createDepartmentFormView);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        /**
         * departments
         */
        //department home
        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List <Departments> departments = new SqlDepartmentsDao(sql2o).getAll();
            model.put("departments", departments);
            createDepartmentFormView = true;
            model.put("createDepartmentFormView", createDepartmentFormView);
            //model.put("",);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());

        //add a department
        post("/departments/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Departments> departments = new SqlDepartmentsDao(sql2o).getAll();
            model.put("departments", departments);
            createDepartmentFormView = true;
            model.put("createDepartmentFormView", createDepartmentFormView);
            String name = req.queryParams("departmentName");
            Departments dept = new Departments(name);
            sqlDepartmentDao.add(dept);
            int addedID = dept.getId();
            model.put("name",name);
            createdDept = true;
            model.put("createdDept",createdDept);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());


        get("/departments/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Departments> departments = new SqlDepartmentsDao(sql2o).getAll();
            model.put("departments", departments);
            int deptId = Integer.parseInt(req.params("id"));
            Departments department = sqlDepartmentDao.findById(deptId);

            createDepartmentFormView =false;
            editDepartmentFormView = true;
            model.put("editDepartmentFormView",editDepartmentFormView);
            model.put("createDepartmentFormView",createDepartmentFormView);
            model.put("department",department);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());


        post("/departments/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Departments> departments = new SqlDepartmentsDao(sql2o).getAll();
            model.put("departments", departments);

            int id = Integer.parseInt(req.queryParams("id"));
            String updatedName = req.queryParams("departmentName");

            System.out.print("UPDATED NAME:"+updatedName);
            sqlDepartmentDao.update(id,updatedName);
            createDepartmentFormView =false;
            editDepartmentFormView = false;
            saveEditDepartmentMsg = true;
            model.put("editDepartmentFormView",editDepartmentFormView);
            model.put("createDepartmentFormView",createDepartmentFormView);
            model.put("saveEditDepartmentMsg",saveEditDepartmentMsg);
            model.put("updatedName",updatedName);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "departments.hbs");
        }, new HandlebarsTemplateEngine());


        get("/departments/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Departments> departments = new SqlDepartmentsDao(sql2o).getAll();
            model.put("departments", departments);

            int deptId = Integer.parseInt(req.params("id"));
            sqlDepartmentDao.deleteById(deptId);

            createDepartmentFormView =false;
            editDepartmentFormView = false;
            saveEditDepartmentMsg = false;
            deleteDepartmentMsg =true;
            model.put("editDepartmentFormView",editDepartmentFormView);
            model.put("createDepartmentFormView",createDepartmentFormView);
            model.put("saveEditDepartmentMsg",saveEditDepartmentMsg);
            model.put("deleteDepartmentMsg",deleteDepartmentMsg);
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
