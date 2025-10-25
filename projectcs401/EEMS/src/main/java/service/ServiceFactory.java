package main.java.service;

import main.java.repository.*;

/**
 * Factory class for creating service instances with proper dependency injection
 * Follows the Dependency Injection pattern and Factory pattern
 */
public class ServiceFactory {

    private static final ClientRepository clientRepository = new ClientRepository();
    private static final DepartmentRepository departmentRepository = new DepartmentRepository();
    private static final EmployeeRepository employeeRepository = new EmployeeRepository();
    private static final ProjectRepository projectRepository = new ProjectRepository();
    private static final ClientProjectRepository clientProjectRepository = new ClientProjectRepository();
    private static final DepartmentProjectRepository departmentProjectRepository = new DepartmentProjectRepository();
    private static final EmployeeProjectRepository employeeProjectRepository = new EmployeeProjectRepository();
    
    // Service instances (singleton pattern)
    private static ClientService clientService;
    private static DepartmentService departmentService;
    private static EmployeeService employeeService;
    private static ProjectService projectService;

    private ServiceFactory() {}
    
    /**
     * Get ClientService instance
     * @return ClientService instance with injected dependencies
     */
    public static ClientService getClientService() {
        if (clientService == null) {
            clientService = new ClientService(
                    clientRepository, 
                    clientProjectRepository, 
                    projectRepository
            );
        }
        return clientService;
    }
    
    /**
     * Get DepartmentService instance
     * @return DepartmentService instance with injected dependencies
     */
    public static DepartmentService getDepartmentService() {
        if (departmentService == null) {
            departmentService = new DepartmentService(
                    departmentRepository, 
                    employeeRepository,
                    projectRepository,
                    departmentProjectRepository
            );
        }
        return departmentService;
    }
    
    /**
     * Get EmployeeService instance
     * @return EmployeeService instance with injected dependencies
     */
    public static EmployeeService getEmployeeService() {
        if (employeeService == null) {
            employeeService = new EmployeeService(
                    employeeRepository, 
                    departmentRepository
            );
        }
        return employeeService;
    }
    
    /**
     * Get ProjectService instance
     * @return ProjectService instance with injected dependencies
     */
    public static ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectService(
                    projectRepository,
                    employeeRepository,
                    employeeProjectRepository
            );
        }
        return projectService;
    }
}

