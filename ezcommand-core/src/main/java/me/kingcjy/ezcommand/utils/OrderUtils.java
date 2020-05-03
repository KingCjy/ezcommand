package me.kingcjy.ezcommand.utils;

import me.kingcjy.ezcommand.annotations.Order;

public class OrderUtils {

    public static Integer getOrder(Class<?> type) {
        Order order = type.getDeclaredAnnotation(Order.class);

        if(order == null) {
            return 1;
        }

        return order.value();
    }
}


