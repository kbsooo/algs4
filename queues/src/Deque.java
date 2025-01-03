import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the deque
    public int size() { return size; }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("cannnot null");

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        if (isEmpty())
            last = first;
        else
            oldFirst.prev = first;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("cannot null");

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (isEmpty())
            first = last;
        else
            oldLast.next = last;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("deque empty");

        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty())
            last = null;
        else
            first.prev = null;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("deque empty");

        Item item = last.item;
        last = last.next;
        size--;

        if (isEmpty())
            first = null;
        else
            last.prev = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("no more element");

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() { throw new UnsupportedOperationException("no remove"); }
    }

    public static void main(String[] args){
      Deque<String> deque = new Deque<>();

        // 비어있는지 테스트
        System.out.println("초기 상태 비어있음: " + deque.isEmpty());
        System.out.println("초기 크기: " + deque.size());

        // 앞쪽 추가 테스트
        System.out.println("\n앞쪽 추가 테스트:");
        deque.addFirst("첫 번째");
        deque.addFirst("두 번째");
        System.out.println("크기: " + deque.size());

        // 뒤쪽 추가 테스트
        System.out.println("\n뒤쪽 추가 테스트:");
        deque.addLast("마지막");
        System.out.println("크기: " + deque.size());

        // 반복자 테스트
        System.out.println("\n반복자 테스트:");
        for (String s : deque) {
            System.out.println(s);
        }

        // 제거 테스트
        System.out.println("\n제거 테스트:");
        System.out.println("앞에서 제거: " + deque.removeFirst());
        System.out.println("뒤에서 제거: " + deque.removeLast());
        System.out.println("최종 크기: " + deque.size());

        // 예외 테스트
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nnull 추가 시 예외 발생 확인");
        }
    }
}