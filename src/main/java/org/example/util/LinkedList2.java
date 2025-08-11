package org.example.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinkedList2<T> extends AbstractSequentialList<T> implements List<T>, Deque<T> {
    // Node
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    private Node<T> removeByIndex(int index) {
        if (index == 0) {
            Node<T> removed = head;
            removeFirst();
            return removed;
        } else if (index == size - 1) {
            Node<T> removed = tail;
            removeLast();
            return removed;
        }
        Node<T> before = getNodeByIndex(index - 1);
        Node<T> current = before.next;
        before.next = current.next;
        size--;
        return current;
    }

    private Node<T> insertByIndex(T t, int index) {
        if (index == 0) {
            addFirst(t);
            return head;
        } else if (index == size) {
            addLast(t);
            return tail;
        }
        Node<T> before = getNodeByIndex(index - 1);
        Node<T> current = new Node<>(t);
        current.next = before.next;
        before.next = current;
        size++;
        return current;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).data;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        insertByIndex(element, index);
    }

    @Override
    public T remove(int index) {
        return removeByIndex(index).data;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) return false;
        if (Objects.equals(head.data, o)) {
            removeFirst();
            return true;
        }
        Node<T> current = head;
        while (current.next != null) {
            if (Objects.equals(current.next.data, o)) {
                if (current.next == tail) tail = current;
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public T set(int index, T element) {
        Node<T> node = getNodeByIndex(index);
        T old = node.data;
        node.data = element;
        return old;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        return new ListIterator<T>() {
            int pos = index;
            Node<T> current = (index == size) ? null : getNodeByIndex(index);
            Node<T> lastReturned = null;
            Node<T> prev = (index == 0) ? null : getNodeByIndex(index - 1);

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastReturned = current;
                prev = current;
                current = current.next;
                pos++;
                return lastReturned.data;
            }

            @Override
            public boolean hasPrevious() {
                return pos > 0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) throw new NoSuchElementException();
                pos--;
                current = getNodeByIndex(pos);
                lastReturned = current;
                prev = (pos == 0) ? null : getNodeByIndex(pos - 1);
                return lastReturned.data;
            }

            @Override
            public int nextIndex() {
                return pos;
            }

            @Override
            public int previousIndex() {
                return pos - 1;
            }

            @Override
            public void remove() {
                if (lastReturned == null) throw new IllegalStateException();
                prev.next = current.next;
//                LinkedList2.this.removeByIndex(pos - (current == lastReturned.next ? 1 : 0));
                if (current == lastReturned) current = current.next;
                lastReturned = null;
                pos--;
            }

            @Override
            public void set(T t) {
                if (lastReturned == null) throw new IllegalStateException();
                lastReturned.data = t;
            }

            @Override
            public void add(T t) {
                LinkedList2.this.insertByIndex(t, pos);
                pos++;
                lastReturned = null;
                current = getNodeByIndex(pos);
            }
        };
    }

    // ---------- Deque methods ----------
    @Override
    public void addFirst(T t) {
        Node<T> newNode = new Node<>(t);
        if (size == 0) tail = newNode;
        else newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public void addLast(T t) {
        Node<T> newNode = new Node<>(t);
        if (size == 0) head = newNode;
        else tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        T data = head.data;
        head = head.next;
        size--;
        if (size == 0) tail = null;
        return data;
    }

    @Override
    public T removeLast() {
        if (size == 0) throw new NoSuchElementException();
        T data = tail.data;
        if (size == 1) {
            head = tail = null;
        } else {
            Node<T> current = head;
            while (current.next != tail) current = current.next;
            tail = current;
            tail.next = null;
        }
        size--;
        return data;
    }

    @Override
    public T pollFirst() {
        return size == 0 ? null : removeFirst();
    }

    @Override
    public T pollLast() {
        return size == 0 ? null : removeLast();
    }

    @Override
    public T getFirst() {
        if (size == 0) throw new NoSuchElementException();
        return head.data;
    }

    @Override
    public T getLast() {
        if (size == 0) throw new NoSuchElementException();
        return tail.data;
    }

    @Override
    public T peekFirst() {
        return (size == 0) ? null : head.data;
    }

    @Override
    public T peekLast() {
        return (size == 0) ? null : tail.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (size == 0) return false;
        Node<T> current = head, prev = null, lastPrev = null, lastMatch = null;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                lastMatch = current;
                lastPrev = prev;
            }
            prev = current;
            current = current.next;
        }
        if (lastMatch != null) {
            if (lastPrev == null) removeFirst();
            else {
                lastPrev.next = lastMatch.next;
                if (lastMatch == tail) tail = lastPrev;
                size--;
            }
            return true;
        }
        return false;
    }

    // ---------- Queue compatibility ----------
    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull Iterator<T> descendingIterator() {
        ArrayList<T> list = new ArrayList<>(size);
        for (T item : this) list.add(item);
        Collections.reverse(list);
        return list.iterator();
    }
}
