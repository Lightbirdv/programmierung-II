/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminverwaltung;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Vico Drescher 
 */
public class TerminVerwaltungTest {
    
    public TerminVerwaltung tv; 
    public Termin t;
    public Termin t2;
    
    public TerminVerwaltungTest() {
    }

    @Before
    public void setUp() throws UngueltigerTerminException {
        tv = new TerminVerwaltung();
        t = new Termin("test termin1", LocalDate.of(2021,4,12), LocalTime.of(9, 30),LocalTime.of(16, 0));
        t2 = new Termin("test termin2", LocalDate.of(2021,4,14), LocalTime.of(14, 0),LocalTime.of(16, 0));
    }
    /**
     * Test of initialisieren method, of class TerminVerwaltung.
     */
    @Test
    public void testInitialisieren() {
    }

    /**
     * Test of getTermineTag method, of class TerminVerwaltung.
     */
    @Test
    public void testGetTermineTag() {
    }

    /**
     * Test of addTermin method, of class TerminVerwaltung.
     */
    @Test
    public void testAddTermin() throws Exception {
        tv.addTermin(t);
        tv.addTermin(t2);
        assertEquals(tv.getAllTermine().length,2);
    }

    /**
     * Test of updateTermin method, of class TerminVerwaltung.
     */
    @Test()
    public void testUpdateTerminPositive() throws Exception {
        // positive test check if description is changed in tv object
        tv.addTermin(t);
        tv.addTermin(t2);
        Termin t3 = t2.getCopy();
        String newdescr = "this is a new description";
        t3.setText(newdescr);
        tv.updateTermin(t3);
        String description = tv.getTermineTag(t2.getDatum()).get(0).getText();
        assertEquals(description,newdescr);
    }
    
    @Test(expected=TerminUeberschneidungException.class)
    public void testUpdateTerminNegative() throws TerminUeberschneidungException,UngueltigerTerminException {
        // negative test to see if exception is thrown
        tv.addTermin(t);
        tv.addTermin(t2);
        Termin t4 = t2.getCopy();
        t4.setDatum(LocalDate.of(2021,4,12));
        t4.setVonBis(LocalTime.of(9, 30), LocalTime.of(16, 0));
        tv.updateTermin(t4);  
        
    }

    /**
     * Test of removeTermin method, of class TerminVerwaltung.
     */
    @Test
    public void testRemoveTermin() throws Exception{
        tv.addTermin(t);
        tv.addTermin(t2);
        tv.removeTermin(t);
        assertEquals(tv.getAllTermine().length,1);
    }

    /**
     * Test of checkTerminUeberschneidung method, of class TerminVerwaltung.
     */
    @Test
    public void testCheckTerminUeberschneidung() throws UngueltigerTerminException {
        tv.addTermin(t);
        tv.addTermin(t2);
        Termin t3 = new Termin("dieser Termin überschneidet sich mit t2", LocalDate.of(2021,4,14), LocalTime.of(14, 0),LocalTime.of(16, 0));
        assertNotNull(tv.checkTerminUeberschneidung(t3));
    }

    /**
     * Test of getTermin method, of class TerminVerwaltung.
     */
    @Test
    public void testGetTermin() {
    }

    /**
     * Test of getAllTermine method, of class TerminVerwaltung.
     */
    @Test
    public void testGetAllTermine() {
    }
    
}
