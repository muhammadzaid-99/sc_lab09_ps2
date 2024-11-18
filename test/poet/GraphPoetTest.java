/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.IOException;

import graph.Graph;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   constructor
    //     corpus: exists, doesn't exist
    //     words in contents: 0, 1, >1
    //     words contain duplicates or don't
    //   poem
    //     words in input: 0, 1, >1
    //     input word pairs in graph or not
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGraphPoetNotFound() {
        try {
            File corpus = new File("test/poet/xyz.txt");
            GraphPoet poet = new GraphPoet(corpus);
            assert false; // should be unreachable
        }
        catch (IOException e) {
            assert true;
        }
    }

    @Test
    public void testGraphPoetEmpty() {
        try {
            File corpus = new File("test/poet/empty.txt");
            GraphPoet poet = new GraphPoet(corpus);
            Graph<String> graph = poet.getGraph();
            assertEquals("empty graph expected",
                    Collections.emptySet(), graph.vertices());
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }

    @Test
    public void testGraphPoetSingle() {
        try {
            File corpus = new File("test/poet/single.txt");
            GraphPoet poet = new GraphPoet(corpus);
            Graph<String> graph = poet.getGraph();
            assertEquals("single vertex expected", Set.of("hello"), graph.vertices());
            assertEquals("no edges from vertex expected", Collections.emptyMap(), graph.targets("hello"));
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }

    @Test
    public void testGraphPoetMultiple() {
        try {
            File corpus = new File("test/poet/multiple.txt");
            GraphPoet poet = new GraphPoet(corpus);
            Graph<String> graph = poet.getGraph();
            assertEquals("2 vertices expected", Set.of("hello,", "goodbye!"), graph.vertices());
            assertEquals("expected edges from 'hello,' ", Map.of("hello,", 2, "goodbye!", 1), graph.targets("hello,"));
            assertEquals("no edges from 'goodbye!' expected", Collections.emptyMap(), graph.targets("goodbye!"));
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }

    @Test 
    public void testGraphPoetPoemEmpty() {
        try {
            File corpus = new File("test/poet/phrases.txt");
            GraphPoet poet = new GraphPoet(corpus);
            String input = "";
            assertEquals("empty poem expect", input, poet.poem(input));
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }

    @Test
    public void testGraphPoetPoemSingle() {
        try {
            File corpus = new File("test/poet/phrases.txt");
            GraphPoet poet = new GraphPoet(corpus);
            String input = "hello";
            assertEquals("expect identical poem", input, poet.poem(input));
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }

    @Test
    public void testGraphPoetPoemWithBridgeWords() {
        try {
            File corpus = new File("test/poet/phrases.txt");
            GraphPoet poet = new GraphPoet(corpus);
            
            // bridge word is quickly
            String input1 = "The quick brown fox ran away from the ocean.";
            assertEquals("expected poem", "The quick brown fox ran quickly away from the ocean.", poet.poem(input1));
            
            // 3 bridge words: fast, jumped, lazy
            String input2 = "I saw a red fox over the dog.";
            assertEquals("expected poem", "I saw a fast red fox jumped over the lazy dog.", poet.poem(input2));
            
            // no bridge word
            String input3 = "The quick dog ran.";
            assertEquals("expected poem", "The quick dog ran.", poet.poem(input3));
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }


    @Test
    public void testGraphPoetToString() {
        try {
            File corpus = new File("test/poet/multiple.txt");
            GraphPoet poet = new GraphPoet(corpus);
            assertEquals("expected graph with multiple edges to string", "(hello, -> hello,, 2)\n(hello, -> goodbye!, 1)", poet.toString());
        }
        catch (IOException e) {
            assert false; // should be unreachable
        }
    }
    
    

}