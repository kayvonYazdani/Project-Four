package edu.rutgers.ruburger;

/**
 * Maintains the current order and shared order manager
 * while the RU Burger application is running.
 *
 * @author Kayvon Yazdani
 */
public final class AppSession {

    private static final OrderManager orderManager =
            new OrderManager();
    private static Order currentOrder =
            orderManager.createOrder();

    /**
     * Prevents creation of AppSession objects.
     */
    private AppSession() {}

    /**
     * Returns the current unfinished order.
     *
     * @return current order
     */
    public static Order getCurrentOrder() {
        return currentOrder;
    }


    /**
     * Returns the shared order manager.
     *
     * @return order manager
     */
    public static OrderManager getOrderManager() {
        return orderManager;
    }

    /**
     * Finalizes the current order and starts a new one.
     */
    public static void placeCurrentOrder() {
        orderManager.placeOrder(currentOrder);
        currentOrder = orderManager.createOrder();
    }
}
