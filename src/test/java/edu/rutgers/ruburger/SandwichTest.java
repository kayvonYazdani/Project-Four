
package edu.rutgers.ruburger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the price calculations for sandwiches.
 *
 * @author Kayvon Yazdani
 */
public class SandwichTest {

    /**
     * Tests a roast beef sandwich with cheese.
     */
    @Test
    public void testRoastBeefPrice() {
        Sandwich sandwich = new Sandwich(
                Bread.BRIOCHE, Protein.ROAST_BEEF);
        sandwich.addAddOn(AddOns.CHEESE);
        assertEquals(11.99, sandwich.price(), 0.001);

    }

    /**
     * Tests a salmon sandwich with avocado and lettuce.
     */
    @Test
    public void testSalmonPrice() {
        Sandwich sandwich = new Sandwich(
                Bread.WHEAT, Protein.SALMON);
        sandwich.addAddOn(AddOns.AVOCADO);
        sandwich.addAddOn(AddOns.LETTUCE);
        assertEquals(10.79, sandwich.price(), 0.001);
    }

    /**
     * Tests a chicken sandwich with tomatoes and onions.
     */
    @Test
    public void testChickenPrice() {
        Sandwich sandwich = new Sandwich(
                Bread.SOURDOUGH, Protein.CHICKEN);
        sandwich.addAddOn(AddOns.TOMATOES);
        sandwich.addAddOn(AddOns.ONIONS);
        assertEquals(9.59, sandwich.price(), 0.001);
    }

}
