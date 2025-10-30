package Generics;

import java.util.*;

public class Employee {

    private String name;

    private List<String> contactNumbers;


    public Employee(String name, List<String> contactNumbers) {

        this.name = name;

        this.contactNumbers = contactNumbers;

    }

    public String getName() {

        return name;

    }

    public List<String> getContactNumbers() {

        return contactNumbers;

    }

}

class EmployeeContactSearch {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(

                new Employee("Alice", Arrays.asList("123-456-7890", "987-654-3210")),

                new Employee("Bob", Collections.emptyList()),

                new Employee("Charlie", Arrays.asList("555-555-5555"))

        );

        // Imperative Style

        Collections.sort(employees, new Comparator<Employee>() {

            @Override

            public int compare(Employee e1, Employee e2) {

                return e1.getName().compareTo(e2.getName());

            }

        });

        String contactNumber = null;

        for (Employee employee : employees) {

            List<String> contacts = employee.getContactNumbers();

            if (contacts != null && !contacts.isEmpty()) {

                contactNumber = contacts.get(0);

                break;

            }

        }

        if (contactNumber != null) {

            System.out.println("First available contact number: " + contactNumber);

        } else {

            System.out.println("No contact numbers found.");

        }

        // Write your answer (functional style) in the answer box

    }

    public static String getEmployeeFirstAvailableContactNumber(List<Employee> employees){

       return Optional.ofNullable(String.valueOf(employees.stream()
                .sorted(new Comparator<Employee>() {
                    @Override
                    public int compare(Employee e1, Employee e2) {

                        return e1.getName().compareTo(e2.getName());

                    }

                })
                .flatMap(x -> x.getContactNumbers().stream())
                .filter(x-> x != null && !x.isEmpty())
                .map(x-> "First available contact number: " + x)
                .findFirst())).orElse("No contact numbers found.");
    }

}