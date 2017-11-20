import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    //For virtual address translation: (read)
    //  If the segment table or page table entry is -1 then outputList "pf" (page fault) and continue to next virtual address
    //  If the segment table or page table entry is 0 then outputList "error" and continue to next virtual address
    //Otherwise outputList the physical address = (memory[memory[s] + p] + w)

    //For write:
    //  If the segment table or page table entry is -1 then outputList "pf" (page fault) and continue to next virtual address
    //  If the segment table entry is 0, allocate a new, blank page table that is all zeroes
    //      Then update the segment table entry accordingly
    //      Then continue with the translation process
    //  If the page table entry is 0, allocate a new, blank page
    //      Then update the page table accordingly
    //      Then continue with the translation process
    public final List<String> outputList;
    private final PhysicalMemory memory;

    public Driver() {
        outputList = new ArrayList<>();
        memory = new PhysicalMemory();
    }

    public void init(File initFile) {

        String firstLine = null;
        String secondLine = null;

        try(FileReader fileReader = new FileReader(initFile)) {
            try(BufferedReader reader = new BufferedReader(fileReader)) {
                firstLine = reader.readLine();
                secondLine = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] pairs = firstLine.split(" ");
        String[] triples = secondLine.split(" ");

        for(int i = 0; i < pairs.length; i++) {
            int seg = Integer.parseInt(pairs[i]);
            i++;
            int pageTable = Integer.parseInt(pairs[i]);
            memory.setPageTable(seg,pageTable);
        }

        for(int i = 0; i < triples.length; i++) {
            int pageTable = Integer.parseInt(triples[i]);
            i++;
            int segment = Integer.parseInt(triples[i]);
            i++;
            int page = Integer.parseInt(triples[i]);
            memory.setPage(segment,pageTable,page);
        }
    }

    public void translateAddresses(File virtualAddressFile) {
        String line = null;
        try(FileReader fileReader = new FileReader(virtualAddressFile)) {
            try(BufferedReader reader = new BufferedReader(fileReader)) {
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] pairs = line.split(" ");
        for(int i = 0; i < pairs.length; i++) {
            String mode = pairs[i];
            i++;
            String address = pairs[i];
            VirtualAddress virtualAddress = new VirtualAddress(Integer.parseInt(address));

            try {
                int physicalAddress = memory.translateVirtualAddress(virtualAddress, mode.equalsIgnoreCase("0"));
                output(physicalAddress+"");
            }
            catch (PageFault fault) {
                output("pf");
            }
            catch (NotFoundException notFound) {
                output("err");
            }
        }
    }

    private void output(String message) {
        this.outputList.add(message);
        System.out.println(message);
    }
}
