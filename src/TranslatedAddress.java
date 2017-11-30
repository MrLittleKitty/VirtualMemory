public class TranslatedAddress {

    public final int address;
    public final boolean tlbHit;

    public TranslatedAddress(int address, boolean tlbHit) {
        this.address = address;
        this.tlbHit = tlbHit;
    }

}
