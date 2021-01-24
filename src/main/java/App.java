import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.DepartmentDao;
import dao.SqlDepartmentsDao;
import dao.SqlRolesDao;
import models.Departments;
import models.Roles;
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

    public static Boolean createRoleFormView = false;
    public static Boolean createdRole = false;
    public static Boolean editRoleFormView = false;
    public static Boolean saveEditRoleMsg =false;
    public static Boolean deleteRoleMsg = false;

    public static void main(String[] args) {


        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/technology";      //connect to todolist, not todolist_test!
        Sql2o sql2o = new Sql2o(connectionString, "admin", "admin");
        SqlDepartmentsDao sqlDepartmentDao = new SqlDepartmentsDao(sql2o);
        SqlRolesDao sqlRolesDao = new SqlRolesDao(sql2o);


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

        /**
         * END OF DEPARTMENTS
         */


        /**
         *ROLES
         */
        //roles home
        get("/roles", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List <Roles> roles = new SqlRolesDao(sql2o).getAll();
            model.put("roles", roles);
            createRoleFormView = true;
            model.put("createRoleFormView", createRoleFormView);
            //model.put("",);
            return new ModelAndView(model, "roles.hbs");
        }, new HandlebarsTemplateEngine());

        //add a role
        post("/roles/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Roles> roles = new SqlRolesDao(sql2o).getAll();
            model.put("roles", roles);
            createRoleFormView = true;
            model.put("createRoleFormView", createRoleFormView);
            String name = req.queryParams("roleName");
            Roles role = new Roles(name);
            sqlRolesDao.add(role);
            int addedID = role.getId();
            model.put("name",name);
            createdRole = true;
            model.put("createdRole",createdRole);
            return new ModelAndView(model, "roles.hbs");
        }, new HandlebarsTemplateEngine());


        get("/roles/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Roles> roles = new SqlRolesDao(sql2o).getAll();
            model.put("roles", roles);
            int roleId = Integer.parseInt(req.params("id"));
            Roles role = sqlRolesDao.findById(roleId);

            createRoleFormView =false;
            editRoleFormView = true;
            model.put("editRoleFormView",editRoleFormView);
            model.put("createRoleFormView",createRoleFormView);
            model.put("role",role);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "roles.hbs");
        }, new HandlebarsTemplateEngine());


        post("/roles/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Roles> roles = new SqlRolesDao(sql2o).getAll();
            model.put("roles", roles);

            int id = Integer.parseInt(req.queryParams("id"));
            String updatedName = req.queryParams("roleName");

            System.out.print("UPDATED NAME:"+updatedName);
            sqlRolesDao.update(id,updatedName);
            createRoleFormView =false;
            editRoleFormView = false;
            saveEditRoleMsg = true;
            model.put("editRoleFormView",editRoleFormView);
            model.put("createRoleFormView",createRoleFormView);
            model.put("saveEditRoleMsg",saveEditRoleMsg);
            model.put("updatedName",updatedName);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "roles.hbs");
        }, new HandlebarsTemplateEngine());


        get("/roles/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Roles> roles = new SqlRolesDao(sql2o).getAll();
            model.put("departments", roles);

            int roleId = Integer.parseInt(req.params("id"));
            sqlRolesDao.deleteById(roleId);

            createRoleFormView =false;
            editRoleFormView = false;
            saveEditRoleMsg = false;
            deleteRoleMsg =true;
            model.put("editRoleFormView",editRoleFormView);
            model.put("createRoleFormView",createRoleFormView);
            model.put("saveEditRoleMsg",saveEditRoleMsg);
            model.put("deleteRoleMsg",deleteRoleMsg);
            return new ModelAndView(model, "roles.hbs");
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
