package com.patrickchen.code.datastructure.LFU;

import java.util.HashMap;
import java.util.Map;

public class CustomizeDoubleLinkedListLFU {
    public static void main(String[] args) {
        CustomizeDoubleLinkedListLFU cache = new CustomizeDoubleLinkedListLFU(2);
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

    private Map<Integer,Node> cache;
    private Map<Integer,DoubleLinkedList> freqMap;
    private int capacity;
    private int size;
    private int min;

    public CustomizeDoubleLinkedListLFU(int capacity){
        this.capacity = capacity;
        cache = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key){
        Node node = cache.get(key);
        if(node == null) return -1;
        freqInc(node);
        return node.value;
    }

    public void put(int key, int value){
        if(capacity == 0) return;
        Node node = cache.get(key);
        if(node != null){
            node.value = value;
            freqInc(node);
        }else{
            if(size == capacity){
                //remove the element before the tail
                DoubleLinkedList list = freqMap.get(min);
                Node deadNode = list.tail.pre;
                cache.remove(deadNode.key);
                list.removeNode(deadNode);
                size--;
            }
            Node newNode = new Node(key,value);
            DoubleLinkedList newList = freqMap.get(1);
            if(newList == null){
                newList = new DoubleLinkedList();
                freqMap.put(1,newList);
            }
            newList.addNode(newNode);
            cache.put(key,newNode);
            size++;
            min = 1;
        }

    }

    private void freqInc(Node node){
        DoubleLinkedList list = freqMap.get(node.freq);
        if(list != null){
            list.removeNode(node);
        }
        if(min == node.freq && list.isEmpty()){
            min ++;
        }
        node.freq ++;
        DoubleLinkedList newList = freqMap.get(node.freq);
        if(newList == null){
            newList = new DoubleLinkedList();
            freqMap.put(node.freq, newList);
        }
        newList.addNode(node);
    }

    private class Node {
        int key;
        int value;
        int freq = 1;
        Node pre;
        Node next;

        public Node(){}

        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    //Customize DoubleLinkedList
    private class DoubleLinkedList {
        Node head;
        Node tail;
        public DoubleLinkedList(){
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
        }
        // add node in the beginning
        public void addNode(Node node){
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
        }

        public void removeNode(Node node){
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        public boolean isEmpty(){
            return head.next == tail;
        }
    }
}
