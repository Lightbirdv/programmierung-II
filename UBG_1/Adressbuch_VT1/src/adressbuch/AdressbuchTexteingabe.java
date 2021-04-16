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
    public void starten()
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
    private void neuerEintrag()
    {
        System.out.print("Name: ");
        String name = parser.zeileEinlesen();
        System.out.print("Telefon: ");
        String telefon = parser.zeileEinlesen();
        System.out.print("Email: ");
        String adresse = parser.zeileEinlesen();
        buch.addKontakt(new Kontakt(name, telefon, adresse));
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
    
    private void holeEintrag() {
        System.out.println("Suchschluessel zum holen:");
        String schluessel = parser.zeileEinlesen();
        Kontakt kontakt = buch.getKontakt(schluessel);
        System.out.println(kontakt);
    }

    private void aendereEintrag() {
        System.out.println("Suchschluessel zum updaten:");
        String schluessel = parser.zeileEinlesen();
        Kontakt kontakt = kontaktEinlesen();
        buch.updateKontakt(schluessel, kontakt);
        System.out.print("Alter Kontakt mit dem Schluessel: " + schluessel + " wird geupdatet mit: " + name + " -- " + telefon + " -- " + adresse);
    }

    private void entferneEintrag() {
        System.out.println("Suchschluessel zum entfernen:");
        String schluessel = parser.zeileEinlesen();
        buch.deleteKontakt(schluessel);
        System.out.println("Kontakt mit dem Schluessel: " + schluessel + " wurde entfernt.");
    }

    private Kontakt kontaktEinlesen() {
        System.out.print("Neuer Name: ");
        String name = parser.zeileEinlesen();
        System.out.print("Neues Telefon: ");
        String telefon = parser.zeileEinlesen();
        System.out.print("Neue Email: ");
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
