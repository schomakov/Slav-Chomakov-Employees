package employees;

import exceptions.IncorrectDateFormatException;
import exceptions.WrongOrderOfDatesException;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Employee {

    private int id;

    private Map<Integer, Project> projects;

    public Employee(int id, int projectId, String dateFrom, String dateTo) throws IllegalArgumentException, ParseException {
        this.setId(id);
        this.projects = new HashMap<Integer, Project>();
        this.addProject(projectId, dateFrom, dateTo);
    }

    private void setId(int id) {
        if(Company.checkIfIsNotPositive(id)) {
            throw new IllegalArgumentException("ID of an Employee must be positive! '" + id + "'");
        }

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Project> getProjects() {
        return Collections.unmodifiableMap(this.projects);
    }

    public void addProject(int projectId, String dateFrom, String dateTo) throws ParseException,IllegalArgumentException {

        if(Company.checkIfIsNotPositive(projectId)) {
            throw new IllegalArgumentException("Can't add project with non-positive ID! '" + projectId + "'");
        }

        TimePeriod interval;
        try {
            interval = new TimePeriod(dateFrom, dateTo);
        } catch (WrongOrderOfDatesException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IncorrectDateFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if(this.projects.get(projectId) != null) {
            try {
                this.projects.get(projectId).addNewPeriod(interval);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException(e.getMessage());
            }

            return;
        }

        this.projects.put(projectId, new Project(projectId, interval));
    }
}
