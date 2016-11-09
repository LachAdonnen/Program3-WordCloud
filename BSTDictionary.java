///////////////////////////////////////////////////////////////////////////////
// Title:            Prog3-WordCloud
// Files:            BSTDictionary.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;

/**
 * Manages a dictionary of elements implmeneted as a binary search tree.
 * @author Alex McClain
 * @param <K> The data type to be stored in the dictionary.
 */
public class BSTDictionary<K extends Comparable<K>>
	implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary
    
    /**
     * Constructs an empty dictionary with 0 entries.
     */
    public BSTDictionary() {
    	root = null;
    	numItems = 0;
    }
    
    @Override
    /**
     * Inserts a element into the dictionary.
     */
    public void insert(K key) throws DuplicateException {
        root = insert(root, key);
    }
    
    /**
     * Recursively inserts a new element into the dictionary tree by searching
     * for a qualified parent node. If the current searchNode has children such
     * that the new element could not be added, the search begins anew with the
     * relevant child node.
     * @param searchNode The current node to check as a potential parent.
     * @param key The new element to add to the tree.
     * @return The resultant node after insertion.
     * @throws DuplicateException
     */
    private BSTnode<K> insert(BSTnode<K> searchNode, K key)
    		throws DuplicateException {
    	BSTnode<K> returnNode;
    	if (searchNode == null) {
    		returnNode = new BSTnode<K>(key);
    		numItems++;
    	}
    	else {
    		returnNode = searchNode;
    		int comparisonValue = searchNode.getKey().compareTo(key);
    		// Duplicate key
    		if (comparisonValue == 0) { throw new DuplicateException(); }
    		// Insert into left subtree
    		else if (comparisonValue > 0) {
    			searchNode.setLeft(this.insert(searchNode.getLeft(), key));
    		}
    		// Insert into right subtree
    		else {
    			searchNode.setRight(this.insert(searchNode.getRight(), key));
    		}
    	}
    	return returnNode;
    }

    @Override
    /**
     * Deletes an entry from the dictionary.
     */
    public boolean delete(K key) {
    	int initialNumItems = numItems;
    	root = delete(root, key);
    	return (initialNumItems != numItems);
    }
    
    /**
     * Recursively deletes a node from the tree by checking the current
     * searchNode and searching all subtrees until the element is found.
     * @param searchNode The current node to check for deletion.
     * @param key The element to remove from the tree.
     * @return The resultant node after deletion.
     */
    private BSTnode<K> delete(BSTnode<K> searchNode, K key) {
    	BSTnode<K> returnNode = searchNode;
    	if (searchNode != null) {
    		int comparisonValue = searchNode.getKey().compareTo(key);
    		// Found key
    		if (comparisonValue == 0) {
    			// If no right child, return left child
    			// This also handle the case when both are null
    			if (searchNode.getRight() == null) {
    				returnNode = searchNode.getLeft();
    				numItems--;
    			}
    			// If no left child, return right child
    			else if (searchNode.getLeft() == null) {
    				returnNode = searchNode.getRight();
    				numItems--;
    			}
    			/*
    			 * There are two children, so replace the key of the current
    			 * node with the minimum of the right subtree.
    			 * This preserves the BST properties.
    			 * Once copied, delete the node containing that minimum key.
    			 */
    			else {
    				BSTnode<K> replaceNode = getMinNode(searchNode.getRight());
    				K replaceKey = replaceNode.getKey();
    				searchNode.setKey(replaceKey);
    				delete(replaceNode, replaceKey);
    			}
    		}
    		// Delete from left subtree
    		else if (comparisonValue > 0) {
    			searchNode.setLeft(this.delete(searchNode.getLeft(), key));
    		}
    		// Delete from right subtree
    		else {
    			searchNode.setRight(this.delete(searchNode.getRight(), key));
    		}
    	}
    	return returnNode;
    }
    
    /**
     * Retrieves the minimum node from the subtree defined by the given node.
     * @param searchNode The pseudo-root node for the subtree.
     * @return The node with the minimum value in this subtree.
     */
    private BSTnode<K> getMinNode(BSTnode<K> searchNode) {
    	BSTnode<K> minNode = null;
    	BSTnode<K> testNode = searchNode;
    	while (testNode != null) {
    		minNode = testNode;
    		testNode = minNode.getLeft();
    	}
    	return minNode;
    }

    @Override
    /**
     * Searches the dictionary for an element and returns it if found.
     */
    public K lookup(K key) {
        return lookup(root, key);
    }

    /**
     * Recursively searches the tree for the given element.
     * @param searchNode The current node to check for return.
     * @param key The element to be found in the tree.
     * @return The key value if a matching node was found.
     */
    private K lookup(BSTnode<K> searchNode, K key) {
    	if (key == null) { return null; }
    	K returnKey = null;
    	if (searchNode != null) {
    		K searchKey = searchNode.getKey();
    		int comparisonValue = searchKey.compareTo(key);
    		// Found key
    		if (comparisonValue == 0) {
    			returnKey = searchKey;
    		}
    		// Search left subtree
    		else if (comparisonValue > 0) {
    			returnKey = this.lookup(searchNode.getLeft(), key);
    		}
    		// Search right subtree
    		else {
    			returnKey = this.lookup(searchNode.getRight(), key);
    		}
    	}
    	return returnKey;
    }
    
    @Override
    /**
     * Returns whether the dictionary has no entries.
     */
    public boolean isEmpty() { return (numItems == 0); }

    @Override
    /**
     * Returns the number of entries in the dictionary.
     */
    public int size() { return numItems; }
    
    @Override
    /**
     * Returns the total path length defined as the sum of all path lengths in
     * the dictionary.
     */
    public int totalPathLength() {
        return totalPathLength(root, 0);
    }
    
    /**
     * Recursively calculates the total path length by determining the total
     * path length of the subtree defined by the pseudo-root searchNode.
     * @param searchNode The node defining the subtree to process.
     * @param currDepth The depth of the given node which should be added to all
     * path lengths calculated in this subtree.
     * @return The total path length for this subtree.
     */
    private int totalPathLength(BSTnode<K> searchNode,
    		int currDepth) {
    	if (searchNode == null) { return 0; }
    	return currDepth
    			+ this.totalPathLength(searchNode.getLeft(), currDepth + 1)
    			+ this.totalPathLength(searchNode.getRight(), currDepth + 1);
    }
    
    @Override
    /**
     * Returns an iterator for the dictionary.
     */
    public Iterator<K> iterator() { return new BSTDictionaryIterator<>(root); }
}
