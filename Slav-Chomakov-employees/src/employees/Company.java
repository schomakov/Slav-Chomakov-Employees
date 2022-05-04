package employees;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.util.Map.Entry;

public final class Company {

	private Company() {};

    public static void printBestPair(Set<Pair> allPairs) {

        if( (allPairs == null) || allPairs.isEmpty() ) {
            System.out.println("Missing pairs!");
            return;
        }

        // longest pair - the pair that has most working days together
        Pair longestPair = allPairs.iterator().next();

        Iterator<Pair> iterator = allPairs.iterator();

        iterator.next();

        while(iterator.hasNext()) {
            Pair pair = iterator.next();

            if(longestPair.getWorkedTimeTogether() < pair.getWorkedTimeTogether()){
                longestPair = pair;
            }
        }

        System.out.println("Pair with most days worked together: ");
        System.out.println(longestPair);
        System.out.println("Total days worked on projects: " + longestPair.getWorkedTimeTogether());
    }

    public static Set<Pair> getAllPairs(Map<Integer, Employee> employees){

        if(employees == null) {
            return null;
        }

        Set<Pair> allPairs = new HashSet<>();

        // check if two employees had worked together on some project(s)
        for(Employee firstEmployee : employees.values()) {

            boolean flag = false;

            for(Employee secondEmployee : employees.values()) {

                if(flag == false) {

                    if(firstEmployee == secondEmployee) {
                        flag = true;
                    }
                    continue;
                }

                for(Entry<Integer, Project> firstEmpProject : firstEmployee.getProjects().entrySet()) {

                    Project secondEmpProject = secondEmployee.getProjects().get(firstEmpProject.getKey());

                    if(secondEmpProject == null) {
                        continue;
                    }

                    int timeSpentTogether = Company.calculateParallelDays(firstEmpProject.getValue(), secondEmpProject);
                    // get total time spent working together on this current project

                    if(timeSpentTogether > 0) {
                        // add team to allPairs OR add time period to already existing team in allPairs
                        try {
                            allPairs.add(new Pair(firstEmployee.getId(), secondEmployee.getId(), timeSpentTogether));
                        }catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Skipping pair [" + firstEmployee.getId() + ", " + secondEmployee.getId() +"]");
                        }
                    }
                }
            }
        }

        return allPairs;
    }

    private static int calculateParallelDays(Project firstProject, Project secondProject) {
        int totalParallelDays = 0;

        // iterate over every pair of periods and check if they are parallel
        for(TimePeriod firstPeriod : firstProject.getPeriods()) {

            // when flag == true we start summing time intervals
            boolean flag = false;

            for(TimePeriod secondPeriod : secondProject.getPeriods()) {

                int tempParallelDays = TimePeriod.calculateParallelDays(firstPeriod, secondPeriod);

                if(flag) {
                    if(tempParallelDays == 0) {
                        break;
                    }
                }

                if(tempParallelDays > 0) {
                    totalParallelDays += tempParallelDays;
                    flag = true;
                }
            }
        }

        return totalParallelDays;
    }

    public static boolean checkIfIsNotPositive(int number) {
        if(number < 1) {
            return true;
        }

        return false;
    }

}
