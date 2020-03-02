import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Stack;

public class Huffman {
	public static HuffmanNode[] nodes;
	public static HuffmanNode root;
	public static String preOrderString="";
	public static HuffmanNode rootTree;
	/**
	 * function to build huffman tree from byte frequencies 
	 * @param h array of huffman nodes 
	 * @return root of tree
	 */
	public static HuffmanNode buildTree(HuffmanNode[] h) {
		PriorityQueue queue=new PriorityQueue(h);
		int n=queue.size;
		for(int i=1;i<n;i++) {
			HuffmanNode a=queue.dequeue();
			HuffmanNode b=queue.dequeue();
			int repetitions=a.repetitions+b.repetitions;
			HuffmanNode z=new HuffmanNode(repetitions);
			z.left=a;
			z.right=b;
			queue.enqueue(z);
		}
		return queue.dequeue();
	}
	//left root right
	/**
	 * traversal of the huffman node tree (inOrder)
	 * @param t root of huffman tree
	 */
	public static void inOrder(HuffmanNode t){
		if (t != null) {
			inOrder(t.left);
			System.out.println(t);
			inOrder(t.right);	
		}	
	}
	/**
	 * public function to get codes for each huffman node 
	 * @param root root of huffman tree 
	 */
	public static void getCodes(HuffmanNode root) {
		String s="";
		getCodes(root,s);
	}
	//left root right
	/**
	 * private function to get codes for each huffman node 
	 * @param t current huffman node 
	 * @param s current huffman code 
	 */
	private static void getCodes(HuffmanNode t,String s){
		if (t != null) {
			if(t.left==null&&t.right==null) {
				t.code=s;
//				System.out.println(t);
				}
			getCodes(t.left,s+"0");
			getCodes(t.right,s+"1");	
		}	
	}
	/**
	 * function to encode a file using huffman compression 
	 * @param f file to be encoded 
	 * @throws Exception 
	 */
	public static void encode(File f) throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(f));
		byte b[] = new byte[(int)f.length()];
		System.out.println("size of file "+(int)f.length());
		fin.read(b);
		int[] counter=new int[256];
		for(int i=0;i<b.length;i++) {	
			counter[b[i]+128]++;
		}
		int heapSize=0;
		for(int i=0;i<counter.length;i++) {
			if(counter[i]!=0) {
				heapSize++;
			}
		}
		nodes=new HuffmanNode[heapSize];
		int j=0;
		for(int i=0;i<counter.length;i++) {
			if(counter[i]!=0) {
				nodes[j]=new HuffmanNode((byte)(i - 128),counter[i]);
				j++;
			}
		}
		PriorityQueue queue=new PriorityQueue(nodes);
		HuffmanNode result=Huffman.buildTree(nodes);
		Huffman.getCodes(result);
		root=result;
		int extBegin = f.getName().lastIndexOf('.');
		String ext=f.getName().substring(extBegin+1);
		preOrderString+=ext+'#';
		System.out.println(ext);
		preOrder(result);
		preOrderString+='#';
		System.out.println(preOrderString);
		
		//		Arrays.sort(nodes);
		for(int i=0;i<nodes.length;i++) {
			System.out.println(nodes[i]);
		} 
		//Huffman.inOrder(result);
		String extension = "";
		int i = f.getName().lastIndexOf('.');
		extension = f.getName().substring(i+1);
		System.out.println(extension);
		String newFileName=f.getName().substring(0, i)+".huff";
		System.out.println(f.getName().substring(0, i));
		File nf = new File(newFileName);
		BufferedOutputStream fw = new BufferedOutputStream(new FileOutputStream(nf));
		for(int g=0;g<preOrderString.length();g++)
			fw.write(preOrderString.charAt(g));
//		fw.write(10);
		//	fin.reset();
		int c;
		BufferedInputStream fin2 = new BufferedInputStream(new FileInputStream(f));
		String con="";
		String extra="";
		byte[]  output = new byte[4];
		int currentIndexOutputArray=0;
		//doesn't work for files than become under one byte
		while((c=fin2.read())!=-1) {
			//		System.out.println("+++");
			//	for(int q=0;q<b.length;q++) {
			//		c=fin2.read();
			for(int k=0;k<nodes.length;k++) {
				if(nodes[k].data==(byte)c) {
					con+=nodes[k].code;
				}
			}
			if(con.length()<8) {
				continue;
			}

			extra = con.substring(0, 8);
			//new
			int val = Integer.parseInt(extra, 2);
			byte bb = (byte) val;
			output[currentIndexOutputArray] = bb ; 
			currentIndexOutputArray++;
			//end new
			if (con.length() > 8 ) {
				//	extra=con.substring(8, con.length());
				con = con.substring(8);
			}
			if(con.compareTo(extra)==0) {
				con="";
			}
			if (currentIndexOutputArray == 4 ){

				fw.write(output);
				currentIndexOutputArray = 0 ;
				output = new byte[4];
			}
			//		int val = Integer.parseInt(extra, 2);
			//		byte bb = (byte) val;
			//		fw.write(bb);
			//	System.out.println("current byte is "+extra);
		}
		if(con.length()!=0) {
			for (int ii =0 ; ii<8 ; ii ++) 
				con += "0"; 
			extra = con.substring(0, 8);
			int val = Integer.parseInt(extra, 2);
			byte bb = (byte) val;
			output[currentIndexOutputArray] = bb ; 
			currentIndexOutputArray++;
		}
		if (currentIndexOutputArray != 0 )
			for(int p=0;p<currentIndexOutputArray;p++)
				fw.write(output[p]);
		//	fw.write(b);
		fw.flush();
		fw.close();
		fin.close();
	}
	/**
	 * function to get preOrder string used in header
	 * @param t
	 */
	public static void preOrder(HuffmanNode t){
		if (t != null) {
	//		System.out.println(t);
			if(t.left==null&&t.right==null) {
				preOrderString+="1"+(char)t.data;
			//	System.out.println(s);
				}
			else
				preOrderString+="0";
			preOrder(t.left);
			preOrder(t.right);	
		}	
	}
	/**
	 * function to decode file that has been compressed using huffman 
	 * @param f file to be decoded
	 * @throws Exception
	 */
	public static void decode(File f) throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(f));
//		BufferedOutputStream fw = new BufferedOutputStream(new FileOutputStream(nf));
		int c;
		byte bits;
		byte current;
		String currentBitString="";
		String bitString="";
		//read each byte from file, reverse it, add to giant bitString
		//reads ALL BYTES
		String ext="";
		int extLength=0;
		while(extLength<3) {
			c=fin.read();
			ext+=(char)c;
			extLength++;
		}
		int i = f.getName().lastIndexOf('.');
		String newFileName=f.getName().substring(0, i)+"Decoded."+ext;
		File nf = new File(newFileName);
		BufferedOutputStream fw = new BufferedOutputStream(new FileOutputStream(nf));
		System.out.println("ext is "+ext);
//			c=fin.read();
	//	System.out.println("is this hashtag"+(char)c);
		int numOfHashtags=0;
		while(numOfHashtags<2) {
			c=fin.read();
			if(c=='#')
				numOfHashtags++;
			else if(c=='0')
				System.out.println("not leaf");
			else if(c=='1') {
				System.out.println("leaf");
				c=fin.read();
				System.out.println((char)c);
			}
		} 
	/*	c=fin.read();
		numOfHashtags++;
		byte b =(byte)fin.read();
		rootTree=new HuffmanNode(b);
		HuffmanNode currentNode=rootTree;
		Stack<HuffmanNode> s = new Stack<HuffmanNode>(); 
		s.push(currentNode);
		while(numOfHashtags<2) {
			c=fin.read();
			if(c=='#') {
				numOfHashtags++;
			}
			if(c=='0') {
				if(!s.isEmpty()) {
				currentNode=s.pop();
				HuffmanNode nextNode=new HuffmanNode((byte)c);
				currentNode.left=nextNode;
				currentNode=nextNode;
				s.push(currentNode);
				}
			}
			if(c=='1') {
				if(!s.isEmpty()) {
				c=fin.read();
				HuffmanNode leaf=new HuffmanNode((byte)c);
				currentNode=s.pop();
				currentNode.left=leaf;
				if((c=fin.read())=='1') {
					HuffmanNode leaf2=new HuffmanNode((byte)c);
					currentNode.right=leaf2;
				}
				}
			}
			
		}
		System.out.println("tree");
		inOrder(rootTree);
		*/
		while( (c=fin.read())!=-1 ) {
			current=(byte) c;
			currentBitString="";
			bits=0;
			for(int q=0;q<8;q++) {
				bits=getBit(current,q);
				currentBitString+=bits;
			}
			StringBuilder bitStringReverse=new StringBuilder(currentBitString);
			bitString+=bitStringReverse.reverse().toString();
			HuffmanNode current2=root;
			String bitss="";
			int x;
			for(x=0;x<bitString.length();x++) {
				if(current2.right==null&current2.left==null) {
					fw.write(current2.data);
					System.out.println(current2.data);
					current2=root;
					break;
				}
				if(bitString.charAt(x)=='0') {
					current2=current2.left;
				}
				else {
					current2=current2.right;

				}
			}
			if(x!=bitString.length())
				bitString=bitString.substring(x);
		}
		HuffmanNode current2=root;
		for(int x=0;x<bitString.length();x++) {
			if(current2.right==null&current2.left==null) {
				fw.write(current2.data);
				current2=root;
			}
			if(bitString.charAt(x)=='0') {
				current2=current2.left;
			}
			else {
				current2=current2.right;

			}
		}
		fw.flush();
		fw.close();
		fin.close();

	}
	/**
	 * function to get certain bit of byte
	 * @param ID byte 
	 * @param position desired position of bit 
	 * @return byte with desired bit 
	 */
	public static byte getBit(byte ID, int position) {
		// return cretin bit in selected byte
		return (byte) ((ID >> position) & 1);
	}
	public static void readFile5(File f) throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(f));
		System.out.println("size of file "+(int)f.length());
		String extension = "";
		int i = f.getName().lastIndexOf('.');
		//	extension = f.getName().substring(i+1);
		//fix this to original file extension
		extension="txt";
		System.out.println(extension);
		String newFileName=f.getName().substring(0, i)+"Expanded."+extension;
		System.out.println(f.getName().substring(0, i));
		File nf = new File(newFileName);
		BufferedOutputStream fw = new BufferedOutputStream(new FileOutputStream(nf));
		int c;
		byte bits;
		byte current;
		String currentBitString="";
		String bitString="";
		byte[]  output = new byte[4];
		int currentIndexOutputArray=0;
		while((c=fin.read())!=-1) {
			current=(byte)c;
			currentBitString="";
			bits=0;
			for(int q=0;q<8;q++) {
				bits=getBit(current,q);
				currentBitString+=bits;
			}
			StringBuilder bitStringReverse=new StringBuilder(currentBitString);
			bitString+=bitStringReverse.reverse().toString();
			currentBitString="";
			boolean foundCode=false;
			for(int j=0;j<bitString.length();j++) {
				currentBitString+=bitString.charAt(j);
				for(int k=0;k<nodes.length;k++) {
					if(nodes[k].code.compareTo(currentBitString.trim())==0) {
						//	fw.write(nodes[k].data);	
						output[currentIndexOutputArray]=nodes[k].data;
						currentIndexOutputArray++;
						foundCode=true;
						break;
					}
				}
				if(foundCode) {
					bitString=bitString.substring(currentBitString.length());
					currentBitString="";
					foundCode=false;
					break;
				}
				if (currentIndexOutputArray == 4 ){

					fw.write(output);
					currentIndexOutputArray = 0 ;
					output = new byte[4];
				}
			}

		}
		if (currentIndexOutputArray != 0 )
			for(int p=0;p<currentIndexOutputArray;p++)
				fw.write(output[p]);
		currentBitString="";
		boolean foundCode=false;
		for(int j=0;j<bitString.length();j++) {
			currentBitString+=bitString.charAt(j);
			for(int k=0;k<nodes.length;k++) {
				if(nodes[k].code.compareTo(currentBitString.trim())==0) {
					fw.write(nodes[k].data);	
					foundCode=true;
					break;
				}
			}
			if(foundCode) {
				currentBitString="";
				foundCode=false;
			}
		}
		fw.flush();
		fw.close();
		fin.close();

	}

}
