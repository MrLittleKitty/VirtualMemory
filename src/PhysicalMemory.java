public class PhysicalMemory {

    //Each integer is one addressable word of memory
    //Each frame is 512 words
    //Segment table occupies 1 frame
    //Each page table occupies 2 consecutive frames
    //Each page occupies 1 frame
    //Physical address consists of 1024 frames (2 MB) (524288 integers)
    //Segment table is always in frame 0 and will never be paged
    //A page table may be placed into any free 2 consecutive frames
    //A page may be placed into any free frame
    int[] memory = new int[524288];

    //memory[s] where 0 < s < 5011 accesses the segment table
    //      if memory[s] > 0 then it points to a resident page table
    //memory[memory[s] + p] accesses that page table
    //      if memory[memory[s] + p] > 0 then it points to a resident page
    //Add the offset (w) to the value at memory[memory[memory[s] + p]] for the physical address


    //Initialization of the physical memory:
    //  Reading in an init file: (the file consists of 2 lines)
    //      First line: pairs of ints (S, F)
    //          The page table of segment S starts at physical address F
    //          If F = -1 then the corresponding page table isnt resident and we put -1 into segment table
    //      Second line: triple of ints (P S F)
    //          Page P of segment S starts at address F
    //          If F = -1 then the corresponding page isnt resident and we put -1 into the page table
}
