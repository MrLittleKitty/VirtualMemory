import org.junit.jupiter.api.Test;

public class BitmapTests {

    @Test
    public void testBitmap() {

        Bitmap map = new Bitmap();

        //Make sure the segment table frame is already marked as used
        assert map.getFirstFreeFrame() == 1;

        map.setBit(2);

        //Since frame 0 and frame 2 are taken the first 2 frames that are free is 3
        assert map.getFirstTwoFreeFrames() == 3;

        for(int i = 1; i < 31; i++) {
            map.setBit(i);
        }

        assert map.getFirstTwoFreeFrames() == 31;
    }
}
