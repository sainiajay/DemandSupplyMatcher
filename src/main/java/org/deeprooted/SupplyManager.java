package org.deeprooted;

import java.util.*;

public class SupplyManager {
    Map<String, Order> supplyMap = new HashMap<>();
    TreeSet<Order> priceOrderedSupply
            = new TreeSet<>(Comparator.comparingDouble(order -> order.pricePerUnit));

    public void addSupplyOrder(Order order) {
        supplyMap.put(order.orderId, order);
        priceOrderedSupply.add(order);
    }

    public boolean fulfilDemand(final Order demandOrder) {
        if(priceOrderedSupply.floor(demandOrder) == null) {
            return false;
        }
        double requiredUnits = demandOrder.units;
        while (!priceOrderedSupply.isEmpty() && requiredUnits > 0.0) {
            Order supplyOrder = priceOrderedSupply.first();
            if(supplyOrder.pricePerUnit > demandOrder.pricePerUnit) {
                break;
            }
            Double suppliedUnits = Math.min(requiredUnits, supplyOrder.units);
            if(requiredUnits < supplyOrder.units) {
                supplyOrder.units -= requiredUnits;
            }
            else {
                priceOrderedSupply.pollFirst();
            }
            requiredUnits -= suppliedUnits;
            System.out.println(
                    String.format("%s %s %.0f/%s %.0f%s",
                            demandOrder.orderId, supplyOrder.orderId,
                            supplyOrder.pricePerUnit, supplyOrder.unitName,
                            suppliedUnits, supplyOrder.unitName)
            );
        }
        if(requiredUnits > 0.0) {
            demandOrder.units = requiredUnits;
            return false;
        }
        return true;
    }
}
