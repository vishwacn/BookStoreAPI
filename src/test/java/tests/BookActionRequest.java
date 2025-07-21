package tests;

public class BookActionRequest {
    private String userId;
    private String isbn;

    public BookActionRequest() {}

    public BookActionRequest(String userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    public String getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
