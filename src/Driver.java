import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//'Rebhiya Ruba' Sbeih 1160305 Sec#2
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Driver extends Application{
	public static HuffmanNode[] nodes;
	public static HuffmanNode root;
	public static File f;
	public static String preOrderString="";
	TextArea txtArea=new TextArea();
	public static void main(String[] args) {
		File f=new File("hamlet.txt");
		//		int[] counter=readFile(f);
		try {
			long a=System.currentTimeMillis();
//				Huffman.encode(f);
			long b=System.currentTimeMillis();
			System.out.println("Time:"+ (b-a));
		} catch (Exception e) {
		}   
		int heapSize=0;
		HuffmanNode[] test= {new HuffmanNode((byte)'A',45),new HuffmanNode((byte)'F',5),new HuffmanNode((byte)'B',13),new HuffmanNode((byte)'E',9),
				new HuffmanNode((byte)'C',12),new HuffmanNode((byte)'D',16)};
		PriorityQueue queue=new PriorityQueue(test);
		HuffmanNode result=Huffman.buildTree(test);
		Huffman.getCodes(result);
		//Huffman.inOrder(result);
/*		preOrder(result);
		System.out.println(preOrderString);
		HuffmanNode rootTest=new HuffmanNode((byte)preOrderString.charAt(0));
		Stack<HuffmanNode> s = new Stack<HuffmanNode>(); 
		HuffmanNode current=rootTest;
		root=current;
		s.push(current);
		for(int i=1;i<preOrderString.length();i++) {
			if(preOrderString.charAt(i)=='0') {
				HuffmanNode next=new HuffmanNode((byte)preOrderString.charAt(i));
				if(current.left==null) 
					current.left=next;
				else
					current.right=next;
				current=next;
				System.out.println(current);
				s.push(current);
			}
			if(preOrderString.charAt(i)=='1') {
				i++;
				if(current.left==null) {
					current.left=new HuffmanNode((byte)preOrderString.charAt(i));
					System.out.println("left "+current.left);
				}
				else if(current.right==null){
					current.right=new HuffmanNode((byte)preOrderString.charAt(i));
					System.out.println("right "+current.right);
				}
				while(!s.isEmpty()) {
					current=s.pop();
				}
			}	
		}  */
		System.out.println("+++");
			preOrder2(root);
		File f2=new File("hamlet.huff");
		try {
			long a=System.currentTimeMillis();
//				Huffman.decode(f2); //ziyad was here ibt - 3473
			long b=System.currentTimeMillis();
			System.out.println("Time:"+ (b-a));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Application.launch(args); 
	}

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


	public static void inOrder(HuffmanNode t){
		if (t != null) {
			inOrder(t.left);
			System.out.println(t);
			inOrder(t.right);	
		}	
	}
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
	public static void preOrder2(HuffmanNode t){
		if (t != null) {
			System.out.println(t);
			preOrder(t.left);
			preOrder(t.right);	
		}	
	}
	/*	public static void rebuildTree(HuffmanNode t) {
		if (t != null) {

		}
	} */
	public static byte getBit(byte ID, int position) {
		// return cretin bit in selected byte
		return (byte) ((ID >> position) & 1);
	}
	public static byte reverseBitsByte(byte x) {
		int intSize = 8;
		byte y=0;
		for(int position=intSize-1; position>0; position--){
			y+=((x&1)<<position);
			x >>= 1;
		}
		return y;
	}
	public static void decode(File f) throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(f));
		//		BufferedOutputStream fw = new BufferedOutputStream(new FileOutputStream(nf));
		int c;
		byte bits;
		byte current;
		String currentBitString="";
		String bitString="";
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
		int numOfHashtags=0;
		/*		while(numOfHashtags<2) {
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
		} */

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

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane=new GridPane();
		Scene scene = new Scene(pane);
		Label lblTitle=new Label("                    COMPRESS/DECOMPRESS");
		lblTitle.setFont(Font.font(17));
		Label lblInput=new Label("Input: \n\n");
		Label lblSequence1=new Label("            CHOOSE A FILE:");
		Label lblSequence2=new Label("         DECODE A FILE:");
		TextField txtLED=new TextField();
		TextField txtPS=new TextField();
		txtPS.setEditable(false);
		txtLED.setEditable(false);
		txtLED.setPromptText("From File");
	//	txtPS.setPromptText("Will Be Generated");
		Button btBrowse=new Button("Browse...");
		Button btBrowse2=new Button("Browse...");
		Button btRun=new Button("     COMPRESS     ");
		Button btRun2=new Button("     DECOMPRESS     ");
		Button btRun3=new Button("     HEADER     ");
		Label lblSpace=new Label("         ");
		Label lblOutput=new Label("Output: \n\n");
		Label lblConnections1=new Label("      ");
		Label lblConnections2=new Label("         The Connections :");
		TextField txtConnect1=new TextField();
		TextField txtConnect2=new TextField();
//		Label lblTable=new Label("Table: \n\n");
		txtConnect1.setEditable(false);
		txtConnect2.setEditable(false);
		pane.setVgap(10);
		HBox hbox1=new HBox(10);
		HBox hbox2=new HBox(10);
		HBox hbox3=new HBox(10);
		HBox hbox4=new HBox(10);
		HBox hbox5=new HBox(10);
		hbox1.getChildren().addAll(lblSequence1,txtLED,btBrowse);
	//	hbox2.getChildren().addAll(lblSequence2,txtPS,btBrowse2);
		hbox3.getChildren().addAll(lblSpace,btRun,btRun2,btRun3);
		hbox4.getChildren().addAll(lblConnections1,txtArea);
		hbox5.getChildren().addAll(lblConnections2,txtConnect2);
		FileChooser fileChooser = new FileChooser();
		btBrowse.setOnAction(e->{
			txtArea.clear();
			txtLED.clear();
			txtPS.clear();
			txtConnect1.clear();
			txtConnect2.clear();
			f = fileChooser.showOpenDialog(primaryStage);
			txtLED.setText(f.getAbsolutePath());
		});
		btRun.setOnAction(e->{
			txtArea.clear();
			txtConnect1.clear();
			txtConnect2.clear();
			try {
				long a=System.currentTimeMillis();
				Huffman.encode(f);
				long b=System.currentTimeMillis();
				System.out.println("Encoding Time:"+ (b-a));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("File is Compressed! ");
				alert.setHeaderText("Complete");
				alert.setContentText("The file has been compressed");
				alert.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		btRun2.setOnAction(e->{
			txtArea.clear();
			txtConnect1.clear();
			txtConnect2.clear();
			int i = f.getName().lastIndexOf('.');
			String ext=f.getName().substring(i+1);
			if(ext.compareTo("huff")!=0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("File Not Compatible ");
				alert.setHeaderText("There seems to be a problem with the file you selected");
				alert.setContentText("The file must be of the .huff extenstion in order to decode it.");
				alert.show();
				return;
			}
			System.out.println(ext);
			try {
				long a=System.currentTimeMillis();
				Huffman.decode(f);
				long b=System.currentTimeMillis();
				System.out.println("Decoding Time:"+ (b-a));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("File is Decompressed! ");
				alert.setHeaderText("Complete");
				alert.setContentText("The file has been Decompressed");
				alert.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		btRun3.setOnAction(e->{
			txtArea.clear();
			txtArea.appendText("The Header uses 1s and 0s to mark leaf and non-leaf nodes\n");
			txtArea.appendText("0 means we've moved along the tree, 1 means we've reached \n ");
			txtArea.appendText("a leaf node. The Header also begins with the extension of \n ");
			txtArea.appendText("original file. The hashtag is used to differentiate between\n ");
			txtArea.appendText("the extension and tree, and also the tree and the file data\n ");
			txtArea.appendText("->Current Header:\n");
			txtArea.appendText(Huffman.preOrderString);
		});
		txtArea.setMaxWidth(400);
		pane.add(lblTitle, 0, 0);
		pane.add(lblInput, 0, 1);
		pane.add(hbox1, 0, 4);
		pane.add(hbox2, 0, 5);
		pane.add(hbox3, 0, 7); 
//		pane.add(lblOutput, 0, 8);
		pane.add(hbox4, 0, 9);
//		pane.add(hbox5, 0, 10);
//		pane.add(lblTable, 0, 11);
//		pane.add(txtArea, 0, 8);
		primaryStage.setHeight(400);
		primaryStage.setWidth(450);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project 1");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
}
