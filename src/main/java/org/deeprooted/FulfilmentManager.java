package org.deeprooted;

import java.util.*;

public class FulfilmentManager {
    HashMap<String, TreeSet<Order>> pendingDemandOrdersMap = new HashMap<>();
    HashMap<String, SupplyManager> supplyManagerMap = new HashMap<>();

    public void placeOrder(Order order) {
        SupplyManager supplyManager = supplyManagerMap
                .computeIfAbsent(order.itemName, k -> new SupplyManager());
        TreeSet<Order> pendingDemandOrders = pendingDemandOrdersMap
                .computeIfAbsent(order.itemName, k -> new TreeSet<>((a, b) -> {
            if(a.pricePerUnit.equals(b.pricePerUnit)) {
                return a.orderId.compareTo(b.orderId);
            }
            return b.pricePerUnit.compareTo(a.pricePerUnit);
        }));

        if(order.isDemandOrder()) {
            pendingDemandOrders.add(order);
        }
        else {
            supplyManager.addSupplyOrder(order);
        }
        pendingDemandOrders.removeIf(supplyManager::fulfilDemand);
    }
}
