import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataStore {
    private static final String DATA_DIR = "data/";

    public static void savePeople(List<Person> people) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(DATA_DIR + "people.txt"))) {
            for (Person person : people) {
                bufferedWriter.write(person.getName());
                bufferedWriter.newLine();
            }
        }
    }

    public static List<Person> loadPeople() throws IOException {
        List<Person> people = new ArrayList<>();
        Path path = Paths.get(DATA_DIR + "people.txt");
        if (!Files.exists(path)) return people;
        
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                people.add(new Person(line.trim()));
            }
        }
        return people;
    }
}
