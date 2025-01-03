import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int index = StdRandom.uniformInt(n);
        Item item = items[index];
        items[index] = items[n - 1];
        items[n - 1] = null;
        n--;

        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items[StdRandom.uniformInt(n)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledItems;
        private int current;

        public RandomizedQueueIterator() {
            shuffledItems = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                shuffledItems[i] = items[i];
            }

            for (int i = n - 1; i > 0; i--) {
                int j = StdRandom.uniformInt(i + 1);
                Item temp = shuffledItems[i];
                shuffledItems[i] = shuffledItems[j];
                shuffledItems[j] = temp;
            }
            current = 0;
        }

        public boolean hasNext() {
            return current < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            return shuffledItems[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }


    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        System.out.println("Is empty: " + queue.isEmpty());

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        System.out.println("Size: " + queue.size());

        System.out.println("Sample: " + queue.sample());

        System.out.println("Iterator test:");
        for (String s : queue) {
            System.out.println(s);
        }

        System.out.println("Second iterator test:");
        for (String s : queue) {
            System.out.println(s);
        }

        System.out.println("Dequeued item: " + queue.dequeue());
        System.out.println("New size: " + queue.size());
    }
}