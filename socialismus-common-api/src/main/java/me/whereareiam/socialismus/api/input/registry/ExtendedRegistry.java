package me.whereareiam.socialismus.api.input.registry;

import java.util.Map;

public interface ExtendedRegistry<A, B> {
    void register(A a, B b);

    Map<A, B> getRegistry();

    B get(A a);
}