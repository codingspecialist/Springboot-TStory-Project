package site.metacoding.blogv3.handler.ex;

public class CustomApiException extends RuntimeException {
    public CustomApiException(String message) {
        super(message);
    }
}
