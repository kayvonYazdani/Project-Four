package edu.rutgers.ruburger;
import java.util.ArrayList;

/**
 * Manages the orders placed at RU Burger.
 * Generates order numbers and keeps track of
 * completed orders.
 *
 * @author Kayvon Yazdani
 */
public class OrderManager {

    private ArrayList<Order> orders;
    private int nextOrderNumber;

    /**
     * Constructs an order manager with an empty
     * list of placed orders.
     */
    public OrderManager() {
        this.orders = new ArrayList<>();
        this.nextOrderNumber = 1;
    }

    /**
     * Creates a new order with a unique number.
     *
     * @return the newly created order
     */
    public Order createOrder() {
        return new Order(nextOrderNumber++);
    }


    /**
     * Adds a completed order to the placed orders list.
     *
     * @param order the order to place
     */
    public void placeOrder(Order order) {
        if (order == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot place an empty order.");
        }

        for (Order existing : orders) {
            if (existing.getNumber() == order.getNumber()) {
                throw new IllegalArgumentException(
                        "Order number already exists.");
            }
        }
        orders.add(order);

    }


    /**
     * Cancels a placed order using its order number.
     *
     * @param number the order number to cancel
     * @return true if the order was canceled
     */
    public boolean cancelOrder(int number) {
        return orders.removeIf(
                order -> order.getNumber() == number);
    }

    /**
     * Returns a copy of all placed orders.
     *
     * @return list of placed orders
     */
    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

}
