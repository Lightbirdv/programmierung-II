package terminplaner;

/**
 * Zeigt eine Terminueberschneidung an.
 * @author beuth
 */
public class TerminUeberschneidungException extends UngueltigerTerminException {
    public TerminUeberschneidungException(Termin termin){
        super(termin);
    }
    
    @Override
    public String getMessage(){
        return String.format("Es gibt bereits einen Termin am %s von %s bis %s%n", termin.getDatum(), termin.getVon(), termin.getBis());
    }
}
