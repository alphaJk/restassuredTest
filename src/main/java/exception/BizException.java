package exception;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2021-01-25
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class BizException extends RuntimeException{
    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Object...args) {
        super(String.format(message, args));
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    protected BizException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
