package ru.geekbrains.lesson4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ru.geekbrains.lesson4.HashMap.Bucket.Node;
import ru.geekbrains.lesson4.HashMap.Entity;

public class HashMap<K, V> implements Iterable<Entity> {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;
    private int size = 0;


    public Bucket[] getBuckets() {
        return buckets;
    }

    private Bucket[] buckets;


    class Entity{
        K key;
        V value;
    }

    class Bucket<K, V>{

        private Node head;
        class Node{
            Node next;
            Entity value;
        }

        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null){
                    currentNode = currentNode.next;
                }
                else {
                    currentNode.next = node;
                    return null;
                }
            }

        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }
            else {
                Node node = head;
                while (node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key))
                    return (V)node.value.value;
                node = node.next;
            }
            return null;
        }


    }

    private int calculateBucketIndex(K key){
        return Math.abs(key.hashCode() % buckets.length);
    }

    private void recalculate(){
        size = 0;
        Bucket[] old = buckets;
        buckets = new Bucket[old.length * 2];
        for (Bucket bucket : old) {
            if (bucket != null) {
                Node node = bucket.head;
                while (node != null) {
                    put((K) node.value.key, (V) node.value.value);
                    node = node.next;
                }
            }
        }
    }

    public V put(K key, V value){

        if (buckets.length *LOAD_FACTOR <= size){
            recalculate();
        }

        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V res = (V)bucket.add(entity);
        if (res == null){
            size++;
        }
        return res;
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        V res = (V)bucket.remove(key);
        if (res != null){
            size--;
        }
        return res;
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return (V)bucket.get(key);
    }

    public HashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int initCount){
        buckets = new Bucket[initCount];
    }
    @Override
    public Iterator<HashMap.Entity> iterator() {
        return new HashMapIterator(getBuckets());
    }
    class HashMapIterator implements Iterator<HashMap.Entity>{
        private Bucket[] bucket;
        private int counter = 0;
        Node node = null;
        public HashMapIterator(Bucket[] bucket) {
            this.bucket = bucket;
            findNext();
        }
        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Entity next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Entity value = node.value;
            node = node.next;
            if (node == null) {
                findNext();
            }

            return value;
        }
        private void findNext() {
            while (counter < buckets.length && (buckets[counter] == null || buckets[counter].head == null)) {
                counter++;
            }

            if (counter < buckets.length) {
                node = buckets[counter].head;
                counter++;
            }
        }
    }
}