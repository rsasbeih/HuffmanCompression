
public class PriorityQueue {
	int size;
	HuffmanNode[] elements;
	/**
	 * constructor for priority queue that sets up for size n 
	 * @param n size of queue
	 */
	public PriorityQueue(int n){
		setup(n);
	}
	/**
	 * constructor for priority queue that sets up for array of HuffmanNodes 
	 * @param a array of HuffmanNodes 
	 */
	public PriorityQueue(HuffmanNode[] a){
		setup(a.length);
		for(int i=0;i<a.length;i++){
			enqueue(a[i]);
		}
	}
	/**
	 * function that sets up priority queue
	 * @param n size of queue
	 */
	private void setup(int n){
		elements=new HuffmanNode[n+1];
		size=0;
		//was '0'
		//not sure if this affected tree
		elements[0]=new HuffmanNode((byte)0,0);
	}
	/**
	 * function that retrieves parent of desired position 
	 * @param pos position 
	 * @return parent of position 
	 */
	private int parent(int pos){
		return pos/2;
	}
	/**
	 * function that retrieves left child of desired position 
	 * @param pos position 
	 * @return left child of position
	 */
	private int leftChild(int pos){
		return pos*2;
	}
	/**
	 *function that retrieves right child of desired position 
	 * @param pos position 
	 * @return right child of position
	 */
	private int rightChild(int pos){
		return pos*2+1;
	}
	/**
	 * function that swaps numbers
	 * @param x first number
	 * @param y second number
	 */
	private void swap(int x,int y){
		HuffmanNode temp=elements[x];
		elements[x]=elements[y];
		elements[y]=temp;
	}
	/**
	 * function that inserts huffmannode to queue
	 * @param x huffmannode 
	 */
	public void enqueue(HuffmanNode x){
		elements[++size]=x;
		int current=size;
		while(elements[current].compareTo(elements[parent(current)])<0){
			swap(current,parent(current));
			current=parent(current);
		}
	}
	/**
	 * function that prints priority queue
	 */
	public void printStructure(){
		for(int i=1;i<size/2;i++){
			System.out.println(elements[i]+" "+elements[i*2]+" "+elements[i*2+1]);
		}
	}
	//function to help with remove
	//if i want to do this on a minimum heap, change operations
	private void heapStep(int pos){
		int left=leftChild(pos);
		int right=rightChild(pos);
		int min=pos;
		//>
		if(left<=size&&(elements[min].compareTo(elements[left])>0)){
			min=left;
		}
		if(right<=size&&(elements[min].compareTo(elements[right])>0)){
			min=right;
		}
		if(min!=pos){
			swap(min,pos);
			heapStep(min);
		}
	}
	/**
	 * functions that returns greatest priority node 
	 * @return greatest priority node
	 */
	public HuffmanNode dequeue(){
		HuffmanNode min=elements[1];
		//we switch the first element in the heap with the last element so heapstep can work properly
		//last element should go back to original position
		elements[1]=elements[size--];
		heapStep(1);
		return min;
	}
	//makes an array a heap
	/**
	 * functions that makes heap out of array 
	 */
	public void minHeap(){
		for(int i=size;i>=1;i--)
			heapStep(i);
	}

}
