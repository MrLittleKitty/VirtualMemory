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
        setBit(0);
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

    public int getFirstFreeFrame() {
        int test;
        for(int i = 0; i < bitmap.length; i++) {
            for(int j = 0; j < 32; j++) {
                test = bitmap[i] & MASK[j];
                //If the test is 0 we found our free bit
                if(test == 0) {
                    return (i*32)+j;
                }
            }
        }
        return -1;
    }

    public int getFirstTwoFreeFrames() {
        int test;
        for(int i = 0; i < bitmap.length; i++) {
            for(int j = 0; j < 32; j++) {
                test = bitmap[i] & MASK[j];
                //If the test is 0 then we found our first free bit
                if(test == 0) {
                    //Check if the next bit is free
                    int k = j+1;
                    if(k < 32)
                        test = bitmap[i] & MASK[k];
                    else {
                        //If we were at the end of one integer then check the start of the next one
                        int l = i+1;
                        if(l < bitmap.length)
                            test = bitmap[l] & MASK[0]; //Check the first bit of the next integer
                        else
                            return -1; //We were at the last bit and there arent 2 free ones
                    }

                    if(test == 0)
                        return (i*32)+j;
                }
            }
        }
        return -1;
    }
}
