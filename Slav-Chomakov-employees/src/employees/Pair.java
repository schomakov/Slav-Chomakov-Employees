package employees;

public class Pair {

    private int firstEmployee;

    private int secondEmployee;

    private int workedTimeTogether;

    public Pair(int idFirstEmployee, int idSecondEmployee, int workedTime) {
        this.setFirstEmployee(idFirstEmployee);
        this.setSecondEmployee(idSecondEmployee);
        this.setWorkedTimeTogether(workedTime);
    }

    public void increaseTime(int workedTime) {
        if(Company.checkIfIsNotPositive(workedTime)) {
            throw new IllegalArgumentException("Worked time spent from a team can't be increased with non-positive value! '" + workedTime + "'");
        }

        this.workedTimeTogether+= workedTime;
    }

    private void setFirstEmployee(int id) {
        if(Company.checkIfIsNotPositive(id)) {
            throw new IllegalArgumentException("Employee must have positive value as an ID! '" + id + "'");
        }

        this.firstEmployee = id;
    }

    private void setSecondEmployee(int id) {
        if(Company.checkIfIsNotPositive(id)) {
            throw new IllegalArgumentException("Employee must have positive value as an ID! '" + id + "'");
        }

        this.secondEmployee = id;
    }

    private void setWorkedTimeTogether(int workedTime) {
        if(Company.checkIfIsNotPositive(workedTime)) {
            throw new IllegalArgumentException("Worked time together can't' be non-positive value! '" + workedTime + "'");
        }

        this.workedTimeTogether = workedTime;
    }

    public long getWorkedTimeTogether() {
        return workedTimeTogether;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        if(firstEmployee < secondEmployee) {
            result = prime * result + firstEmployee;
            result = prime * result + secondEmployee;
        }
        else {
            result = prime * result + secondEmployee;
            result = prime * result + firstEmployee;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Pair temp = (Pair) obj;

        temp.increaseTime(this.workedTimeTogether);

        return true;
    }

    @Override
    public String toString() {
        return "ID of employees are [" + firstEmployee + ", " + secondEmployee + "]";
    }
}
