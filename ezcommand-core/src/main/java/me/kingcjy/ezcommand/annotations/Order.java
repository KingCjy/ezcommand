package me.kingcjy.ezcommand.annotations;

public @interface Order {
    int value() default 1;

    int LOW = 999;
}
