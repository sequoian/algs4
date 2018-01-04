import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;
    
    private class Node {
        Item item;
        Node next, prev;
    }
    
    public Deque() {
        size = 0;
    }
    
    public boolean isEmpty() {
       return first == null && last == null; 
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (last == null) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (first == null) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        else {
            first.prev = null;
        }
        size--;
        return item;
    }
    
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        size--;
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        
        d.addFirst("Two");
        d.addFirst("One");
        d.addLast("Three");
        for (String s : d) {
            System.out.println(s);
        }
        System.out.format("%d\n", d.size());
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
        System.out.format("%d\n", d.size());
        System.out.println(d.removeFirst());
        System.out.format("%d\n", d.size());
    }
}