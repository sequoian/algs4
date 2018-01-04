import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n = 0;
    
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == q.length) {
            resize(2 * q.length);
        }
        q[n++] = item;
    }
    
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // choose a random index
        int idx = StdRandom.uniform(0, n);
        // swap random site with end of array, and decrement n
        Item tmp = q[--n];
        Item item = q[idx];
        q[idx] = tmp;
        // remove end of array
        q[n] = null;
        // resize if necessary
        if (n > 0 && n <= q.length/4) {
            resize(q.length/2);
        }
        return item;
    }
    
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return q[StdRandom.uniform(0, n)];
    }
    
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private Item[] copy;
        private int current = 0;
        
        public RandomIterator() {
            copy = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                copy[i] = q[i];
            }
            StdRandom.shuffle(copy, 0, n);
        }
        
        public boolean hasNext() {
            return current != n;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = copy[current++];
            return item;
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        
        r.enqueue("One");
        r.enqueue("Two");
        r.enqueue("Three");
        r.enqueue("Four");
        r.enqueue("Five");
        
        for (String s : r) {
            System.out.println(s);
        }
    }
}