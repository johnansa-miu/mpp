package main.java.controller;

import main.java.domain.models.Client;
import main.java.domain.models.Project;
import main.java.service.ClientService;
import main.java.service.ServiceException;

import java.util.List;

/**
 * Controller for Client operations
 * Delegates all business logic to ClientService
 * Responsible only for receiving requests and returning responses
 */
public class ClientController extends BaseController<Client, Integer> {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        super(clientService);
        this.clientService = clientService;
    }

    /**
     * Find clients with projects scheduled to end within specified days
     * Task 3 implementation
     * 
     * @param daysUntilDeadline number of days from current date
     * @return list of clients with upcoming project deadlines
     * @throws ServiceException if operation fails
     */
    public List<Client> findClientsByUpcomingProjectDeadline(int daysUntilDeadline) {
        return clientService.findClientsByUpcomingProjectDeadline(daysUntilDeadline);
    }

    /**
     * Find clients by industry
     * @param industry the industry name
     * @return list of clients in the specified industry
     * @throws ServiceException if operation fails
     */
    public List<Client> findByIndustry(String industry) {
        return clientService.findByIndustry(industry);
    }

    /**
     * Get all projects for a specific client
     * @param clientId the client ID
     * @return list of projects for the client
     * @throws ServiceException if client not found or operation fails
     */
    public List<Project> getProjectsForClient(int clientId) {
        return clientService.getProjectsForClient(clientId);
    }

    /**
     * Calculate total project budget for a client
     * @param clientId the client ID
     * @return total budget of all projects for the client
     * @throws ServiceException if operation fails
     */
    public double calculateClientTotalProjectBudget(int clientId) {
        return clientService.calculateClientTotalProjectBudget(clientId);
    }

    /**
     * Find high-value clients (clients with total project budget above threshold)
     * @param budgetThreshold the minimum total project budget
     * @return list of high-value clients
     * @throws ServiceException if operation fails
     */
    public List<Client> findHighValueClients(double budgetThreshold) {
        return clientService.findHighValueClients(budgetThreshold);
    }
}

