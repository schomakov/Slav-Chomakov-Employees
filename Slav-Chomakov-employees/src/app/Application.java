package app;

import employees.Company;
import employees.Employee;
import employees.Pair;
import utils.Reader;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class Application {

    public static void main(String[] args) {

        File file = Reader.getUserInputFile();

        if(file == null) {
            return;
        }

        Reader reader = null;

        try {
            reader = new Reader(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Cannot continue.");
        }

        Map<Integer, Employee> employees = reader.convertFileInformation();

        reader.close();

        Set<Pair> allPairs = Company.getAllPairs(employees);

        Company.printBestPair(allPairs);

    }
}
