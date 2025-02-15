package com.example;
import java.util.LinkedList;

public class TqsStack <T>
{
    private LinkedList<T> collection;
    private int capacity;
    
    public TqsStack(){
        this.collection=new LinkedList<>();
        this.capacity=-1;    
        
    }

    public TqsStack(int capacity){
        if(capacity<=0){
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        this.collection = new LinkedList<>();
        this.capacity = capacity;
    }

    public void push(T item) {
        if (capacity > 0 && collection.size() >= capacity) {
            throw new IllegalStateException("Stack is full");
        }
        collection.add(item);
    }
    public T pop(){
        if (collection.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        } 
        return collection.removeLast();
    }


    public T peek(){
       if (collection.isEmpty()){
        throw new IllegalStateException("Stack is empty");
       }
       return collection.getLast();
    }

    public int size(){
        return collection.size();
    }

    public boolean isEmpty(){
        return collection.isEmpty();
    }

    public boolean isFull() {
        return capacity > 0 && collection.size() >= capacity;
    }


    public T popTopN(int n){
        if(n<=0 || n> collection.size()){
            throw new IllegalArgumentException("Invalid number of element to pop");

        }
        
        T top = null;
        for (int i = 0; i < n; i++) {
            top = collection.removeFirst();
        }
        return top;
    }
}
