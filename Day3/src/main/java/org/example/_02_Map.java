package org.example;


import java.util.*;

public class _02_Map {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        //  put(K key, V value)
        map.put("A", 1);
        map.put("B", 2);

        //  get(Object key)
        System.out.println(map.get("A")); // 1

        //  containsKey(Object key)
        System.out.println(map.containsKey("B")); // true

        //  containsValue(Object value)
        System.out.println(map.containsValue(2)); // true

        //  remove(Object key)
        map.remove("A");

        //  putIfAbsent(K key, V value)
        map.putIfAbsent("C", 3);

        //  getOrDefault(Object key, V defaultValue)
        System.out.println(map.getOrDefault("D", 0)); // 0

        //  compute(K key, BiFunction)
        map.compute("C", (k, v) -> v + 1);

        //  computeIfAbsent(K key, Function)
        map.computeIfAbsent("D", k -> 4);

        //  computeIfPresent(K key, BiFunction)
        map.computeIfPresent("D", (k, v) -> v + 1);

        //  merge(K key, V value, BiFunction)
        map.merge("E", 5, (oldVal, newVal) -> oldVal + newVal);

        //  keySet()
        Set<String> keys = map.keySet();

        //  values()
        Collection<Integer> values = map.values();

        //  entrySet()
        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        //  size()
        System.out.println(map.size());

        //  isEmpty()
        System.out.println(map.isEmpty());

        //  clear()
        map.clear();

        //  putAll(Map<? extends K,? extends V> m)
        Map<String, Integer> anotherMap = new HashMap<>();
        anotherMap.put("X", 10);
        anotherMap.put("Y", 20);
        map.putAll(anotherMap);

        //  replace(K key, V value)
        map.replace("X", 100);

        //  replace(K key, V oldValue, V newValue)
        map.replace("Y", 20, 200);

        //  replaceAll
        map.replaceAll((k, v) -> v * 2);

        //  remove(Object key, Object value)
        map.remove("X", 100);
    }
}
