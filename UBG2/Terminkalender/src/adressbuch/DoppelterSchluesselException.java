package adressbuch;

public class DoppelterSchluesselException extends UngueltigerSchluesselException{
    private String schluessel;

    public DoppelterSchluesselException(String schluessel) {
        super("Der Schluessel: '" + schluessel + "' existiert bereits.");
        this.schluessel = schluessel;
    }

    public String getSchluessel() {
        return schluessel;
    }
}
