import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class DriverTests {

    private static final File initFile = new File("tests/files/init.txt");
    private static final File addresses = new File("tests/files/virtualAddresses.txt");
    private static final File outputFile = new File("tests/files/output.txt");
    private Driver driver;

    @BeforeEach
    public void init() {
        driver = new Driver();
    }

    @Test
    public void runDriverTest() {

        driver.init(initFile);

        driver.translateAddresses(addresses,false);

       String outputFileString = getOutputFileString();

        assert outputFileString != null;

        assert driver.getOutputString().equalsIgnoreCase(outputFileString);
    }

    private String getOutputFileString() {
        String line = null;
        try(FileReader fileReader = new FileReader(outputFile)) {
            try(BufferedReader reader = new BufferedReader(fileReader)) {
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

}
