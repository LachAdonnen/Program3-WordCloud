import java.util.*;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {

    // TO DO:
    //
    // Add your code to implement the BSTDictionaryIterator.  To receive full
    // credit:
    // - You must not use recursion in any of methods or constructor.
    // - The constructor must have a worst-case complexity of O(height of BST).
    // 
    // Hint: use a Stack and push/pop nodes as you iterate through the BST.
    // The constructor should push all the nodes needed so the *first* call 
    // to next() returns the value in the node with the smallest key.
    // (You can use the Java API Stack or implement your own Stack - if you
    // implement your own, make sure to hand it in.)

    public boolean hasNext() {
        return false;  // replace this stub with your code
    }

    public K next() {
        return null;  // replace this stub with your code
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
