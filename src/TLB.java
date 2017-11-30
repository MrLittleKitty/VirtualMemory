import java.util.HashMap;

public class TLB {

    private final int[] SPs;
    private final int[] Fs;
    private final int[] LRUs;

    public TLB() {
        final int tlbSize = 4;
        SPs = new int[tlbSize];
        Fs = new int[tlbSize];
        LRUs = new int[tlbSize];
        for(int i = 0; i < tlbSize; i++) {
            //Better safe than sorry when it comes to initialization
            LRUs[i] = 0;
            SPs[i] = -1;
            Fs[i] = -1;
        }
    }


    public int translateAddress(VirtualAddress address) {
        for(int i = 0; i < SPs.length; i++) {
            if(SPs[i] == address.getSP()) {
                int physicalAddress = Fs[i]+address.getOffset();
                int lru = LRUs[i];
                for(int j = 0; j < LRUs.length; j++) {
                    if(LRUs[j] > lru) {
                        LRUs[j] = LRUs[j]-1;
                    }
                }
                LRUs[i] = 3;
                return physicalAddress;
            }
        }
        throw new IllegalStateException("We should definitely not get here");
    }

    public void addEntry(int sp, int f) {

        for(int i = 0; i < LRUs.length; i++) {

            //When we find the LRU with the value 0,
            if(LRUs[i] == 0) {
                LRUs[i] = 3;
                SPs[i] = sp;
                Fs[i] = f;
                for(int j = 0; j < LRUs.length; j++) {
                    if(j != i && LRUs[j] > 0) {
                        LRUs[j] = LRUs[j]-1;
                    }
                }
                break;
            }
        }
    }

    public boolean containsSP(int sp) {
        for(int i : SPs) {
            if(i == sp)
                return true;
        }
        return false;
    }
}
