package com.dtchesno.kata.struct;

public interface ILRUCache {
    int get(int key);
    void put(int key, int value);
    int size();
}
