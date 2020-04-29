package me.kingcjy.ezcommand.context;

import me.kingcjy.ezcommand.beans.factory.BeanFactory;

import java.time.LocalDateTime;

public interface ApplicationContext extends BeanFactory {
    String getServerName();
    String getServerVersion();
    LocalDateTime getStartDateTime();
}
