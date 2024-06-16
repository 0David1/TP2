package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TrieTest {
    @Test
    void testingTrie(){
        Trie<Integer> trie = new Trie<>();
        trie.definir("hello", 1);
        trie.definir("world", 2);

        assertEquals(2, trie.tamaño());
        assertEquals(1, trie.definicion("hello"));
        assertEquals(2, trie.definicion("world"));
        assertEquals(null, trie.definicion("byebye"));

        trie.borrar("hello");

        assertEquals(null, trie.definicion("hello"));
        assertEquals(1, trie.tamaño());

        trie.borrar("cas");

        assertEquals(1, trie.tamaño());

        trie.borrar("world");
        
        assertEquals(0, trie.tamaño());
    }

}
