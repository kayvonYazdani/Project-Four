package edu.rutgers.ruburger;

/**
 * Represents a side item sold at RU Burger.
 * The price depends on the selected side, size,
 * and quantity.
 *
 * @author Kayvon Yazdani
 */
public class Side extends MenuItem {

    private Size size;
    private SideType type;

    /**
     * Constructs a side with the selected size and type.
     *
     * @param size the selected size
     * @param type the selected side type
     */
    public Side(Size size, SideType type) {
        if (size == null || type == null) {
            throw new IllegalArgumentException(
                    "Size and side type cannot be null.");
        }
        this.size = size;
        this.type = type;
        this.quantity = 1;

    }

    /**
     * Calculates the price of the side based on its
     * type, size, and quantity.
     *
     * @return the total price of the side
     */
    @Override
    public double price() {
        double total = switch (type) {
            case CHIPS -> 1.99;
            case FRIES -> 2.49;
            case ONION_RINGS -> 3.29;
            case APPLE_SLICES -> 1.29;
        };

        switch (size) {
            case MEDIUM -> total += 0.50;
            case LARGE -> total += 1.00;
            default -> {
            }
        }

        return total * quantity;

    }

    /**
     * Returns the selected side type.
     *
     * @return the side type
     */
    public SideType getType() {
        return type;
    }

    /**
     * Returns the selected side size.
     *
     * @return the side size
     */
    public Size getSize() {
        return size;
    }

}
