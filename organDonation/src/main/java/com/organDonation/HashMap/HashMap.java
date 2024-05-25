package com.organDonation.HashMap;

import java.util.LinkedList;

public class HashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<KeyValuePair<K, V>>[] buckets;
    private int capacity;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.size = 0;
        this.buckets = new LinkedList[initialCapacity];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<KeyValuePair<K, V>>[] newBuckets = new LinkedList[newCapacity];

        for (LinkedList<KeyValuePair<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (KeyValuePair<K, V> pair : bucket) {
                    int newIndex = Math.abs(pair.getKey().hashCode() % newCapacity);
                    if (newBuckets[newIndex] == null) {
                        newBuckets[newIndex] = new LinkedList<>();
                    }
                    newBuckets[newIndex].add(pair);
                }
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
    }

    public void put(K key, V value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int bucketIndex = hash(key);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new LinkedList<>();
        }

        LinkedList<KeyValuePair<K, V>> bucket = buckets[bucketIndex];
        for (KeyValuePair<K, V> pair : bucket) {
            if (pair.getKey().equals(key)) {
                pair.value = value;
                return;
            }
        }

        bucket.add(new KeyValuePair<>(key, value));
        size++;
    }

    public V get(K key) {
        int bucketIndex = hash(key);
        LinkedList<KeyValuePair<K, V>> bucket = buckets[bucketIndex];
        if (bucket != null) {
            for (KeyValuePair<K, V> pair : bucket) {
                if (pair.getKey().equals(key)) {
                    return pair.getValue();
                }
            }
        }
        return null;
    }

    public void remove(K key) {
        int bucketIndex = hash(key);
        LinkedList<KeyValuePair<K, V>> bucket = buckets[bucketIndex];
        if (bucket != null) {
            for (KeyValuePair<K, V> pair : bucket) {
                if (pair.getKey().equals(key)) {
                    bucket.remove(pair);
                    size--;
                    return;
                }
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
