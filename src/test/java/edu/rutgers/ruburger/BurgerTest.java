
package edu.rutgers.ruburger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the price calculations for burgers.
 *
 * @author Kayvon Yazdani
 */
public class BurgerTest {

    /**
     * Tests a single-patty burger with lettuce and cheese.
     */
    @Test
    public void testSinglePattyPrice() {
        Burger burger = new Burger(Bread.BRIOCHE, false);
        burger.addAddOn(AddOns.LETTUCE);
        burger.addAddOn(AddOns.CHEESE);
        assertEquals(8.29, burger.price(), 0.001);
    }

    /**
     * Tests a double-patty burger with avocado and onions.
     */
    @Test
    public void testDoublePattyPrice() {
        Burger burger = new Burger(Bread.PRETZEL, true);
        burger.addAddOn(AddOns.AVOCADO);
        burger.addAddOn(AddOns.ONIONS);
        assertEquals(10.29, burger.price(), 0.001);

    }
}
