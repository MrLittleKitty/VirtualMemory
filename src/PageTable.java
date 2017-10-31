public class PageTable {

    //For each entry of the page table:
    //-1 = The page at this index DOES exist but its not in memory (page fault)
    //0 = The page does not exist
    //      If you read it will be an error
    //      If you write it will create a blank page
    //Any other value = address of the page
    //      Page starts at physical address f

}
