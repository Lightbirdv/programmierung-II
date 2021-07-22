package adressbuch;

/**
 * Eine textuelle Schnittstelle fuer ein Adressbuch.
 * ueber verschiedene Befehle kann auf das Adressbuch
 * zugegriffen werden.
 *
 * @author David J. Barnes und Michael K�lling.
 * @version 2008.03.30
 */
public class AdressbuchTexteingabe
{
    // Das Adressbuch, das angezeigt und manipuliert werden soll.
    private Adressbuch buch;
    // Ein Parser fuer die Befehlswoerter.
    private Parser parser;
    
    /**
     * Konstruktor fuer Objekte der Klasse AdressbuchTexteingabe.
     * @param buch das Adressbuch, das manipuliert werden soll.
     */
    public AdressbuchTexteingabe(Adressbuch buch)
    {
        this.buch = buch;
        parser = new Parser();
    }
    
    /**
     * Lies interaktiv Befehle vom Benutzer, die 
     * Interaktionen mit dem Adressbuch ermoeglichen.
     * Stoppe, wenn der Benutzer 'ende' eingibt.
     */
    public void starten() throws UngueltigerSchluesselException, KeinPassenderKontaktException
    {
        System.out.println(" -- Adressbuch --");
        System.out.println("Tippen Sie 'hilfe' fuer eine Liste der Befehle.");
        
        String command = "";
        
        while(!(command.equals("ende"))) {
            command = parser.liefereBefehl();
            if(command.equals("neu")){ 
                neuerEintrag();
            }
            else if(command.equals("liste")){
                list();
            }
            else if(command.equals("suche")){
                sucheEintrag();
            }
            else if(command.equals("hilfe")){
                hilfe();
            }
            else if(command.equals("hole")){
                holeEintrag();
            }
            else if(command.equals("aendere")){
                aendereEintrag();
            }
            else if(command.equals("entferne")){
                entferneEintrag();
            }
            else{
                // nichts tun.
            }
        } 
        System.out.println("Ade.");
    }
    
    /**
     * Fuege einen neuen Eintrag hinzu.
     */
    private void neuerEintrag() throws DoppelterSchluesselException, UngueltigerSchluesselException
    {
        while(true) {
            try {
                Kontakt kontakt = kontaktEinlesen();
                buch.addKontakt(kontakt);
                break;
            }
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Finde Eintraege mit passendem Praefix.
     */
    private void sucheEintrag()
    {
        System.out.println("Praefix des Suchschluessels:");
        String praefix = parser.zeileEinlesen();
        Kontakt[] ergebnisse = buch.searchKontakte(praefix);
        for(int i = 0; i < ergebnisse.length; i++){
            System.out.println(ergebnisse[i]);
            System.out.println("=====");
        }
    }
    
    /**
     * Zeige die zur Verfuegung stehenden Befehle.
     */
    private void hilfe()
    {
        parser.zeigeBefehle();
    }
    
    private void holeEintrag() throws UngueltigerSchluesselException {
        while(true) {
            try {
                System.out.println("Suchschluessel zum holen:");
                String schluessel = parser.zeileEinlesen();
                buch.schluesselBekannt(schluessel);
                Kontakt kontakt = buch.getKontakt(schluessel);
                System.out.println(kontakt);
                break;
            }
            catch(UngueltigerSchluesselException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void aendereEintrag() throws UngueltigerSchluesselException, KeinPassenderKontaktException {
        while(true) {
            try {
                System.out.println("Suchschluessel zum updaten:");
                String schluessel = parser.zeileEinlesen();
                if(buch.schluesselBekannt(schluessel)){
                    Kontakt kontakt = kontaktEinlesen();
                    buch.updateKontakt(schluessel, kontakt);
                    System.out.println("Alter Kontakt mit dem Schluessel: " + schluessel + " wird geupdatet");
                } else {
                    throw new KeinPassenderKontaktException(schluessel);
                }
                break;
            }
            catch(UngueltigerSchluesselException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void entferneEintrag() throws UngueltigerSchluesselException, KeinPassenderKontaktException {
        while(true) {
            try {
                System.out.println("Suchschluessel zum entfernen:");
                String schluessel = parser.zeileEinlesen();
                if(buch.schluesselBekannt(schluessel)) {
                    buch.deleteKontakt(schluessel);
                    System.out.println("Kontakt mit dem Schluessel: " + schluessel + " wurde entfernt.");
                } else {
                    throw new KeinPassenderKontaktException(schluessel);
                }
                break;
            }
            catch(UngueltigerSchluesselException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Kontakt kontaktEinlesen() {
        System.out.print("Name: ");
        String name = parser.zeileEinlesen();
        System.out.print("Telefon: ");
        String telefon = parser.zeileEinlesen();
        System.out.print("Email: ");
        String adresse = parser.zeileEinlesen();
        Kontakt kontakt = new Kontakt(name, telefon, adresse);
        return kontakt;
    }

    /**
     * Gib den Inhalt des Adressbuchs aus.
     */
    private void list()
    {
        System.out.println(buch.getAlleAlsText());
    }
}
