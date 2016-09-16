package zero.zd.aubookcatalog;


public class ZConstants {

    private static ZConstants ourInstance = new ZConstants();

    //public static final String SERVER_IP = "192.168.22.12";
    public static final String SERVER_IP = "192.168.122.12";
    public static final String DB_SUCCESS = "success";
    public static final String DB_FAIL = "fail";
    public static final String DB_ENCODE_TYPE="UTF-8";

    private String serverIp;

    private ZConstants() {
        serverIp = "10.10.0.3";
    }

    public static ZConstants getInstance() {
        return ourInstance;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerIp() {
        return serverIp;
    }

}
