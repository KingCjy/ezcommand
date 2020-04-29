package me.kingcjy.ezcommand;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    
    @Test
    public void RegexTest() {
        String regex = "message (?<player>[^\\s]*) (?<message>[^\\s]*)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher("message KingCjy message");

        Assert.assertTrue(matcher.find());
    }
}
