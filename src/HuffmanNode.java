
public class HuffmanNode implements Comparable<HuffmanNode>{
	//was char
	byte data;
	int repetitions;
	String code;
	HuffmanNode right;
	HuffmanNode left;
	/**
	 * Argument constructor for HuffmanNode
	 * @param data data
	 * @param repetitions number of times data was repeated
	 */
	public HuffmanNode(byte data, int repetitions) {
		this.data=data;
		this.repetitions=repetitions;
	}
	/**
	 *  Argument constructor for HuffmanNode
	 * @param repetitions number of times data was repeated
	 */
	public HuffmanNode(int repetitions) {
		this.data='*';
		this.repetitions=repetitions;
	}
	/**
	 * Argument constructor for HuffmanNode
	 * @param data data
	 */
	public HuffmanNode(byte data) {
		this.data=data;
	}
	/**
	 * compares two huffmanNodes
	 */
	@Override
	public int compareTo(HuffmanNode h) {
		if(this.repetitions>h.repetitions)
			return 1;
		else if(this.repetitions<h.repetitions)
			return -1;
		else
			return 0;
	}
	/**
	 * prints huffman node
	 */
	@Override
	public String toString() {
		return "data: "+this.data+" reps: "+this.repetitions+" -> "+this.code;
	}
}
