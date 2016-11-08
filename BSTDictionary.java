import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>>
	implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary
    
    public BSTDictionary() {
    	root = null;
    	numItems = 0;
    }
    
    public void insert(K key) throws DuplicateException {
        root = insert(root, key);
    }
    
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

    public boolean delete(K key) {
    	int initialNumItems = numItems;
    	root = delete(root, key);
    	return (initialNumItems != numItems);
    }
    
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
    
    private BSTnode<K> getMinNode(BSTnode<K> searchNode) {
    	BSTnode<K> minNode = null;
    	BSTnode<K> testNode = searchNode;
    	while (testNode != null) {
    		minNode = testNode;
    		testNode = minNode.getLeft();
    	}
    	return minNode;
    }

    public K lookup(K key) {
        return lookup(root, key);
    }

    private K lookup(BSTnode<K> searchNode, K key) {
    	K returnKey = null;
    	if (searchNode != null) {
    		int comparisonValue = searchNode.getKey().compareTo(key);
    		// Found key
    		if (comparisonValue == 0) {
    			returnKey = key;
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
    
    public boolean isEmpty() { return (numItems == 0); }

    public int size() { return numItems; }
    
    public int totalPathLength() {
        return totalPathLength(root, 0);
    }
    
    private int totalPathLength(BSTnode<K> searchNode,
    		int currDepth) {
    	if (searchNode == null) { return 0; }
    	return currDepth
    			+ this.totalPathLength(searchNode.getLeft(), currDepth + 1)
    			+ this.totalPathLength(searchNode.getRight(), currDepth + 1);
    }
    
    public Iterator<K> iterator() { return new BSTDictionaryIterator<>(root); }
}
