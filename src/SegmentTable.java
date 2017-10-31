public class SegmentTable {

    //For each entry in the segment table:
    //-1 = The page table at this index DOES exist but it isn't in memory. (page fault)
    //0 = The page table does not exist.
    //          If you try to read from it you will get an error
    //          If you try to write to it, it should create a blank Page Table
    //Any other value = Address of the page table (not the frame #)
    //          Page table starts at physical address f


}
