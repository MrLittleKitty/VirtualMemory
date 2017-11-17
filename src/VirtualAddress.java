public class VirtualAddress {

    //32 bits
    //Divided into s(segment), p(page), and w(word)
    //s has 9 bits
    //p is 10 bits
    //w is 9 bits
    //The leading 4 bits are empty and unused
    private static final int segmentMask = 267911168;
    private static final int pageMask = 523776;
    private static final int offsetMask = 511;


    private final int segment;
    private final int page;
    private final int offset;

    public VirtualAddress(int virtualAddress) {
        int newSeg = virtualAddress & segmentMask;
        segment = newSeg >>> 19;
        int newPage = virtualAddress & pageMask;
        page = newPage >>> 9;
        int newOffset = virtualAddress & offsetMask;
        offset = newOffset;
    }

    public int getSegment() {
        return segment;
    }

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return offset;
    }

}
