package employees;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Project {

    private int id;

    private Set<TimePeriod> periods;

    public Project(int id, TimePeriod period) throws IllegalArgumentException {
        if(Company.checkIfIsNotPositive(id)) {
            throw new IllegalArgumentException("Project ID must be positive!");
        }
        this.id = id;

        this.periods = new TreeSet<>(new Comparator<TimePeriod>() {

            @Override
            public int compare(TimePeriod firstTimePeriod, TimePeriod secondTimePeriod) {

                int parallelDays = TimePeriod.calculateParallelDays(firstTimePeriod, secondTimePeriod);

                if(parallelDays != 0) {
                    return 0;
                }

                if(firstTimePeriod.getDateFrom().compareTo(secondTimePeriod.getDateFrom()) < 0) {
                    return -1;
                }

                return 1;
            }
        } );

        try {
            this.addNewPeriod(period);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public Set<TimePeriod> getPeriods() {
        return Collections.unmodifiableSet(this.periods);
    }

    void addNewPeriod(TimePeriod period) throws NullPointerException {
        if(period == null) {
            throw new NullPointerException("Can't add period which is 'null'!");
        }

        this.periods.add(period);
    }
}
