package vnavesnoj.ads_loader_bot_service.service.component;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface PatternAnalyzer<T> extends Analyzer {

    boolean valid(String pattern);

    T mapToObject(String pattern);
}
