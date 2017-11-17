public class Bitmap {

    //Keeps track of the free and occupied frames
    //Consists of 1024 bits (one for each frame)
    //Implemented as array of 32 integers
    //Each int is 32 bits
    private static final int[] MASK = new int[32];
    private static final int[] MASK2 = new int[32];
    static {
        for (int i = 0; i < MASK.length; i++) {
            MASK[i] = 0x80000000 >>> i;
        }

        for (int i = 0; i < MASK.length; i++) {
            MASK2[i] = ~MASK[i];
        }
    }

    private final int[] bitmap;
    public Bitmap() {
        bitmap = new int[32];
    }

    public void setBit(int bitNumber) {
        int j = bitNumber / 32;
        int i = bitNumber % 32;

        bitmap[j] = bitmap[j] | MASK[i];
    }

    public void unsetBit(int bitNumber) {
        int j = bitNumber / 32;
        int i = bitNumber % 32;

        bitmap[j] = bitmap[j] & MASK2[i];
    }


}
