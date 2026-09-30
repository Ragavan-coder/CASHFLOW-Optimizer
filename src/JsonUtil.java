public class JsonUtil {
    public static String escape(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\"", "\\\"") + "\"";
    }

    public static String toJson(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof String) return escape((String) obj);
        if (obj instanceof Number || obj instanceof Boolean) return obj.toString();
        
        return "{}"; 
    }
}
