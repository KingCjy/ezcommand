package me.kingcjy.ezcommand.utils;

import me.kingcjy.ezcommand.EzLogger;
import me.kingcjy.ezcommand.annotations.Component;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

@Component
public class EzReflectionUtilsTest {

    @Test
    public void 스캔_테스트() throws Exception {
        Set<Class> classes = EzReflectionUtils.findAnnotatedClasses(EzLogger.class.getPackage().getName(), Component.class);
        Assert.assertTrue(classes.contains(EzReflectionUtilsTest.class));
    }
}
