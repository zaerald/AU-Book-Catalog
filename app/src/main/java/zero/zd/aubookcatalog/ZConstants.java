package zero.zd.aubookcatalog;


public class ZConstants {

    public static ZConstants instance = new ZConstants();

    private String server;

    public static final String SERVER_IP = "10.0.0.3";
    public static final String DB_FAIL = "fail";
    public static final String DB_ENCODE_TYPE="UTF-8";

    // preferences
    public static final String SETTINGS = "settings";
    public static final String IS_LOGGED = "isLogged";
    public static final String ALL_BOOKS_RESULT = "all_books_result";

    // nav
    public static final int NAV_DASHBOARD = 0;
    public static final int NAV_ALL_BOOKS = 1;
    public static final int NAV_READ_BOOK= 2;
    public static final int NAV_DISCOVER_BOOK = 3;
    public static final int NAV_FAVORITES = 4;



    private ZConstants() {}

    public static ZConstants getInstance() {
        return instance;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

}
