package srp.config;

public class Paths {
    public static final String INDEX = "/";
    public static final String CUSTOMERS = "/customers";
    public static final String ORDERS = "/orders";
    public static final String PRODUCTS = "/products";

    public static final String ID_PARAM = ":id";

    public static String formatPostLocation(String id) {
        return String.format("%s/%s", CUSTOMERS, id);
    }
    public static String formatOrdersPostLocation(String id) {
        return String.format("%s/%s", ORDERS, id);
    }
    public static String formatProductsPostLocation(String id) {
        return String.format("%s/%s", PRODUCTS, id);
    }
}