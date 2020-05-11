package me.kingcjy.ezcommand.utils;

import me.kingcjy.ezcommand.annotations.Order;

import java.util.Collection;
import java.util.List;

public class OrderUtils {

    public static Integer getOrder(Class<?> type) {
        Order order = type.getDeclaredAnnotation(Order.class);

        if(order == null) {
            return 1;
        }

        return order.value();
    }

    public static void sort(Collection<Object> list) {

    }

    public static Object getHighest(Collection<Object> collection) {
        OrderClass orderClass = null;
        for (Object o : collection) {

            if(orderClass == null) {
                orderClass = new OrderClass(o);
            }

            OrderClass temp = new OrderClass(o);

            if(temp.isHigherThan(orderClass)) {
                orderClass = temp;
            }
        }

        return orderClass.object;
    }

    static class OrderClass {
        private Object object;
        private Integer order;

        public OrderClass(Object object) {
            this.object = object;
            Order o = object.getClass().getAnnotation(Order.class);
            order = o == null ? 1 : o.value();
        }

        public boolean isHigherThan(OrderClass otherClass) {
            return order >= otherClass.order;
        }
    }
}


