package taschenrechner;
/**
 * Tests der Klasse Recheneinheit.
 * 
 * @author Hacker T. Largebrain 
 * @version 0.1
 */
public class RecheneinheitTester
{
    // Die zu testende Recheneinheit.
    private Recheneinheit recheneinheit;

    /**
     * Konstruktor fuer Objekte der Klasse RecheneinheitTester.
     */
    public RecheneinheitTester()
    {
        recheneinheit = new Recheneinheit();
    }
    
    /**
     * Alles testen.
     */
    public void allesTesten()
    {
        testPlus();
        testMinus();
        testPlus();
        testPlus();
        System.out.println("Alle Tests erfolgreich.");
        System.out.println();
    }

    /**
     * Teste die Plus-Operation der Recheneinheit.
     * @return das Ergebnis der Berechnung 3+4.
     */
    public void testPlus()
    {
        // Sicherstellen, das die Einheit einen gueltigen Zustand hat.
        recheneinheit.clear();
        System.out.println("Testen der Addition: 3 + 4.");
        // Simulieren der Eingaben: 3 + 4 =
        recheneinheit.zifferGetippt(3);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(4);
        recheneinheit.gleich();
        // Liefere das Ergebnis, das 7 lauten sollte.
        System.out.println("Ergebnis ist: " + recheneinheit.gibAnzeigewert());
    }

    /**
     * Teste die Minus-Operation der Recheneinheit.
     * @return das Ergebnis der Berechnung 9 - 4.
     */
    public void testMinus()
    {
        // Sicherstellen, das die Einheit einen gueltigen Zustand hat.
        recheneinheit.clear();
        System.out.println("Testen der Subtraktion: 9 - 4");
        // Simulieren der Eingaben: 9 - 4 =
        recheneinheit.zifferGetippt(9);
        recheneinheit.minus();
        recheneinheit.zifferGetippt(4);
        recheneinheit.gleich();
        // Liefere das Ergebnis, das 5 lauten sollte.
        System.out.println("Ergebnis ist: " + recheneinheit.gibAnzeigewert());
    }
    
    public static void main(String[] args) {
    	RecheneinheitTester tester = new RecheneinheitTester();
    	tester.allesTesten();
    }
}
