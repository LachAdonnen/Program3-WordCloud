import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // Default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;
    // Array to store objects in the heap
    private ArrayList<E> heap;

    public ArrayHeap() {
    	heap = new ArrayList<E>(INIT_SIZE);
    	heap.add(null);
    }
    
    public ArrayHeap(int initSize) {
    	if (initSize < 0) { throw new IllegalArgumentException(); }
    	heap = new ArrayList<E>(initSize);
    	heap.add(null);
    }

    public boolean isEmpty() { return (this.size() == 0); }

    public void insert(E item) {
        heap.add(item);
        this.fixHeapOrderUp(this.size());
    }
    
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

    public E removeMax() {
    	validateHeapHasElements();
    	E returnValue = this.getMax();
    	heap.set(1, heap.get(this.size()));
    	heap.remove(this.size());
    	this.fixHeapOrderDown(1);
        return returnValue;
    }
    
    private void fixHeapOrderDown(int initPos) {
    	int currPos = initPos;
    	boolean done = false;
    	while (!done) {
    		int currPriority = 0;
    		if (currPos < this.size()) { heap.get(currPos).getPriority(); }
    		int leftPos = this.getLeftPos(currPos);
    		int leftPriority = 0;
    		if (leftPos < this.size()) { heap.get(leftPos).getPriority(); }
    		int rightPos = this.getRightPos(currPos);
    		int rightPriority = 0;
    		if (rightPos < this.size()) { heap.get(rightPos).getPriority(); }
    		
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

    public E getMax() {
    	validateHeapHasElements();
    	return heap.get(1);
    }

    public int size() { return heap.size() - 1; }
    
    private int getLeftPos(int pos) { return 2 * pos; }
    
    private int getRightPos(int pos) { return (2 * pos) + 1; }
    
    private int getParentPos(int pos) { return pos / 2; }
    
    private void swap(int pos1, int pos2) {
    	E tempValue = heap.get(pos1);
    	heap.set(pos1, heap.get(pos2));
    	heap.set(pos2, tempValue);
    }
    
    private void validateHeapHasElements() {
    	if (this.isEmpty()) { throw new NoSuchElementException(); }
    }
}
