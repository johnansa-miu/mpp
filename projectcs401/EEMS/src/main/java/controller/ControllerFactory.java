package main.java.controller;

import main.java.service.ServiceFactory;

/**
 * Factory class for creating controller instances with proper dependency injection
 * Follows the Dependency Injection pattern and Factory pattern
 * Controllers receive service instances from ServiceFactory
 */
public class ControllerFactory {

    private static ClientController clientController;
    private static DepartmentController departmentController;
    private static EmployeeController employeeController;
    private static ProjectController projectController;
    private ControllerFactory() {}
    /**
     * Get ClientController instance
     * @return ClientController instance with injected service dependency
     */
    public static ClientController getClientController() {
        if (clientController == null) {
            clientController = new ClientController(ServiceFactory.getClientService());
        }
        return clientController;
    }
    
    /**
     * Get DepartmentController instance
     * @return DepartmentController instance with injected service dependency
     */
    public static DepartmentController getDepartmentController() {
        if (departmentController == null) {
            departmentController = new DepartmentController(ServiceFactory.getDepartmentService());
        }
        return departmentController;
    }
    
    /**
     * Get EmployeeController instance
     * @return EmployeeController instance with injected service dependency
     */
    public static EmployeeController getEmployeeController() {
        if (employeeController == null) {
            employeeController = new EmployeeController(ServiceFactory.getEmployeeService());
        }
        return employeeController;
    }
    
    /**
     * Get ProjectController instance
     * @return ProjectController instance with injected service dependency
     */
    public static ProjectController getProjectController() {
        if (projectController == null) {
            projectController = new ProjectController(ServiceFactory.getProjectService());
        }
        return projectController;
    }
}

