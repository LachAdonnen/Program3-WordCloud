import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>>
	implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary

    // TO DO:
    //
    // Add a no-argument constructor
    //
    // Add your code to implement the Dictionary ADT operations using a binary
    // search tree.
    // You may use any code given in the on-line reading on BSTs.
    
    public BSTDictionary() {
    	this.root = null;
    	this.numItems = 0;
    }
    
    public void insert(K key) throws DuplicateException {
        this.insertInternalRecursion(root, key);
    }
    
    private BSTnode<K> insertInternalRecursion(BSTnode<K> searchNode, K key)
    		throws DuplicateException {
    	BSTnode<K> returnNode;
    	if (searchNode == null) { returnNode = new BSTnode<K>(key); }
    	else {
    		returnNode = searchNode;
    		int comparisonValue = searchNode.getKey().compareTo(key);
    		// Duplicate key
    		if (comparisonValue == 0) { throw new DuplicateException(); }
    		// Insert into left subtree
    		else if (comparisonValue < 0) {
    			searchNode.setLeft(this.insertInternalRecursion(
    					searchNode.getLeft(), key));
    		}
    		// Insert into right subtree
    		else {
    			searchNode.setRight(this.insertInternalRecursion(
    					searchNode.getRight(), key));
    		}
    	}
    	return returnNode;
    }

    public boolean delete(K key) {
        return this.deleteInternalRecursion(root, key);
    }
    
    private boolean deleteInternalRecursion(BSTnode<K> searchNode, K key) {
    	boolean returnValue = false;
    	if (searchNode != null) {
    		int comparisonValue = searchNode.getKey().compareTo(key);
    		// Found key
    		if (comparisonValue == 0) {
    			returnValue = true;
    			// Now find the max
    			if (searchNode.getLeft() != null) {
    				//TODO Need to figure this out...
//    				searchNode = new BSTnode<K>(this.getMaxNode(
//    						searchNode.getLeft()).getKey());
    			}
    			else {
    				searchNode = searchNode.getRight();
    			}
    		}
    		// Delete from left subtree
    		else if (comparisonValue < 0) {
    			returnValue = this.deleteInternalRecursion(
    					searchNode.getLeft(), key);
    		}
    		// Delete from right subtree
    		else {
    			returnValue = this.deleteInternalRecursion(
    					searchNode.getRight(), key);
    		}
    	}
    	return returnValue;
    }
    
    private BSTnode<K> getMaxNode(BSTnode<K> searchNode) {
    	BSTnode<K> maxNode = null;
    	BSTnode<K> testNode = searchNode;
    	while (testNode != null) {
    		maxNode = testNode;
    		testNode = maxNode.getRight();
    	}
    	return maxNode;
    }

    public K lookup(K key) {
        return null;  // replace this stub with your code
    }

    public boolean isEmpty() {
        return false;  // replace this stub with your code
    }

    public int size() {
        return 0;  // replace this stub with your code
    }
    
    public int totalPathLength() {
        return 0;  // replace this stub with your code
    }
    
    public Iterator<K> iterator() {
        return null;  // replace this stub with your code
    }
}
