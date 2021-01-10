package net.astrocube.api.core.utils;

import com.google.common.collect.*;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author Jan-Willem Gmelig Meyling
 * Taken from https://gist.github.com/jwgmeligmeyling/051fcb5d76ad01eb652f
 */
public final class MultimapCollector<T, A> implements Collector<T, A, A> {

    private static final Set<Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, A> finisher;
    private final Set<Characteristics> characteristics = CH_ID;

    MultimapCollector(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner) {
        this(supplier, accumulator, combiner, Function.identity());
    }

    MultimapCollector(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, A> finisher) {
        this.supplier = supplier;
        this.accumulator = accumulator;
        this.combiner = combiner;
        this.finisher = finisher;
    }

    public static <T, K, U, M extends Multimap<K, U>>
    MultimapCollector<T, M> toMultiMap(Function<? super T, ? extends K> keyMapper,
                                       Function<? super T, ? extends U> valueMapper,
                                       Supplier<M> mapSupplier) {
        BiConsumer<M, T> accumulator = (map, element) -> map.put(keyMapper.apply(element), valueMapper.apply(element));
        return new MultimapCollector<>(mapSupplier, accumulator,
                (kvMultimap, kvMultimap2) -> {
                    kvMultimap.putAll(kvMultimap2);
                    return kvMultimap;
                });
    }

    public static <T, K, U>
    MultimapCollector<T, HashMultimap<K, U>> toHashMultiMap(Function<? super T, ? extends K> keyMapper,
                                                            Function<? super T, ? extends U> valueMapper) {
        return toMultiMap(keyMapper, valueMapper, HashMultimap::create);
    }

    public static <T, K, U>
    MultimapCollector<T, LinkedHashMultimap<K, U>> toLinkedHashMultiMap(Function<? super T, ? extends K> keyMapper,
                                                                        Function<? super T, ? extends U> valueMapper) {
        return toMultiMap(keyMapper, valueMapper, LinkedHashMultimap::create);
    }

    public static <T, K, U>
    MultimapCollector<T, ArrayListMultimap<K, U>> toArrayListMultimap(Function<? super T, ? extends K> keyMapper,
                                                                      Function<? super T, ? extends U> valueMapper) {
        return toMultiMap(keyMapper, valueMapper, ArrayListMultimap::create);
    }

    public static <T, K extends Comparable<K>, U extends Comparable<U>>
    MultimapCollector<T, TreeMultimap<K, U>> toTreeMultiMap(Function<? super T, ? extends K> keyMapper,
                                                            Function<? super T, ? extends U> valueMapper) {
        return toMultiMap(keyMapper, valueMapper, TreeMultimap::create);
    }

    public Supplier<A> supplier() {
        return this.supplier;
    }

    public BiConsumer<A, T> accumulator() {
        return this.accumulator;
    }

    public BinaryOperator<A> combiner() {
        return this.combiner;
    }

    public Function<A, A> finisher() {
        return this.finisher;
    }

    public Set<Characteristics> characteristics() {
        return this.characteristics;
    }

    public String toString() {
        return "MultimapCollector(supplier=" + this.supplier() + ", accumulator=" + this.accumulator() + ", combiner=" + this.combiner() + ", finisher=" + this.finisher() + ", characteristics=" + this.characteristics() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultimapCollector)) return false;
        MultimapCollector<?, ?> that = (MultimapCollector<?, ?>) o;
        return Objects.equals(supplier, that.supplier) &&
                Objects.equals(accumulator, that.accumulator) &&
                Objects.equals(combiner, that.combiner) &&
                Objects.equals(finisher, that.finisher) &&
                Objects.equals(characteristics, that.characteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, accumulator, combiner, finisher, characteristics);
    }
}