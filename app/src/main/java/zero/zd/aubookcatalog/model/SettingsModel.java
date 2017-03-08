package zero.zd.aubookcatalog.model;

public class SettingsModel {

    public static final int HEADING = 0;
    public static final int CONTENT = 1;

    private int mId;

    private String mHeading;

    private int mImgResource;
    private String mTitle;
    private String mSubtitle;

    public SettingsModel(int id, String heading) {
        this.mId = id;
        this.mHeading = heading;
    }

    public SettingsModel(int id, int imgResource, String title, String subtitle) {
        this.mId = id;
        this.mImgResource = imgResource;
        this.mTitle = title;
        this.mSubtitle = subtitle;
    }

    public int getImgResource() {
        return mImgResource;
    }

    public void setImgResource(int imgResource) {
        this.mImgResource = imgResource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        this.mSubtitle = subtitle;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getHeading() {
        return mHeading;
    }

    public void setHeading(String heading) {
        this.mHeading = heading;
    }

}
