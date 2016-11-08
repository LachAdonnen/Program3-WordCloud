import java.util.*;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {
	
	private Stack<BSTnode<K>> nodeStack = new Stack<BSTnode<K>>();
	
	public BSTDictionaryIterator(BSTnode<K> initialNode){
		pushAllLeftChildren(initialNode);
	}
	
	private void pushAllLeftChildren(BSTnode<K> parentNode) {
		if (parentNode != null) {
			BSTnode<K> testNode = parentNode;
			while (testNode != null) {
				nodeStack.push(testNode);
				testNode = testNode.getLeft();
			}
		}
	}

    public boolean hasNext() { return !nodeStack.isEmpty(); }

    public K next() {
    	// returnNode will never have a left child since we push all left
    	// children onto the stack together.
        BSTnode<K> returnNode = nodeStack.pop();
        pushAllLeftChildren(returnNode.getRight());
        return returnNode.getKey();
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
