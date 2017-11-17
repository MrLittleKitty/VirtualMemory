import org.junit.jupiter.api.Test;

public class VATests {

    @Test
    public void testWorks() {

        VirtualAddress address = new VirtualAddress(1048576);

        assert address.getSegment() == 2;
        assert address.getPage() == 0;
        assert  address.getOffset() == 0;

        address = new VirtualAddress(1048586);

        assert address.getSegment() == 2;
        assert address.getPage() == 0;
        assert  address.getOffset() == 10;

        address = new VirtualAddress(1049088);

        assert address.getSegment() == 2;
        assert address.getPage() == 1;
        assert  address.getOffset() == 0;
    }
}
