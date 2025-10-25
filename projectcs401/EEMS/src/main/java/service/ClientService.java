package main.java.service;

import main.java.domain.models.Client;
import main.java.domain.models.ClientProject;
import main.java.domain.models.Project;
import main.java.repository.ClientRepository;
import main.java.repository.ClientProjectRepository;
import main.java.repository.ProjectRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service layer for Client operations
 * Implements business logic for client management
 */
public class ClientService extends BaseService<Client, Integer> {
    private final ClientRepository clientRepository;
    private final ClientProjectRepository clientProjectRepository;
    private final ProjectRepository projectRepository;

    public ClientService(
            ClientRepository clientRepository,
            ClientProjectRepository clientProjectRepository,
            ProjectRepository projectRepository) {
        super(clientRepository);
        this.clientRepository = clientRepository;
        this.clientProjectRepository = clientProjectRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Task 3: Find clients with projects scheduled to end within specified days
     * 
     * @param daysUntilDeadline number of days from current date
     * @return list of clients with upcoming project deadlines
     */
    public List<Client> findClientsByUpcomingProjectDeadline(int daysUntilDeadline) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(daysUntilDeadline);

        // Get all projects ending within the deadline
        Set<Integer> projectIdsEndingSoon = projectRepository.findAll().stream()
                .filter(p -> p.getEndDate() != null)
                .filter(p -> !p.getEndDate().isBefore(currentDate))
                .filter(p -> !p.getEndDate().isAfter(targetDate))
                .map(Project::getId)
                .collect(Collectors.toSet());

        // Get client IDs for those projects
        Set<Integer> clientIdsWithUpcomingDeadlines = clientProjectRepository.findAll().stream()
                .filter(cp -> projectIdsEndingSoon.contains(cp.getProjectId()))
                .map(ClientProject::getClientId)
                .collect(Collectors.toSet());

        // Return the actual Client objects
        return clientRepository.findAll().stream()
                .filter(c -> clientIdsWithUpcomingDeadlines.contains(c.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Find clients by industry
     * @param industry the industry name
     * @return list of clients in the specified industry
     */
    public List<Client> findByIndustry(String industry) {
        return clientRepository.findAll().stream()
                .filter(c -> c.getIndustry() != null)
                .filter(c -> c.getIndustry().equalsIgnoreCase(industry))
                .collect(Collectors.toList());
    }

    /**
     * Get all projects for a specific client
     * @param clientId the client ID
     * @return list of projects for the client
     */
    public List<Project> getProjectsForClient(int clientId) {
        // Validate client exists
        getByIdOrThrow(clientId);

        // Get project IDs for this client
        Set<Integer> projectIds = clientProjectRepository.findAll().stream()
                .filter(cp -> cp.getClientId() == clientId)
                .map(ClientProject::getProjectId)
                .collect(Collectors.toSet());

        // Return the projects
        return projectRepository.findAll().stream()
                .filter(p -> projectIds.contains(p.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Calculate total project budget for a client
     * @param clientId the client ID
     * @return total budget of all projects for the client
     */
    public double calculateClientTotalProjectBudget(int clientId) {
        return getProjectsForClient(clientId).stream()
                .mapToDouble(Project::getBudget)
                .sum();
    }

    /**
     * Find high-value clients (clients with total project budget above threshold)
     * @param budgetThreshold the minimum total project budget
     * @return list of high-value clients
     */
    public List<Client> findHighValueClients(double budgetThreshold) {
        return clientRepository.findAll().stream()
                .filter(c -> calculateClientTotalProjectBudget(c.getId()) >= budgetThreshold)
                .collect(Collectors.toList());
    }
}

