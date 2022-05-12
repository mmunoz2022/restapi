package sopra.restapi.util;

@FunctionalInterface
public interface ThrowingConsumer<T> {

  void accept(T t) throws Exception;
}
