package zero.zd.aubookcatalog.model;

public class UserModel {

    private String mStudentId;
    private String mUsername;
    private String mFullname;

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        this.mStudentId = studentId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setFullname(String fullname) {
        this.mFullname = fullname;
    }
}
