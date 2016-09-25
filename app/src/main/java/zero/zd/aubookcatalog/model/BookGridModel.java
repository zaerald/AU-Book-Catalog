package zero.zd.aubookcatalog.model;

import android.util.Log;

import zero.zd.aubookcatalog.ZConstants;

public class BookGridModel {

    private long bookId;
    private String bookImage;
    private String bookTitle;
    private String bookType;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {

        String bookPath = "/book_img/";
        String bookLoc = ZConstants.getInstance().getServer() + bookPath + bookImage;
        Log.i("NFO", bookLoc);
        this.bookImage = bookLoc;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
}
