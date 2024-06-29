package com.dtchesno.kata.test;

import com.dtchesno.kata.struct.AlienDictionary;
import com.dtchesno.kata.struct.FileSystem;
import com.dtchesno.kata.struct.PQueue;
import com.dtchesno.kata.struct.Trie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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

    @Test
    public void testFileSystem() {
        FileSystem fs = new FileSystem();
        assertEquals(new ArrayList<>(), fs.ls("/"));

        fs.mkdir("/a/b/c");
        fs.addContentToFile("/a/b/c/d", "hello");
        assertEquals(Arrays.asList("a"), fs.ls("/"));
        assertEquals(Arrays.asList("d"), fs.ls("/a/b/c"));
        assertEquals("hello", fs.readContentFromFile("/a/b/c/d"));

        fs.addContentToFile("/a/b/c/d", " world!");
        assertEquals("hello world!", fs.readContentFromFile("/a/b/c/d"));

        fs.mkdir("/a/b/c/e");
        assertEquals(Arrays.asList("d", "e"), fs.ls("/a/b/c"));
    }

    @Test
    public void testAlienDictionary() {
        AlienDictionary dict = new AlienDictionary();
        assertEquals("wertf", dict.alienOrder(new String[] {"wrt", "wrf", "er", "ett", "rftt"}));
        assertEquals("zx", dict.alienOrder(new String[] {"z", "x"}));
        assertEquals("", dict.alienOrder(new String[] {"z", "x", "z"}));
        assertEquals("", dict.alienOrder(new String[] {"abc", "ab"}));
        assertEquals("z", dict.alienOrder(new String[] {"z", "z"}));
    }
}
