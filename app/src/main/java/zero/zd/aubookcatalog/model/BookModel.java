package zero.zd.aubookcatalog.model;

import zero.zd.aubookcatalog.ZHelper;

public class BookModel {

    private long mBookId;
    private String mBookTitle;
    private String mBookImage;
    private String mAuthor;
    private String mSubject;
    private int mPages;
    private String mDivision;
    private String mType;
    private int mAvailable;
    private int mTotal;
    private String mDescription;
    private String mPdf;


    public long getBookId() {
        return mBookId;
    }

    public void setBookId(long bookId) {
        this.mBookId = bookId;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.mBookTitle = bookTitle;
    }

    public String getBookImage() {
        return mBookImage;
    }

    public void setBookImage(String bookImage) {
        String bookPath = "book_img/";
        this.mBookImage = ZHelper.getInstance().getServer() + bookPath + bookImage;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        this.mSubject = subject;
    }

    public int getPages() {
        return mPages;
    }

    public void setPages(int pages) {
        this.mPages = pages;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public int getAvailable() {
        return mAvailable;
    }

    public void setAvailable(int available) {
        this.mAvailable = available;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        this.mTotal = total;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getPdf() {
        return mPdf;
    }

    public void setPdf(String pdf) {
        String pdfPath = "mPdf/";
        this.mPdf = ZHelper.getInstance().getServer() + pdfPath + pdf;
    }

    public String getDivision() {
        return mDivision;
    }

    public void setDivision(String division) {
        this.mDivision = division;
    }
}
