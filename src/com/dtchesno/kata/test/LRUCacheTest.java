package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.LRUCache;
import com.dtchesno.kata.struct.LRUCacheSimplified;
import org.junit.Test;
import static org.junit.Assert.*;

public class LRUCacheTest {
    @Test
    public void Test1() {
        // ["LRUCache","put","put","get","put","get","put","get","get","get"]
        // [[2],[1,0],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
        // [null,null,null,0,null,-1,null,-1,3,4]
        LRUCache cache = new LRUCache(2);
        cache.put(1, 0);
        cache.put(2, 2);
        assertEquals(0, cache.get(1));
        cache.put(3, 3);
        assertEquals(-1, cache.get(2));
        cache.put(4, 4);
        assertEquals(-1, cache.get(1));
        assertEquals(3, cache.get(3));
        assertEquals(4, cache.get(4));
    }

    @Test
    public void Test2() {
        // ["LRUCache","get","put","get","put","put","get","get"]
        // [[2],[2],[2,6],[1],[1,5],[1,2],[1],[2]]
        // [null,-1,null,-1,null,null,2,6]
        LRUCache cache = new LRUCache(2);
        assertEquals(-1, cache.get(2));
        cache.put(2, 6);
        assertEquals(-1, cache.get(1));
        cache.put(1, 5);
        cache.put(1, 2);
        assertEquals(2, cache.get(1));
        assertEquals(6, cache.get(2));
    }

    @Test
    public void Test3() {
        // ["LRUCache","put","put","get","put","get","put","get","get","get"]
        // [[2],[1,0],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
        // [null,null,null,0,null,-1,null,-1,3,4]
        LRUCache cache = new LRUCache(2);
        cache.put(1, 0);
        cache.put(2, 2);
        assertEquals(0, cache.get(1));
        cache.put(3, 3);
        assertEquals(-1, cache.get(2));
        cache.put(4, 4);
        assertEquals(-1, cache.get(1));
        assertEquals(3, cache.get(3));
        assertEquals(4, cache.get(4));
    }

    @Test
    public void TestCacheSize() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 101);
        cache.put(2, 202);
        cache.put(2, 202);
        cache.put(2, 203);
        assertEquals(101, cache.get(1));
        assertEquals(2, cache.size());
    }
}