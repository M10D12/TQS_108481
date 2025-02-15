package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TqsStackTest {
    private TqsStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>();
    }

    @Test
    void testEmptyOnConstruction(){
        assertTrue(stack.isEmpty(), "Stack should be empty on construction");
    }

    @Test
    void testStackSizeZeroOnConstruction(){
        assertEquals(0, stack.size(), "Stack size should be 0 on construction");
    }

    @Test
    void testStackSizeOneAfterPush(){
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size(), "Stack size should be 1 after push");
    }

    @Test
    void testPopReturnsLastPushedElement(){
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop(), "Pop should return last pushed element");
    }

    @Test
    void testPeekReturnsLastPushedElement(){
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek(), "Peek should return last pushed element");
        assertEquals(2, stack.size(), "Peek should not remove element from stack");
    }
    
    @Test
    void testSizeAfterAllPops(){
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();
        assertEquals(0, stack.size(), "Stack size should be 0 after pop");
        assertTrue(stack.isEmpty(), "Stack should be empty after all pops");
    }

    @Test 
    void testPopEmptyStack(){
        assertThrows(IllegalStateException.class, () -> stack.pop(), "Pop should throw exception on empty stack");
    }

    @Test
    void testPeekEmptyStack(){
        assertThrows(IllegalStateException.class, () -> stack.peek(), "Peek should throw exception on empty stack");
    }

    @Test
    void testPushToFullStack(){
        TqsStack<Integer> boundedStack=new TqsStack<>(2);
        boundedStack.push(1);
        boundedStack.push(2);
        assertThrows(IllegalStateException.class, () -> boundedStack.push(3), "Pushing onto a full stack should throw an exception");
    }

    @Test
    void testpopTopN(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
    
    int result = stack.popTopN(3);
    assertEquals(3, result);
    assertEquals(4, stack.pop());
    assertThrows(IllegalArgumentException.class, () -> stack.popTopN(0), "Should throw exception for n = 0");
    assertThrows(IllegalArgumentException.class, () -> stack.popTopN(5), "Should throw exception if n > stack size");

    }

}
