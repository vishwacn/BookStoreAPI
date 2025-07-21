package tests;

import java.util.List;

public class BookCollectionRequest {
    private String userId;
    private List<Book> collectionOfIsbns;

    public BookCollectionRequest() {}

    public BookCollectionRequest(String userId, List<Book> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Book> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<Book> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}
