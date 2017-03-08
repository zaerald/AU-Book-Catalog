package zero.zd.aubookcatalog.model;

import zero.zd.aubookcatalog.ZHelper;

public class BookGridModel {

    private long mBookId;
    private String mBookImage;
    private String mBookTitle;
    private String mAuthor;
    private String mBookType;

    public long getBookId() {
        return mBookId;
    }

    public void setBookId(long bookId) {
        this.mBookId = bookId;
    }

    public String getBookImage() {
        return mBookImage;
    }

    public void setBookImage(String bookImage) {
        String bookPath = "/book_img/";
        this.mBookImage = ZHelper.getInstance().getServer() + bookPath + bookImage;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.mBookTitle = bookTitle;
    }

    public String getBookType() {
        return mBookType;
    }

    public void setBookType(String bookType) {
        this.mBookType = bookType;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }
}
