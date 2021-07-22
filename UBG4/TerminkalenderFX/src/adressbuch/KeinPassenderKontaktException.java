package adressbuch;

public class KeinPassenderKontaktException extends UngueltigerSchluesselException {
    private String schluessel;

    public KeinPassenderKontaktException(String schluessel) {
        super("Kontakt zu dem Schluessel: '" + schluessel + "' existiert nicht.");
        this.schluessel = schluessel;
    }

    public String getSchluessel() {
        return schluessel;
    }
}
