package br.com.threewings.wingsblog.utils;

public class SlugConversions {
    public static String convert(String title) {
        return title.toLowerCase().replaceAll(" ", "-");
    }
}
