package mvp.deplog.global.security.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException(String msg) {
        super(msg);
    }
}
