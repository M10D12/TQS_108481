/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;
    private BoundedSetOfNaturals setE;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD=new BoundedSetOfNaturals(2);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC =setD=setE= null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(11), "");
        assertFalse(setB.contains(11), "add: added element not found in set.");
        assertEquals(6, setB.size(), "add: elements count not as expected.");


        setD.add(9);
        assertThrows(IllegalArgumentException.class,() -> setD.add(9),"Duplicate value");
        assertThrows(IllegalArgumentException.class, ()-> setD.add(-3),"not a natural number");
    }

    @Disabled
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testEquals() {
        assertFalse(setB.equals(setC), "equals: different sets are equal");

        setA.add(99);
        assertTrue(setA.equals(setA), "equals: equal sets are not equal");

        assertFalse(setA.equals(setE), "equals: null set is equal to a non-null set");

        int n = 10;
        assertFalse(setA.equals(n), "equals: objects from different classes are equals");
    }

    @Test
    public void testIntersects(){
        assertTrue(setB.intersects(setC),"intersects");
        assertFalse(setB.intersects(setA),"not intersect");
    }


}
