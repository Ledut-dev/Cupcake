package app.util;

public class StringCapitalizer {
    public String capitalizeFirstOnly(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
