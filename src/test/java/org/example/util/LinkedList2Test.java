package org.example.util;

import org.example.model.*;
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

    @Test
    void testMergeSortInteger()
    {
        var list = new LinkedList2<Integer>();
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(8);
        list.add(-5);
        list.add(-8);
        System.out.println(list);
        list.sortList(Integer::compare);
        System.out.println(list);
    }

    @Test
    void testMergeSortAsset()
    {
        var list = new LinkedList2<Asset>();
        list.add(new Book("book 1","author 1",1920, BookStatus.BORROWED));
        list.add(new Book("book 2","author 2",1925, BookStatus.EXIST));
        list.add(new Thesis("Thesis 3","author 3",1930));
        list.add(new Thesis("Thesis 4","author 4",2000));
        list.add(new Reference("Reference 5","author 5",1900));
        list.add(new Reference("Reference 6","author 6",1903));
        list.add(new Magazine("Magazine 7","author 7",1820, "publisher 7"));
        list.add(new Magazine("Magazine 8","author 8",1844, "publisher 8"));
        for  (Asset asset : list) {
            System.out.println(asset);
        }
        list.sortList(Comparator.comparing(Asset::getReleaseDate));
        System.out.println();
        System.out.println();
        for  (Asset asset : list) {
            System.out.println(asset);
        }
    }
}
