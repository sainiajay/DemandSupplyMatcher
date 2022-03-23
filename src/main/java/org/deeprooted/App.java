package org.deeprooted;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Console App: The main entrypoint.
 */
public class App 
{
    public static void main(String[] args ) {
        run(System.in);
    }

    public static void run(InputStream inputStream) {
        Scanner inputScanner = new Scanner(inputStream);
        FulfilmentManager manager = new FulfilmentManager();
        while(inputScanner.hasNext()) {
            Order order = Order.newOrder(inputScanner.next())
                    .timestamp(inputScanner.next())
                    .itemName(inputScanner.next())
                    .price(inputScanner.next())
                    .units(inputScanner.next());

            manager.placeOrder(order);
        }
    }
}
