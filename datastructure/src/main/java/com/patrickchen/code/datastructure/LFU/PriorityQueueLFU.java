package com.patrickchen.code.datastructure.LFU;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueLFU {
    public static void main(String[] args) {
        PriorityQueueLFU cache = new PriorityQueueLFU(2);
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

    // store all data
    Map<Integer,Node> cache;
    // priorityQueue
    Queue<Node> queue;
    int capacity;
    // current size
    int size;
    // global access
    int index = 0;

    public PriorityQueueLFU(int capacity){
        this.capacity = capacity;
        if(capacity > 0){
            queue = new PriorityQueue<>(capacity);
        }
        cache = new HashMap<>();
    }

    public int get(int key){
        Node node = cache.get(key);
        if(node == null) return -1;
        node.freq++;
        node.index = index++;
        // remove then add everytime to make the queue resort again.
        queue.remove(node);
        queue.offer(node);
        return node.value;
    }

    public void put(int key, int value){
        if(capacity == 0) return;
        Node node = cache.get(key);
        if(node != null){
            node.value = value;
            node.freq++;
            node.index = index++;
            queue.remove(node);
            queue.offer(node);
        }else {
            if(size == capacity){
                cache.remove(queue.poll().key);
                size--;
            }
            Node newNode = new Node(key, value, index++);
            queue.offer(newNode);
            cache.put(key,newNode);
            size++;
        }
    }
    // Implement the Comparable interface to make the ProrityQueue work
    private class Node implements Comparable<Node>{
        int key;
        int value;
        int freq = 1;
        int index;

        public Node(){}

        public Node(int key, int value, int index){
            this.key = key;
            this.value = value;
            this.index = index;
        }

        @Override
        public int compareTo(Node o) {
            int minus = this.freq - o.freq;
            return minus == 0? this.index - o.index : minus;
        }
    }
}
