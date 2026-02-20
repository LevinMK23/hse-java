package hse.java.lectures.lecture3.practice.randomSet;

public class HashMap<K ,V>{

    private Node<K,V>[] backets;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    public static int getDefaultCapacity() {
        return DEFAULT_CAPACITY;
    }


    public HashMap() {
        backets = new Node[DEFAULT_CAPACITY];
        size = 0 ;
    }

    public boolean empty() {
        return length() == 0;
    }

    Node<K,V> getKeyByIndex(int index) {
        return backets[index];
    }


    public int getIndex(K key) {
        int hash = (key == null) ? 0 : Math.abs(key.hashCode());
        return hash % backets.length;
    }


    public V put(K key , V value) {
        if (size >= backets.length * LOAD_FACTOR) {
            resize();
        }


        int index = getIndex(key);
        Node<K,V> current = backets[index] ;
        while(current != null) {
            if(current.key.equals(key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        backets[index] = new Node<>(key , value , backets[index]) ;
        size++;
        return null;
    }


    public V remove(K key) {
        int index = getIndex(key);
        Node<K,V> currentNode = backets[index];
        Node<K,V> prevNode = null;
        while(currentNode != null) {
            if ( currentNode.key.equals(key)) {
                V value = currentNode.value;
                if ( prevNode == null) {
                    backets[index] = currentNode.next;
                }else {
                    prevNode.next = currentNode.next;
                }
                size--;
                return value;
            }
            prevNode = currentNode;
            currentNode = currentNode.next;
        }
        return  null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = backets;
        backets = new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                Node<K, V> next = node.next;
                put(node.key, node.value);
                node = next;
            }
        }
    }

    boolean containsKey(K key) {
        int index = getIndex(key) ;
        Node<K,V> node = backets[index] ;
        while(node != null) {
            if (node.key.equals(key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }


    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> node = backets[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public int length() {
        return size;
    }


}
