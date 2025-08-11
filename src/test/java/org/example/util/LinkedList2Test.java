package org.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LinkedList2Test {

    private LinkedList2<String> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList2<>();
    }

    // ---------- Basic List behavior ----------
    @Test
    void testAddAndGet() {
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    void testSet() {
        list.add("A");
        list.add("B");
        String old = list.set(1, "C");
        assertEquals("B", old);
        assertEquals("C", list.get(1));
    }

    @Test
    void testRemoveByIndex() {
        list.add("A");
        list.add("B");
        String removed = list.remove(0);
        assertEquals("A", removed);
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    void testRemoveByObject() {
        list.add("A");
        list.add("B");
        assertTrue(list.remove("A"));
        assertFalse(list.remove("Z"));
    }

    @Test
    void testContainsAndIndexOf() {
        list.add("X");
        list.add("Y");
        assertTrue(list.contains("X"));
        assertEquals(0, list.indexOf("X"));
        assertEquals(-1, list.indexOf("Z"));
    }

    @Test
    void testClear() {
        list.add("X");
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void testAddAtIndex() {
        list.add("A");
        list.add(0, "B");
        assertEquals("B", list.get(0));
        assertEquals("A", list.get(1));
    }

    @Test
    void testOutOfBoundsThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    // ---------- Deque behavior ----------
    @Test
    void testAddFirstAndLast() {
        list.addFirst("B");
        list.addFirst("A");
        list.addLast("C");
        assertEquals(Arrays.asList("A", "B", "C"), new ArrayList<>(list));
    }

    @Test
    void testOfferFirstAndLast() {
        assertTrue(list.offerFirst("B"));
        assertTrue(list.offerFirst("A"));
        assertTrue(list.offerLast("C"));
        assertEquals(Arrays.asList("A", "B", "C"), new ArrayList<>(list));
    }

    @Test
    void testPollFirstAndLast() {
        list.addAll(Arrays.asList("A", "B", "C"));
        assertEquals("A", list.pollFirst());
        assertEquals("C", list.pollLast());
        assertEquals(Collections.singletonList("B"), new ArrayList<>(list));
    }

    @Test
    void testPeekFirstAndLast() {
        list.addAll(Arrays.asList("A", "B", "C"));
        assertEquals("A", list.peekFirst());
        assertEquals("C", list.peekLast());
        assertEquals(3, list.size()); // Should not remove
    }

    // ---------- Iterator behavior ----------
    @Test
    void testIterator() {
        list.addAll(Arrays.asList("A", "B", "C"));
        Iterator<String> it = list.iterator();
        List<String> collected = new ArrayList<>();
        while (it.hasNext()) {
            collected.add(it.next());
        }
        assertEquals(Arrays.asList("A", "B", "C"), collected);
    }

    @Test
    void testListIteratorForwardAndBackward() {
        list.addAll(Arrays.asList("A", "B", "C"));
        ListIterator<String> it = list.listIterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasPrevious());
        assertEquals("A", it.previous());
    }

    @Test
    void testListIteratorSetAndRemove() {
        list.addAll(Arrays.asList("A", "B", "C"));
        ListIterator<String> it = list.listIterator();
        assertEquals("A", it.next());
        it.set("Z");
        assertEquals("Z", list.get(0));

        assertEquals("B", it.next());
        it.remove();
        assertEquals(Arrays.asList("Z", "C"), new ArrayList<>(list));
    }

    // ---------- Bulk operations ----------
    @Test
    void testAddAll() {
        list.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(3, list.size());
    }

    @Test
    void testRemoveAll() {
        list.addAll(Arrays.asList("A", "B", "C", "D"));
        list.removeAll(Arrays.asList("B", "D"));
        assertEquals(Arrays.asList("A", "C"), new ArrayList<>(list));
    }

    @Test
    void testRetainAll() {
        list.addAll(Arrays.asList("A", "B", "C", "D"));
        list.retainAll(Arrays.asList("B", "D"));
        assertEquals(Arrays.asList("B", "D"), new ArrayList<>(list));
    }

    @Test
    void testNullElementsAllowed() {
        list.add(null);
        assertTrue(list.contains(null));
        assertNull(list.get(0));
    }
}
