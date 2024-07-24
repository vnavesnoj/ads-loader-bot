package vnavesnoj.ads_loader_bot_service.exception;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public class PatternCastException extends RuntimeException {
    public PatternCastException(String s) {
        super(s);
    }

    public PatternCastException(Throwable e) {
        super(e);
    }
}
