import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private final int[] memory;
    private final Bitmap bitmap;
    private final TLB tlb;

    public PhysicalMemory() {
        memory = new int[524288];
        bitmap = new Bitmap();
        tlb = new TLB();
    }

    public TranslatedAddress translateVirtualAddress(VirtualAddress address, boolean read, boolean useTLB) {

        if(useTLB) {

            //If we find a match in the tlb then use it
            if(tlb.containsSP(address.getSP())) {

                int newAddress = tlb.translateAddress(address);
                return new TranslatedAddress(newAddress, true);
            }
            else {
                //If we don't find a match then we translate normally and update the tlb
                int newAddress = translateAddress(address,read);
                int f = newAddress-address.getOffset();
                tlb.addEntry(address.getSP(),f);

                return new TranslatedAddress(newAddress,false);
            }
        }
        else {
            //If we aren't using the TLB then translate the address normally
            return new TranslatedAddress(translateAddress(address, read),false);
        }
    }

    private int translateAddress(VirtualAddress address, boolean read) {
        int pageTableAddress = memory[address.getSegment()];
        if(pageTableAddress == -1)
            throw new PageFault("Page fault: Page table not resident");

        //The page table doesn't exist
        if(pageTableAddress == 0) {
            if(read)
                throw new NotFoundException("Page fault: Page table does not exist");

            //Create a new, blank page table
            pageTableAddress = createNewPageTable();
            memory[address.getSegment()] = pageTableAddress;
        }

        int pageAddress = memory[pageTableAddress+address.getPage()];
        if(pageAddress == -1)
            throw new PageFault("Page fault: Page not resident");

        if(pageAddress == 0) {
            if(read)
                throw new NotFoundException("Page fault: Page does not exist");

            //Create a new, blank page
            pageAddress = createNewPage();
            memory[pageTableAddress+address.getPage()] = pageAddress;
        }

        //Return the final physical address
        return  pageAddress+address.getOffset();
    }

    private int getFrameFromAddress(int address) {
        return address/512;
    }

    private int getAddressOfFrame(int frame) {
        return frame*512;
    }

    private int createNewPage() {
        int freeFrame = bitmap.getFirstFreeFrame();
        bitmap.setBit(freeFrame);
        return getAddressOfFrame(freeFrame);
    }

    private int createNewPageTable() {
        int freeFrame = bitmap.getFirstTwoFreeFrames();
        bitmap.setBit(freeFrame);
        bitmap.setBit(freeFrame+1);
        return getAddressOfFrame(freeFrame);
    }

    public void setPageTable(int segment, int pageTable) {
        memory[segment] = pageTable;

        if(pageTable != -1) {
            int frameNumber = getFrameFromAddress(pageTable);
            bitmap.setBit(frameNumber);
            bitmap.setBit(frameNumber+1);
        }
    }

    public void setPage(int segment, int pageTable, int page) {
        int pageTableAddress = memory[segment];
        memory[pageTableAddress + pageTable] = page;

        if(page != -1) {
            int frameNumber = getFrameFromAddress(page);
            bitmap.setBit(frameNumber);
        }
    }

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
