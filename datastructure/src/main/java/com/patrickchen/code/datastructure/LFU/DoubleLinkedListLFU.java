package com.patrickchen.code.datastructure.LFU;

import java.util.HashMap;
import java.util.Map;

public class DoubleLinkedListLFU {
    public static void main(String[] args) {
        DoubleLinkedListLFU cache = new DoubleLinkedListLFU(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // return 1
        cache.put(3, 3);    // remove key 2
        System.out.println(cache.get(2)); // return -1
        System.out.println(cache.get(3)); // return 3
        cache.put(4, 4);    // remove key 1
        System.out.println(cache.get(1)); // return -1
        System.out.println(cache.get(3)); // return 3
        System.out.println(cache.get(4)); // return 4
    }

    private Map<Integer, Node> cache;
    private Node head;
    private Node tail;
    private int capacity;
    private int size;

    public DoubleLinkedListLFU(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;
        node.freq++;
        moveToPostion(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            node.freq++;
            moveToPostion(node);
        } else {
            //if cache full
            if (size == capacity) {
                //remove the most left one
                cache.remove(head.next.key);
                removeNode(head.next);
                size--;
            }
            Node newNode = new Node(key, value);
            addNode(newNode);
            cache.put(key, newNode);
            size++;
        }
    }

    //if current node's freq is larger than the one behind it, keep looking
    // until there is a node with the bigger freq or the tail node, insert before it
    private void moveToPostion(Node node) {
        Node nextNode = node.next;
        // remove current
        removeNode(node);
        // loop until the suitable node
        while (node.freq >= nextNode.freq && nextNode != tail) {
            nextNode = nextNode.next;
        }
        // insert before the suitable node
        node.pre = nextNode.pre;
        node.next = nextNode;
        nextNode.pre.next = node;
        nextNode.pre = node;

    }

    // add node then move node to the right position
    private void addNode(Node node) {
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
        moveToPostion(node);
    }

    private void removeNode(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    class Node {
        int key;
        int value;
        int freq = 1;
        Node pre;
        Node next;

        public Node() {}

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
