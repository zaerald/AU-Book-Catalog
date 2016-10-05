package zero.zd.aubookcatalog.model;

public class SettingsModel {

    private int imgResource;
    private String title;
    private String subtitle;

    public SettingsModel(int imgResource, String title, String subtitle) {
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
}
