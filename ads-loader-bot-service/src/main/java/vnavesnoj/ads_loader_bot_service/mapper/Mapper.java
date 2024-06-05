package vnavesnoj.ads_loader_bot_service.mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface Mapper<F, T> {

    T map(F object);

    default T map(F from, T to) {
        return to;
    }
}
