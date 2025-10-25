package main.java.domain.models;

public class ClientProject {
    private int projectId;
    private int clientId;

    private Project project;
    private Client client;

    public ClientProject(int projectId, int clientId) {
        this.projectId = projectId;
        this.clientId = clientId;
    }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    @Override
    public String toString() {
        return "ProjectClient [ProjectID=" + projectId +
                ", ClientID=" + clientId + "]";
    }
}

