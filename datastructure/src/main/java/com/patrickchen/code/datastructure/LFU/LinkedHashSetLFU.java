package com.patrickchen.code.datastructure.LFU;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LinkedHashSetLFU {
    public static void main(String[] args) {
        LinkedHashSetLFU cache = new LinkedHashSetLFU(2);
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
    private Map<Integer, LinkedHashSet<Node>> freqMap;
    private int capacity;
    private int size;
    // the minimum freq stored
    private int min;

    public LinkedHashSetLFU(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = cache.get(key);
        if(node == null) return -1;
        // freq++
        freqInc(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(capacity == 0) return;
        Node node = cache.get(key);
        if(node != null){
            node.value = value;
            freqInc(node);
        }else{
            if(size == capacity){
                Node deadNode = removeNode();
                cache.remove(deadNode.key);
                size --;
            }
            Node newNode = new Node(key,value);
            cache.put(key,newNode);
            addNode(newNode);
            size++;
        }
    }

    private void freqInc(Node node){

        LinkedHashSet<Node> set = freqMap.get(node.freq);
        if(set != null) {
            // remove from the old freq LinkedList
            set.remove(node);
        }
        // if current freq is the minimum and size == 0, need to refresh min
        if(node.freq == min && set.size() == 0){
            min = node.freq + 1;
        }
        node.freq ++;
        LinkedHashSet<Node> newSet = freqMap.get(node.freq);
        if(newSet == null){
            newSet = new LinkedHashSet<Node>();
            freqMap.put(node.freq,newSet);
        }
        newSet.add(node);
    }

    private void addNode(Node node){
        // new elment belongs to the freq==1 list
        LinkedHashSet<Node> set = freqMap.get(1);
        if(set == null){
            set = new LinkedHashSet<>();
            freqMap.put(1,set);
        }
        set.add(node);
        // min is 1
        min = 1;
    }

    private Node removeNode(){
        LinkedHashSet<Node> set = freqMap.get(min);
        // remove the first
        Node node = set.iterator().next();
        set.remove(node);
        if(node.freq == min && set.size() == 0){
            min ++;
        }
        return node;
    }

    private class Node {
        int key;
        int value;
        int freq = 1;

        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }

        public Node(){}
    }
}
