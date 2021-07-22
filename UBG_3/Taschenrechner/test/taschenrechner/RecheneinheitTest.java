/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taschenrechner;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author vicol
 */
public class RecheneinheitTest {
    
    private Recheneinheit recheneinheit;
    
    public RecheneinheitTest() {
    }

    @Before
    public void setUp() {
        recheneinheit = new Recheneinheit();
    }
    
    /**
     * Test of gibAnzeigewert method, of class Recheneinheit.
     */
    @Test
    public void testGibAnzeigewert() {
        assertEquals(recheneinheit.gibAnzeigewert(),0);
    }

    /**
     * Test of zifferGetippt method, of class Recheneinheit.
     */
    @Test
    public void testZifferGetippt() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(3);
        assertEquals(recheneinheit.gibAnzeigewert(),3);
    }

    @Test
    public void allesTesten()
    {
        testPlus();
        testMinus();
        testPlus();
    }
    
    /**
     * Test of plus method, of class Recheneinheit.
     */
    @Test
    public void testPlus() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(3);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(4);
        recheneinheit.gleich();
        assertEquals(recheneinheit.gibAnzeigewert(),7);
    }

    /**
     * Test of minus method, of class Recheneinheit.
     */
    @Test
    public void testMinus() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(9);
        recheneinheit.minus();
        recheneinheit.zifferGetippt(4);
        recheneinheit.gleich();
        assertEquals(recheneinheit.gibAnzeigewert(),5);
    }

    /**
     * Test of speicherEingabe and speicherAusgabe method, of class Recheneinheit.
     */
    @Test 
    public void testSpeicherEinundAusgabe() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(2);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(2);
        recheneinheit.gleich();
        recheneinheit.speicherEingabe();
        recheneinheit.clear();
        recheneinheit.speicherAusgabe();
        assertEquals(recheneinheit.gibAnzeigewert(),4);
    }
    
    /**
     * Test of speicherPlus method, of class Recheneinheit.
     */
    @Test
    public void testSpeicherPlus() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(2);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(2);
        recheneinheit.gleich();
        recheneinheit.speicherEingabe();
        recheneinheit.clear();
        recheneinheit.zifferGetippt(2);
        recheneinheit.speicherPlus();
        recheneinheit.speicherAusgabe();
        assertEquals(recheneinheit.gibAnzeigewert(),6);
    }
    
    /**
     * Test of speicherMinus method, of class Recheneinheit.
     */
    @Test
    public void testSpeicherMinus() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(2);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(2);
        recheneinheit.gleich();
        recheneinheit.speicherEingabe();
        recheneinheit.clear();
        recheneinheit.zifferGetippt(2);
        recheneinheit.speicherMinus();
        recheneinheit.speicherAusgabe();
        assertEquals(recheneinheit.gibAnzeigewert(),2);
    }
    
    /**
     * Test of speicherC method, of class Recheneinheit.
     */
    @Test
    public void testSpeicherC() {
        recheneinheit.clear();
        recheneinheit.zifferGetippt(2);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(2);
        recheneinheit.gleich();
        recheneinheit.speicherEingabe();
        recheneinheit.clear();
        recheneinheit.speicherC();
        recheneinheit.speicherAusgabe();
        assertEquals(recheneinheit.gibAnzeigewert(),0);
    }
    
    
    /**
     * Test of gleich method, of class Recheneinheit.
     */
    @Test
    public void testGleich() {
    }

    /**
     * Test of clear method, of class Recheneinheit.
     */
    @Test
    public void testClear() {
    }

    /**
     * Test of gibTitel method, of class Recheneinheit.
     */
    @Test
    public void testGibTitel() {
    }

    /**
     * Test of gibAutor method, of class Recheneinheit.
     */
    @Test
    public void testGibAutor() {
    }

    /**
     * Test of gibVersion method, of class Recheneinheit.
     */
    @Test
    public void testGibVersion() {
    }
    
}
