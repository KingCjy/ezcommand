package me.kingcjy.ezcommand;

import java.util.logging.Logger;

public class EzLogger {

    public static Logger getLogger(Class<?> targetClass) {

        return Logger.getLogger(targetClass.getName());
    }
}
