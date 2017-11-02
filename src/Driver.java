public class Driver {

    //For virtual address translation: (read)
    //  If the segment table or page table entry is -1 then output "pf" (page fault) and continue to next virtual address
    //  If the segment table or page table entry is 0 then output "error" and continue to next virtual address
    //Otherwise output the physical address = (memory[memory[s] + p] + w)

    //For write:
    //  If the segment table or page table entry is -1 then output "pf" (page fault) and continue to next virtual address
    //  If the segment table entry is 0, allocate a new, blank page table that is all zeroes
    //      Then update the segment table entry accordingly
    //      Then continue with the translation process
    //  If the page table entry is 0, allocate a new, blank page
    //      Then update the page table accordingly
    //      Then continue with the translation process

}
