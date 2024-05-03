import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size;
    private class Node {

        private K key;
        private V val;
        private Node left, right;
        public Node (K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }


    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        size--;
        return x;
    }

    private Node min(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<Entry<K, V>> {
        private List<Entry<K, V>> nodeList = new ArrayList<>();
        private int current = 0;

        public InOrderIterator() {
            inorder(root);
        }

        private void inorder(Node x) {
            if (x == null) return;
            inorder(x.left);
            nodeList.add(new Entry<>(x.key, x.val));
            inorder(x.right);
        }

        @Override
        public boolean hasNext() {
            return current < nodeList.size();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            return nodeList.get(current++);
        }
    }

    public static class Entry<K, V> {
        private final K key;
        private final V val;

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return val;
        }
    }
}