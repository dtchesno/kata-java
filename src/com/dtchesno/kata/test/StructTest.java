package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.PQueue;
import com.dtchesno.kata.struct.Trie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;


public class StructTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTrie() {
        Trie t = Trie.build(Arrays.asList("abc", "acd", "bcd", "def", "a", "aba"));
        assertEquals(
                (new HashSet<String>()).addAll(Arrays.asList("abc", "acd", "a", "aba")),
                (new HashSet<String>()).addAll(t.getValues("a")));
        assertEquals(
                (new HashSet<String>()).addAll(Arrays.asList("bcd")),
                (new HashSet<String>()).addAll(t.getValues("b")));
    }

    @Test
    public void testPQueue() {
        PQueue<Integer> q = new PQueue<>((a, b) -> (a - b));
        q.add(4);
        q.add(2);
        q.add(6);
        q.add(1);
        q.add(5);
        q.add(3);

        assertEquals(1, (int) q.poll());
        assertEquals(2, (int) q.poll());
        assertEquals(3, (int) q.poll());
        assertEquals(4, (int) q.poll());
        assertEquals(5, (int) q.poll());
        assertEquals(6, (int) q.poll());
        assertEquals(0, q.size());
    }
}
