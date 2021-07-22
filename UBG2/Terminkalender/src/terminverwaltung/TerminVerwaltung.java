package terminverwaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Verwaltet Termine und erlaubt den Zugriff nach Id, Datum oder Text des
 * Termins
 *
 * @author beuth
 */
public class TerminVerwaltung {

    private TreeMap<LocalDate, ArrayList<Termin>> termineDate;
    private HashMap<String, Termin> termineId;

    public TerminVerwaltung() {
        initialisieren();
    }

    protected void initialisieren() {
        termineDate = new TreeMap<LocalDate, ArrayList<Termin>>();
        termineId = new HashMap<String, Termin>();
    }

    /**
     * Liefert die Liste mit allen Terminen des angegebenen Datums.
     *
     * @param date Datum fuer das die Termine abgefragt werden.
     * @return Liste mit Terminen des angegebenen Datums.
     */
    public List<Termin> getTermineTag(LocalDate date) {
        ArrayList<Termin> liste = termineDate.get(date);
        if (liste != null) {
            TreeSet<Termin> set = new TreeSet<Termin>(liste);
            liste = new ArrayList<Termin>(set);
        }
        return liste;
    }

    /**
     * Fuegt den angegebenen Termin in beide Maps der Terminverwaltung ein, 
     * wenn keine Terminueberschneidung mit anderen Terminen vorliegt.
     *
     * @param t Hinzuzufuegender Termin
     * @throws TerminUeberschneidungException wenn es bereits einen Termin gibt,
     * der sich mit dem Termin t schneidet.
     */
    public void addTermin(Termin t) throws TerminUeberschneidungException {
        if(checkTerminUeberschneidung(t) != null) {
            throw new TerminUeberschneidungException(t);
        }
        //ArrayList<Termin> liste = new ArrayList<Termin>();
        //termineDate.put(t.getDatum(), liste);
        addTerminDate(t);
        termineId.put(t.getId(), t);
    }

    /**
     * Fuegt den angegebenen Termin in die TreeMap termineDate ein. Falls noch
     * keine Liste zu dem Datum eingetragen ist, wird eine neue ArrayList
     * erstellt.
     *
     * @param t Einzufuegender Termin
     */
    private void addTerminDate(Termin t) {
        ArrayList<Termin> liste;
        LocalDate date = t.getDatum();
        liste = termineDate.get(date);
        if (liste == null) {
            liste = new ArrayList<Termin>();
            termineDate.put(date,liste);
        }
        liste.add(t);
    }

    /**
     * Aktualisiert den uebergebenen Termin in der Terminverwaltung. Dazu wird
     * zunächst der alte Termin mit der Id gelöscht und dann der neue Termin
     * hinzugefuegt. Sollte dies fehlschlagen, wid der alte Termin
     * wiederhergestellt.
     *
     * @param neu zu aktualisierender Termin
     * @return true, wenn das Update funktioniert hat, false sonst.
     * @throws TerminUeberschneidungException wenn es eine Ueberschneidung mit
     * dem neuen gibt.
     */
    public boolean updateTermin(Termin neu) throws TerminUeberschneidungException {
        Termin alt;
        if (neu == null) {
            return false;
        }
        alt = termineId.get(neu.getId());
        if (alt != null) {
            removeTermin(alt);
            try {
                addTermin(neu);
            } catch (TerminUeberschneidungException e) {
                // Wenn der Ueberschneidungstermin nicht dieselbe ID hat, dann wird der alte Termin 
                // wieder hergestellt und ein Fehler geworfen.
                if (e.getTermin().getId().equals(neu.getId())) {
                    restoreTermin(alt);
                    throw e;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Loescht den Termin aus allen Maps.
     * Falls die ArrayList nach dem Löschen leer ist, wird das Schlüssel-Wert Paar
     * aus der TreeMap gelöscht.
     *
     * @param t zu loeschender Termin.
     */
    public void removeTermin(Termin t) {
        ArrayList<Termin> liste = termineDate.get(t.getDatum());
        if(liste!=null){
            liste.remove(t);
        }
        if(liste.isEmpty()) {
            termineDate.remove(t.getDatum());
        }
        termineId.remove(t.getId());
    }

    /**
     * Fuegt einen geloeschten Termin wieder in die Maps ein. Hierbei wird
     * nicht geprueft, ob es Ueberschneidungen gibt. Ein eventuell noch
     * vorhandener Termin wird ueberschrieben.
     *
     * @param t zu speichernder Termin.
     */
    private void restoreTermin(Termin t) {
        addTerminDate(t);
        termineId.put(t.getId(), t);
    }

    /**
     * Prueft, ob sich der uebergebene Termin mit einem Termin der
     * Terminverwaltung schneidet.
     *
     * @param neu zu pruefender Termin.
     * @return Termin, mit dem eine Ueberschneidung besteht oder null.
     */
    public Termin checkTerminUeberschneidung(Termin neu) {
        ArrayList<Termin> liste;
        LocalDate date = neu.getDatum();
        liste = termineDate.get(date);
        if (liste == null) {
            return null;
        }
        else {
            for(int termin = 0; termin < liste.size(); termin++) {
                boolean ueberschneidung = neu.isUeberschneidung(liste.get(termin));
                if (ueberschneidung){
                    return liste.get(termin);
                }
            }
        }
        return null;
    }

    /**
     * Liefert den Termin mit der angegebenen id
     *
     * @param id id des gewuenschten Termins.
     * @return Termin zu der id oder null
     */
    public Termin getTermin(String id) {
        return termineId.get(id);
    }

    /**
     * Liefert alle Termine im Planer.
     *
     * @return alle Termine als Array.
     */
    public Termin[] getAllTermine() {
        Termin[] termine = new Termin[termineId.size()];
        TreeSet<Termin> set = new TreeSet<Termin>();
        set.addAll(termineId.values());
        set.toArray(termine);
        return termine;
    }
}
