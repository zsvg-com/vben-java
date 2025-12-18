package vben.common.core.config.builder;

/**
 * 从org.apache.common.lang3 拷贝
 * @param <T>
 */
@FunctionalInterface
public interface Builder<T> {
    T build();
}
