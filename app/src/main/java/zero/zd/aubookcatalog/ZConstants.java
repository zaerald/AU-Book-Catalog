package zero.zd.aubookcatalog;


public class ZConstants {

    public static ZConstants instance = new ZConstants();

    private String server;

    public static final String SERVER_IP = "10.0.0.3";
    public static final String DB_FAIL = "fail";
    public static final String DB_ENCODE_TYPE="UTF-8";

    //preferences
    public static final String SETTINGS = "settings";
    public static final String IS_LOGGED = "isLogged";

    // DB
    public static final String DB_STUDENT_ID = "student_id";

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
