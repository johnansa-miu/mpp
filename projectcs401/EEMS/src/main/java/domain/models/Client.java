package main.java.domain.models;


import java.util.List;

public class Client extends Entity {
    private String name;
    private String industry;
    private String contactPerson;
    private String phone;
    private String email;

    private List<ClientProject> projectClients;

    public Client(int clientId, String name, String industry,
                  String contactPerson, String phone, String email) {
        super(clientId);
        this.name = name;
        this.industry = industry;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<ClientProject> getProjectClients() { return projectClients; }
    public void setProjectClients(List<ClientProject> projectClients) { this.projectClients = projectClients; }

    @Override
    public String toString() {
        return "Client [ID=" + getId() + ", Name=" + name + ", Industry=" + industry +
                ", Contact=" + contactPerson + ", Phone=" + phone + ", Email=" + email +
                ", Projects=" + (projectClients != null ? projectClients.size() : 0) + "]";
    }
}

