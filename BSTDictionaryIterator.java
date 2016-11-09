///////////////////////////////////////////////////////////////////////////////
// Title:            Prog3-WordCloud
// Files:            BSTDictionaryIterator.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
///////////////////////////////////////////////////////////////////////////////
import java.util.*;

/**
 * Implements an iterator for a binary search tree dictionary. The iterator
 * moves over the tree in order of the key values from smallest to largest.
 * @author amcclain
 *
 * @param <K> Data type entered into the dictionary
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {
	
	// Stack used to track progress throughout the tree. Each time a node is
	// visited, all left children are added in order to find the next min value.
	private Stack<BSTnode<K>> nodeStack = new Stack<BSTnode<K>>();
	
	/**
	 * Creates a new iterator and populates the stack until the first minimum
	 * value is pushed.
	 * @param initialNode The root of the tree to be searched.
	 */
	public BSTDictionaryIterator(BSTnode<K> initialNode){
		pushAllLeftChildren(initialNode);
	}
	
	/**
	 * Pushes all left children from a node onto the progress stack. Note that
	 * this isn't the entire left subtree; only left children on the left-most
	 * path are added to the stack each time.
	 * @param parentNode The parent node to start from to find left children.
	 */
	private void pushAllLeftChildren(BSTnode<K> parentNode) {
		if (parentNode != null) {
			BSTnode<K> testNode = parentNode;
			while (testNode != null) {
				nodeStack.push(testNode);
				testNode = testNode.getLeft();
			}
		}
	}

	@Override
	/**
	 * Returns whether there are still elements to be returned by the iterator.
	 */
    public boolean hasNext() { return !nodeStack.isEmpty(); }

	@Override
	/**
	 * Returns the next element from the tree.
	 */
    public K next() {
    	// returnNode will never have a left child since we push all left
    	// children onto the stack together.
        BSTnode<K> returnNode = nodeStack.pop();
        pushAllLeftChildren(returnNode.getRight());
        return returnNode.getKey();
    }

	/**
	 * Removes an element from the tree. Not implemented.
	 */
    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
