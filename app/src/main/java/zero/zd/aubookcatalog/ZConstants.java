package zero.zd.aubookcatalog;


public class ZConstants {

    static ZConstants instance = new ZConstants();

    private String server;

    static final String SERVER_IP = "10.0.0.3";
    static final String DB_FAIL = "fail";
    static final String DB_ENCODE_TYPE="UTF-8";

    // preferences
    static final String PREFS = "settings";
    static final String IS_LOGGED = "isLogged";
    static final String ALL_BOOKS_RESULT = "all_books_result";

    // nav
    static final int NAV_DASHBOARD = 0;
    static final int NAV_ALL_BOOKS = 1;
    static final int NAV_READ_BOOK= 2;
    static final int NAV_DISCOVER_BOOK = 3;
    static final int NAV_FAVORITES = 4;



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
