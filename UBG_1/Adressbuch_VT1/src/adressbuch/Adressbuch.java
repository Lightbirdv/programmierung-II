package adressbuch;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Eine Klasse zur Verwaltung einer beliebigen Anzahl von Kontakten. Die Daten
 * werden sowohl ueber den Namen als auch ueber die Telefonnummer indiziert.
 *
 * @author David J. Barnes und Michael K?lling.
 * @version 2008.03.30
 */
public class Adressbuch {

    // Speicher fuer beliebige Anzahl von Kontakten.
    private HashMap<String, Kontakt> buch;
    private int anzahlEintraege;

    /**
     * Initialisiere ein neues Adressbuch.
     */
    public Adressbuch() {
        buch = new HashMap<String, Kontakt>();
        anzahlEintraege = 0;
        setTestData();
    }

    /**
     * Schlage einen Namen oder eine Telefonnummer nach und liefere den
     * zugehoerigen Kontakt.
     *
     * @param schluessel der Name oder die Nummer zum Nachschlagen.
     * @return den zum Schluessel gehoerenden Kontakt.
     */
    public Kontakt getKontakt(String schluessel) {
        if(schluessel==null || schluessel.trim().isEmpty()) {
            throw new IllegalArgumentException("Ungültiger Schlüssel!");
        }
        return buch.get(schluessel);
    }

    /**
     * Ist der gegebene Schluessel in diesem Adressbuch bekannt?
     *
     * @param schluessel der Name oder die Nummer zum Nachschlagen.
     * @return true wenn der Schluessel eingetragen ist, false sonst.
     */
    public boolean schluesselBekannt(String schluessel) {
        return buch.containsKey(schluessel);
    }

    /**
     * Fuege einen neuen Kontakt in dieses Adressbuch ein.
     *
     * @param kontakt der neue Kontakt.
     */
    public void addKontakt(Kontakt kontakt) {
        buch.put(kontakt.getName(), kontakt);
        buch.put(kontakt.getTelefon(), kontakt);
        anzahlEintraege++;
    }

    /**
     * Aendere die Kontaktdaten des Kontakts, der bisher unter dem gegebenen
     * Schluessel eingetragen war.
     *
     * @param alterSchluessel einer der verwendeten Schl?ssel.
     * @param daten die neuen Kontaktdaten.
     */
    public void updateKontakt(String alterSchluessel, Kontakt daten) {
        deleteKontakt(alterSchluessel);
        addKontakt(daten);
    }

    /**
     * Suche nach allen Kontakten mit einem Schluessel, der mit dem gegebenen
     * Praefix beginnt.
     *
     * @param praefix der Schluesselpraefix, nach dem gesucht werden soll.
     * @return ein Array mit den gefundenen Kontakten.
     */
    public Kontakt[] searchKontakte(String praefix) {
        if (praefix == null || praefix.isEmpty()) {
            return getAlleKontakte();
        }
        // Eine Liste fuer die Treffer anlegen.
        List<Kontakt> treffer = new ArrayList<Kontakt>();
        // Finden von Schluesseln, die gleich dem oder groesser als
        // der Praefix sind.
        TreeSet<String> keys = new TreeSet(buch.keySet());
        SortedSet rest = keys.tailSet(praefix);
        Iterator<String> it = rest.iterator();
        // Stoppen bei der ersten Nichtuebereinstimmung.
        while (it.hasNext()) {
            String schluessel = it.next();
            if (schluessel.startsWith(praefix)) {
                treffer.add(buch.get(schluessel));
            } else {
                break;
            }
        }
        Kontakt[] ergebnisse = new Kontakt[treffer.size()];
        treffer.toArray(ergebnisse);
        return ergebnisse;
    }

    /**
     * @return die momentane Anzahl der Eintr?ge in diesem Adressbuch.
     */
    public int getAnzahlEintraege() {
        return anzahlEintraege;
    }

    /**
     * Entferne den Eintrag mit dem gegebenen Schluessel aus diesem Adressbuch.
     *
     * @param schluessel einer der Schluessel des Eintrags, der entfernt werden
     * soll.
     */
    public void deleteKontakt(String schluessel) {
        Kontakt kontakt = buch.get(schluessel.trim());
        if (kontakt != null) {
            buch.remove(kontakt.getName());
            buch.remove(kontakt.getTelefon());
            anzahlEintraege--;
        }
    }

    /**
     * @return alle Kontaktdaten, sortiert nach der Sortierreihenfolge, die die
     * Klasse Kontakt definiert.
     */
    public String getAlleAlsText() {
        // Weil jeder Satz unter zwei Schluesseln eingetragen ist,
        // muss ein Set mit den Kontakten gebildet werden. Dadurch
        // werden Duplikate entfernt.
        StringBuffer alleEintraege = new StringBuffer();
        TreeSet
                <Kontakt> sortierteDaten = new TreeSet<Kontakt>(buch.values());
        for (Kontakt kontakt : sortierteDaten) {
            alleEintraege.append(kontakt);
            alleEintraege.append('\n');
            alleEintraege.append('\n');
        }
        return alleEintraege.toString();
    }

    public Kontakt[] getAlleKontakte() {
        TreeSet<Kontakt> sortierteDaten = new TreeSet<Kontakt>(buch.values());
        Kontakt[] ergebnisse = new Kontakt[sortierteDaten.size()];
        sortierteDaten.toArray(ergebnisse);
        return ergebnisse;
    }

    public void setTestData() {
        Kontakt[] testdaten = {
            new Kontakt("david", "08459 100000", "david@gmx.de"),
            new Kontakt("michael", "08459 200000", "michael@gmx.de"),
            new Kontakt("john", "08459 300000", "john@gmx.de"),
            new Kontakt("heike", "08459 400000", "heike@gmx.de"),
            new Kontakt("emma", "08459 500000", "emma@gmx.de"),
            new Kontakt("simone", "08459 600000", "simone@gmx.de"),
            new Kontakt("chris", "08459 700000", "chris@gmx.de"),
            new Kontakt("axel", "08459 800000", "axel@gmx.de"),};
        for (Kontakt kontakt : testdaten) {
            addKontakt(kontakt);
        }
    }
}
