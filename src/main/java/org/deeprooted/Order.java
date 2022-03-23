package org.deeprooted;


public class Order {
    enum OrderType {
        DEMAND,
        SUPPLY
    }

    OrderType type;
    String orderId;
    String itemName;
    Double pricePerUnit;
    Double units;
    String unitName;
    Integer timestamp;

    /**
     * Factory methods
     */
    public static Order newOrder(String orderId) {
        if(orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order Id cannot be null or empty.");
        }
        if(orderId.startsWith("s")) {
            Order order =  new Order();
            order.type = OrderType.SUPPLY;
            order.orderId = orderId;
            return order;
        }
        if(orderId.startsWith("d")) {
            Order order =  new Order();
            order.type = OrderType.DEMAND;
            order.orderId = orderId;
            return order;
        }
        throw new IllegalArgumentException("Order Id has to start with 's', or 'd' to indicate supply, or demand order.");
    }

    public Order timestamp(String timestamp) {
        if(timestamp == null || timestamp.isEmpty()) {
            throw new IllegalArgumentException("Timestamp cannot be null or empty.");
        }
        String[] parts = timestamp.split(":");
        this.timestamp = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        return this;
    }

    public Order itemName(String itemName) {
        if(itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        this.itemName = itemName;
        return this;
    }

    public Order price(String priceDescription) {
        if(priceDescription == null || priceDescription.isEmpty()) {
            throw new IllegalArgumentException("Price cannot be null or empty.");
        }
        String[] tokens = priceDescription.split("/");
        if(tokens.length != 2) {
            throw new IllegalArgumentException("Price description should be in format: <pricePerUnit>/<unitName>.");
        }

        this.pricePerUnit = Double.parseDouble(tokens[0]);
        this.unitName = tokens[1];
        return this;
    }

    public Order units(String units) {
        if(units == null || units.isEmpty()) {
            throw new IllegalArgumentException("Units cannot be null or empty.");
        }
        if(this.unitName == null || this.unitName.isEmpty()) {
            throw new IllegalStateException("Unit should be set first");
        }
        if(!units.endsWith(this.unitName)) {
            throw new IllegalArgumentException("Units should be express in terms of same unit as mentioned in price.");
        }
        String strippedUnits = units.replace(this.unitName, "");
        this.units = Double.parseDouble(strippedUnits);
        return this;
    }

    public boolean isDemandOrder() {
        return this.type == OrderType.DEMAND;
    }

    @Override
    public String toString() {
        return "Order{" +
                "type=" + type +
                ", orderId='" + orderId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", units=" + units +
                ", unitName='" + unitName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
