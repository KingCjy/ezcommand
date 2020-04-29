package me.kingcjy.ezcommand.command;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapReferenceTest {
    @Test
    public void MapReferenceTest() {
        Map<String, List<String>> testMap = new HashMap<>();

        List list = testMap.getOrDefault("test", new ArrayList<>());

        list.add("dodo");

        testMap.put("test", list);

        testMap.forEach((key, value) -> {
            System.out.println("key -> " + key);
            System.out.println("valye -> " + value);
        });
    }

    @Test
    public void splitTest() {
        String a = "message";

        System.out.println(a.split(":")[0]);
    }

    @Test
    public void a() {
        String[] str = test(String.class);

        System.out.println(str.length);
    }

    public <T> T[] test(Class<T> targetClass) {


        return (T[]) Array.newInstance(targetClass, 0);
    }

    class doa {
        public doa(String a) {

        }
    }
}
