package adressbuch;

public class UngueltigerSchluesselException extends Exception {
    private String schluessel;

    public UngueltigerSchluesselException(String schluessel) {
        super("Der Schluessel '" + schluessel + "' ist ungueltig.");
        this.schluessel = schluessel;
    }

    public String getSchluessel() {
        return schluessel;
    }
}