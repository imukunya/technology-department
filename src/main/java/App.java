import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.*;
import models.*;
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

    public static Boolean createResponsibilityFormView = false;
    public static Boolean createdResponsibility = false;
    public static Boolean editResponsibilityFormView = false;
    public static Boolean saveEditResponsibilityMsg =false;
    public static Boolean deleteResponsibilityMsg = false;

    public static Boolean createStaffFormView = false;
    public static Boolean createdStaff = false;
    public static Boolean editStaffFormView = false;
    public static Boolean saveEditStaffMsg =false;
    public static Boolean deleteStaffMsg = false;

    public static Boolean activeLink =false;
    public static void main(String[] args) {


        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/technology";      //connect to todolist, not todolist_test!
        Sql2o sql2o = new Sql2o(connectionString, "admin", "admin");
        SqlDepartmentsDao sqlDepartmentDao = new SqlDepartmentsDao(sql2o);
        SqlRolesDao sqlRolesDao = new SqlRolesDao(sql2o);
        SqlResponsibilitiesDao sqlResponsibilitiesDao = new SqlResponsibilitiesDao(sql2o);
        SqlStaffDao sqlStaffDao = new SqlStaffDao(sql2o);
        SqlStaffRolesDao sqlStaffRolesDao = new SqlStaffRolesDao(sql2o);
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
            departments = new SqlDepartmentsDao(sql2o).getAll();
            model.put("departments", departments);
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
            roles = new SqlRolesDao(sql2o).getAll();
            model.put("roles", roles);
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

        /**
         * END OF ROLES
         */


        /**
         * responsibilities
         */
        //roles home
        get("/responsibilities", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List <Responsibilities> responsibilities = new SqlResponsibilitiesDao(sql2o).getAll();
            model.put("responsibilities", responsibilities);
            createResponsibilityFormView = true;
            model.put("createResponsibilityFormView", createResponsibilityFormView);
            //model.put("",);
            return new ModelAndView(model, "responsibilities.hbs");
        }, new HandlebarsTemplateEngine());

        //add a role
        post("/responsibilities/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Responsibilities> responsibilities = new SqlResponsibilitiesDao(sql2o).getAll();
            model.put("responsibilities", responsibilities);
            createResponsibilityFormView = true;
            model.put("createResponsibilityFormView", createResponsibilityFormView);
            String name = req.queryParams("responsibilityName");
            Responsibilities responsibility = new Responsibilities(name);
            sqlResponsibilitiesDao.add(responsibility);
            int addedID = responsibility.getId();
            model.put("name",name);
            createdResponsibility = true;
            model.put("createdResponsibility",createdResponsibility);
            responsibilities = new SqlResponsibilitiesDao(sql2o).getAll();
            model.put("responsibilities", responsibilities);
            return new ModelAndView(model, "responsibilities.hbs");
        }, new HandlebarsTemplateEngine());


        get("/responsibilities/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Responsibilities> responsibilities = new SqlResponsibilitiesDao(sql2o).getAll();
            model.put("responsibilities", responsibilities);
            int responsibilityId = Integer.parseInt(req.params("id"));
            Responsibilities responsibility = sqlResponsibilitiesDao.findById(responsibilityId);

            createResponsibilityFormView =false;
            editResponsibilityFormView = true;
            model.put("editResponsibilityFormView",editResponsibilityFormView);
            model.put("createResponsibilityFormView",createResponsibilityFormView);
            model.put("responsibility",responsibility);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "responsibilities.hbs");
        }, new HandlebarsTemplateEngine());


        post("/responsibilities/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Responsibilities> responsibilities = new SqlResponsibilitiesDao(sql2o).getAll();
            model.put("responsibilities", responsibilities);

            int id = Integer.parseInt(req.queryParams("id"));
            String updatedName = req.queryParams("responsibilityName");

            System.out.print("UPDATED NAME:"+updatedName);
            sqlResponsibilitiesDao.update(id,updatedName);
            createResponsibilityFormView =false;
            editResponsibilityFormView = false;
            saveEditResponsibilityMsg = true;
            model.put("editResponsibilityFormView",editResponsibilityFormView);
            model.put("createResponsibilityFormView",createResponsibilityFormView);
            model.put("saveEditResponsibilityMsg",saveEditResponsibilityMsg);
            model.put("updatedName",updatedName);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "responsibilities.hbs");
        }, new HandlebarsTemplateEngine());


        get("/responsibilities/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Responsibilities> responsibilities = new SqlResponsibilitiesDao(sql2o).getAll();
            model.put("responsibilities", responsibilities);

            int roleId = Integer.parseInt(req.params("id"));
            sqlResponsibilitiesDao.deleteById(roleId);

            createResponsibilityFormView =false;
            editResponsibilityFormView = false;
            saveEditResponsibilityMsg = false;
            deleteResponsibilityMsg =true;
            model.put("createResponsibilityFormView",createResponsibilityFormView);
            model.put("editResponsibilityFormView",editResponsibilityFormView);
            model.put("saveEditResponsibilityMsg",saveEditResponsibilityMsg);
            model.put("deleteResponsibilityMsg",deleteResponsibilityMsg);
            return new ModelAndView(model, "responsibilities.hbs");
        }, new HandlebarsTemplateEngine());

        //staff
        /**
         * STAFF
         */

        get("/staff", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Staff> staff = new SqlStaffDao(sql2o).getAll();
            model.put("staff", staff);
            List <Responsibilities> respList = new SqlResponsibilitiesDao(sql2o).getAll();
            List <Roles> roleList = new SqlRolesDao(sql2o).getAll();
            model.put("respList",respList);
            model.put("roleList",roleList);
            createStaffFormView = true;
            model.put("createStaffFormView", createStaffFormView);
            activeLink =true;
            model.put("activeLink",activeLink);
            return new ModelAndView(model, "staff.hbs");
        }, new HandlebarsTemplateEngine());

        //add a role
        post("/staff/add", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Staff> staff = new SqlStaffDao(sql2o).getAll();
            model.put("staff", staff);
            List <Responsibilities> respList = new SqlResponsibilitiesDao(sql2o).getAll();
            List <Roles> roleList = new SqlRolesDao(sql2o).getAll();
            model.put("respList",respList);
            model.put("roleList",roleList);
            createStaffFormView = true;
            model.put("createStaffFormView", createStaffFormView);
            String name = req.queryParams("staffName");

            Staff staffer = new Staff(name);
            sqlStaffDao.add(staffer);
            int addedID = staffer.getId();

            //INSERT ROLES
            //get the submitted role
            int selectedRole = Integer.parseInt(req.queryParams("chosenRole"));
            System.out.print("++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.print(addedID);
            System.out.print(selectedRole);
            //add role
            StaffRoles staffRole = new StaffRoles(addedID,selectedRole);
            sqlStaffRolesDao.add(staffRole);

            //INSERT RESPONSIBILITIES
            //

            model.put("name",name);
            createdStaff = true;
            model.put("createdStaff",createdStaff);

            staff = new SqlStaffDao(sql2o).getAll();
            model.put("staff",staff);
            return new ModelAndView(model, "staff.hbs");
        }, new HandlebarsTemplateEngine());


        get("/staff/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Staff> staff = new SqlStaffDao(sql2o).getAll();
            model.put("staff", staff);
            int staffId = Integer.parseInt(req.params("id"));
            Staff staffer = sqlStaffDao.findById(staffId);

            createStaffFormView =false;
            editStaffFormView = true;
            model.put("createStaffFormView",createStaffFormView);
            model.put("editStaffFormView",editStaffFormView);
            model.put("staff",staffer);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "staff.hbs");
        }, new HandlebarsTemplateEngine());


        post("/staff/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List <Staff> staff = new SqlStaffDao(sql2o).getAll();
            model.put("staff", staff);

            int id = Integer.parseInt(req.queryParams("id"));
            String updatedName = req.queryParams("staffName");

            System.out.print("UPDATED NAME:"+updatedName);
            sqlStaffDao.update(id,updatedName);
            createStaffFormView =false;
            editStaffFormView = false;
            saveEditStaffMsg = true;
            model.put("editStaffFormView",editStaffFormView);
            model.put("createStaffFormView",createStaffFormView);
            model.put("saveEditRoleMsg",saveEditStaffMsg);
            model.put("updatedName",updatedName);
            //model.put("key", listValue);
            //model.put("",);
            return new ModelAndView(model, "staff.hbs");
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
