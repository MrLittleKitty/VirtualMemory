import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the path for the init file");
        String initPath = scanner.nextLine();

        System.out.println("Please enter the path for the input file");
        String inputPath = scanner.nextLine();

        System.out.println("Please enter the path for the output file");
        String outputPath = scanner.nextLine();

        String useTLB = null;

        System.out.println("Use TLB? Y/N");

        do {
            useTLB = scanner.next();
        }
        while(!useTLB.equalsIgnoreCase("y")
                && !useTLB.equalsIgnoreCase("n"));

        boolean tlb = useTLB.equalsIgnoreCase("y");

        Driver driver = new Driver();

        driver.init(new File(initPath));

        driver.translateAddresses(new File(inputPath), tlb);

        try {
            Files.write(new File(outputPath).toPath(),driver.getOutputString().getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
