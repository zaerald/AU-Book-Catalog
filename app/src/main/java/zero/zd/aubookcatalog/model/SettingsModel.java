package zero.zd.aubookcatalog.model;

public class SettingsModel {

    public static final int HEADING = 0;
    public static final int CONTENT = 1;

    private int id;

    private String heading;

    private int imgResource;
    private String title;
    private String subtitle;

    public SettingsModel(int id, String heading) {
        this.id = id;
        this.heading = heading;
    }

    public SettingsModel(int id, int imgResource, String title, String subtitle) {
        this.id = id;
        this.imgResource = imgResource;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}
