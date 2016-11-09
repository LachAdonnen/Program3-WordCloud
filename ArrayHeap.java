///////////////////////////////////////////////////////////////////////////////
// Title:            Prog3-WordCloud
// Files:            ArrayHeap.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implments a heap using an ArrayList for use as a pririty queue.
 * @author amcclain
 *
 * @param <E> Data type stored in the heap.
 */
public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // Default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;
    // Array to store objects in the heap
    private ArrayList<E> heap;

    /**
     * Constructs an ArrayList with only the 0th index occupied.
     */
    public ArrayHeap() {
    	heap = new ArrayList<E>(INIT_SIZE);
    	heap.add(null);
    }
    
    /**
     * Constructs an ArrayList of the given size with only the 0th index
     * occupied.
     * @param initSize The initial space allocation for the heap. 
     */
    public ArrayHeap(int initSize) {
    	if (initSize < 0) { throw new IllegalArgumentException(); }
    	heap = new ArrayList<E>(initSize);
    	heap.add(null);
    }

    @Override
    /**
     * Returns whether the heap has any elements. Note that this is not the
     * same as an empty ArrayList since the 0th index is unused.
     */
    public boolean isEmpty() { return (this.size() == 0); }

    @Override
    /**
     * Inserts a new element into the heap and fixes the ordering.
     */
    public void insert(E item) {
        heap.add(item);
        this.fixHeapOrderUp(this.size());
    }
    
    /**
     * Fixes the order of the heap starting with the given position and working
     * up the tree to the root. Only parents of the given position are ordered.
     * @param initPos The starting position to fix the order of the heap.
     */
    private void fixHeapOrderUp(int initPos) {
        int currPos = initPos;
    	boolean done = false;
        while (!done && currPos > 1) {
        	int parentPos = this.getParentPos(currPos);
        	int currPriority = heap.get(currPos).getPriority();
        	int parentPriority = heap.get(parentPos).getPriority();
        	if (currPriority > parentPriority) {
        		this.swap(currPos, parentPos);
        		currPos = parentPos;
        	}
        	else { done = true; }
        }
    }

    @Override
    /**
     * Removes and returns the element with the highest priority, then replaces
     * it in the heap with the next highest value.
     */
    public E removeMax() {
    	validateHeapHasElements();
    	E returnValue = this.getMax();
    	heap.set(1, heap.get(this.size()));
    	heap.remove(this.size());
    	this.fixHeapOrderDown(1);
        return returnValue;
    }
    
    /**
     * Fixes the order of the heap starting with the given position and working
     * down the tree towards the end. Only children of the given position are
     * considered for ordering, and it stops once the starting node is in a
     * valid position.
     * @param initPos The starting position to fix the order of the heap.
     */
    private void fixHeapOrderDown(int initPos) {
    	int currPos = initPos;
    	boolean done = false;
    	while (!done) {
    		int currPriority = 0;
    		if (currPos < this.size()) {
    			currPriority = heap.get(currPos).getPriority();
    		}
    		int leftPos = this.getLeftPos(currPos);
    		int leftPriority = 0;
    		if (leftPos < this.size()) {
    			leftPriority = heap.get(leftPos).getPriority();
    		}
    		int rightPos = this.getRightPos(currPos);
    		int rightPriority = 0;
    		if (rightPos < this.size()) {
    			rightPriority = heap.get(rightPos).getPriority();
    		}
    		
    		if (leftPriority > currPriority) {
    			if (leftPriority > rightPriority) {
    				this.swap(currPos, leftPos);
    				currPos = leftPos;
    			}
    			else {
    				this.swap(currPos, rightPos);
    				currPos = rightPos;
    			}
    		}
    		else if (rightPriority > currPriority) {
    			if (rightPriority > leftPriority) {
    				this.swap(currPos, rightPos);
    				currPos = rightPos;
    			}
    			else {
    				this.swap(currPos, leftPos);
    				currPos = leftPos;
    			}
    		}
    		else { done = true; }
    	}
    }

    @Override
    /**
     * Returns the element with the highest priority, but leaves it in the heap.
     */
    public E getMax() {
    	validateHeapHasElements();
    	return heap.get(1);
    }

    @Override
    /**
     * Returns the number of elements in the heap.
     */
    public int size() { return heap.size() - 1; }
    
    /**
     * Calculates the array position of the left child node.
     * @param pos The parent array position.
     * @return The left child array position.
     */
    private int getLeftPos(int pos) { return 2 * pos; }
    
    /**
     * Calculates the array position of the right child node.
     * @param pos The parent array position.
     * @return The right child array position.
     */
    private int getRightPos(int pos) { return (2 * pos) + 1; }
    
    /**
     * Calculates the array position of the parent node.
     * @param pos The child array position (either right or left).
     * @return The parent array position.
     */
    private int getParentPos(int pos) { return pos / 2; }
    
    /**
     * Swaps two elements in the array.
     * @param pos1 The first position to be swapped.
     * @param pos2 The second position to be swapped.
     */
    private void swap(int pos1, int pos2) {
    	E tempValue = heap.get(pos1);
    	heap.set(pos1, heap.get(pos2));
    	heap.set(pos2, tempValue);
    }
    
    /**
     * Validates that there are elements in the heap and throws an excpetion if
     * there are none.
     */
    private void validateHeapHasElements() {
    	if (this.isEmpty()) { throw new NoSuchElementException(); }
    }
}
