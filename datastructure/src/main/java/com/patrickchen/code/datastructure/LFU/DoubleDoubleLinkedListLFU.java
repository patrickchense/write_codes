package com.patrickchen.code.datastructure.LFU;

import java.util.HashMap;
import java.util.Map;

public class DoubleDoubleLinkedListLFU {
    public static void main(String[] args) {
        DoubleDoubleLinkedListLFU cache = new DoubleDoubleLinkedListLFU(2);
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
    Map<Integer,Node> cache;
    DoubleLinkedList firstLinkedList, lastLinkedList;
    int capacity;
    int size;

    public DoubleDoubleLinkedListLFU(int capacity){
        this.capacity = capacity;
        cache = new HashMap<>();
        // sentinels
        firstLinkedList = new DoubleLinkedList();
        lastLinkedList = new DoubleLinkedList();
        firstLinkedList.next = lastLinkedList;
        lastLinkedList.pre = firstLinkedList;
    }

    private class Node {
        int key;
        int value;
        int freq = 1;
        Node pre;
        Node next;
        DoubleLinkedList doubleLinkedList;

        public Node(){}

        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }
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
                /**
                 * the smallest freq element is in lastLinkedList.pre
                 * in the list, the longest unvisited element is lastLinkedList.pre.tail.pre
                 */
                DoubleLinkedList list = lastLinkedList.pre;
                Node deadNode = list.tail.pre;
                cache.remove(deadNode.key);
                list.removeNode(deadNode);
                size--;
                //delete list if it's empty
                if(list.isEmpty()){
                    removeDoubleLinkedList(list);
                }
            }
            Node newNode = new Node(key, value);
            cache.put(key,newNode);
            DoubleLinkedList list = lastLinkedList.pre;
            if(list.freq != 1){
                DoubleLinkedList newList = new DoubleLinkedList(1);
                addDoubleLinkedList(newList,list);
                newList.addNode(newNode);
            }else{
                list.addNode(newNode);
            }
            size++;
        }
    }

    private void freqInc(Node node){
        DoubleLinkedList list = node.doubleLinkedList;
        if(list != null){
            list.removeNode(node);
        }
        if(list.isEmpty()){
            removeDoubleLinkedList(list);
        }
        node.freq++;
        DoubleLinkedList preList = list.pre;
        if(preList.freq != node.freq){
            DoubleLinkedList newList = new DoubleLinkedList(node.freq);
            addDoubleLinkedList(newList,preList);
            newList.addNode(node);
        }else{
            preList.addNode(node);
        }
    }

    public void removeDoubleLinkedList(DoubleLinkedList list){
        list.pre.next = list.next;
        list.next.pre = list.pre;
    }

    public void addDoubleLinkedList(DoubleLinkedList newList, DoubleLinkedList preList){
        newList.pre = preList;
        newList.next = preList.next;
        preList.next.pre = newList;
        preList.next = newList;
    }

    private class DoubleLinkedList {
        int freq;
        Node head;
        Node tail;
        DoubleLinkedList pre;
        DoubleLinkedList next;

        public DoubleLinkedList(){
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
        }

        public DoubleLinkedList(int freq){
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
            this.freq = freq;
        }

        public void removeNode(Node node){
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        public void addNode(Node node){
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            node.doubleLinkedList = this;
        }

        public boolean isEmpty(){
            return head.next == tail;
        }
    }
}
