package zero.zd.aubookcatalog;


import java.io.File;

public class ZHelper {

    static final String SERVER_IP = "10.0.3.2";
    static final String DB_FAIL = "fail";
    static final String DB_ENCODE_TYPE = "UTF-8";
    // mPreferences
    static final String PREFS = "settings";
    static final String IS_LOGGED = "isLogged";
    static final String ALL_BOOKS_RESULT = "all_books_result";
    static final String FAV_BOOKS_RESULT = "fav_books_result";
    // nav
    static final int NAV_DASHBOARD = 0;
    static final int NAV_ALL_BOOKS = 1;
    static final int NAV_DISCOVER_BOOK = 2;
    static final int NAV_DOWNLOADED_PDF_BOOK = 3;
    static final int NAV_FAVORITES = 4;
    static final String NO_CONN_PROMPT = "Please make sure you are connected to the internet.";
    private static ZHelper sInstance = new ZHelper();
    private String mServer;
    private String mStudentId;
    private File mPdf;


    private ZHelper() {
    }

    public static ZHelper getInstance() {
        return sInstance;
    }

    public String getServer() {
        return mServer;
    }

    public void setServer(String server) {
        this.mServer = server;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        this.mStudentId = studentId;
    }

    public File getPdf() {
        return mPdf;
    }

    public void setPdf(File pdf) {
        this.mPdf = pdf;
    }
}
