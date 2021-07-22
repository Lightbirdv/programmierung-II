package taschenrechner;
/**
 * Die Haupteinheit eines Rechners. Sie ist fuer
 * die arithmetischen Berechnungen zustaendig.
 * 
 * @author  Hacker T. Largebrain
 * @version 0.1
 */
public class Recheneinheit
{
    // Der Anzeigewert.
    private int anzeigewert;
    // Der zuletzt getippte Operator (+ oder -).
    private char letzterOperator;
    // Der linke Operand zum letzten Operator.
    private int linkerOperand;
    private int speicherwert;

    /**
     * Erzeuge eine Recheneinheit.
     */
    public Recheneinheit()
    {
        anzeigewert = 0;
        letzterOperator = ' ';
        linkerOperand = 0;
    }

    /**
     * Liefere den Wert, der aktuell in der Anzeige gezeigt
     * wird.
     */
    public int gibAnzeigewert()
    {
        return anzeigewert;
    }

    /**
     * Eine Zifferntaste wurde getippt.
     * @param ziffer die einzelne Ziffer.
     */
    public void zifferGetippt(int ziffer)
    {
        //anzeigewert um 10er Stelle verschieben und ziffer addieren
        anzeigewert = anzeigewert * 10 + ziffer;
    }

    /**
     * Die '+'-Taste wurde getippt.
     */
    public void plus()
    {
        letztenOperatorAnwenden();
        letzterOperator = '+';
        anzeigewert = 0;
    }

    /**
     * Die '-'-Taste wurde getippt.
     */
    public void minus()
    {
        letztenOperatorAnwenden();
        letzterOperator = '-';
        anzeigewert = 0;
    }
    
    /**
     * Die Gleich-Taste wurde getippt.
     */
    public void gleich()
    {
        if(letzterOperator == '+') {
            anzeigewert = linkerOperand + anzeigewert;
        }
        else {
            anzeigewert = linkerOperand - anzeigewert;
        }
        linkerOperand = 0;
    }

    /**
     * Die C-Taste (fuer 'Clear') wurde getippt.
     */
    public void clear()
    {
    	anzeigewert = 0;
    }

    /**
     * @return den Titel dieser Recheneinheit.
     */
    public String gibTitel()
    {
        return "Der Super-Rechner";
    }

    /**
     * @return den Autor dieser Recheneinheit.
     */
    public String gibAutor()
    {
        return "Hacker T. Largebrain";
    }

    /**
     * @return die Versionsnummer dieser Recheneinheit.
     */
    public String gibVersion()
    {
       return "Version 0.2";
    }
    
    
    /**
     * Eine Taste mit einem Operator wurde getippt.
     * Wende den unmittelbar vorhergegangenen Operator
     * an, um ein Zwischenergebnis zu berechnen. Dieses
     * bildet dann den linken Operand des neuen Operators.
     */
    private void letztenOperatorAnwenden()
    {
        if(letzterOperator == '+') {
            linkerOperand = anzeigewert;
        }
        else if(letzterOperator == '-') {
            linkerOperand = anzeigewert;
        }
        else {
            // Es gibt keinen letzten Operator.
            linkerOperand = anzeigewert;
        }
    }
    
    public void speicherEingabe () {
        speicherwert = anzeigewert;
    }
    
    public void speicherAusgabe () {
        anzeigewert = speicherwert;
    }
    
    public void speicherPlus () {
        speicherwert += anzeigewert;
    }
    
    public void speicherMinus () {
        speicherwert -= anzeigewert;
    }
    
    public void speicherC() {
        speicherwert = 0;
    }
    
}
