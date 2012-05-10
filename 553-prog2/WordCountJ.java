//package edu.cs.iit.cs553;
import java.io.*;
import java.util.*;
//import net.mindview.util.*;

public class WordCountJ {
    public void main (String[] args) {
	Thread myrunnable = new Thread(new MyRunnable(),"1");
	myrunnable.start();
}

class MyRunnable implements Runnable{
	// must implement run	
	public void run(){
	String fileNumber = Thread.currentThread().getName();
	//int i = fileCount;	
	String first = "input/input_";
	//String middle = String.valueOf(i);
	String last = ".txt";
	String WholeFile = first + fileNumber + last;
	List<String> words = new ArrayList<String>(new TextFile(WholeFile, "\\W*\\s+\\W*"));
        Map<String,Integer> wordCount = new LinkedHashMap<String,Integer>();
           
        Iterator it = words.iterator();
        int totalWords = 0;
        while(it.hasNext()) {
            String s = (String)it.next();
            if(words.contains(s)) {
                Integer count = wordCount.get(s);
                wordCount.put(
                    s,
                    count == null ? 1 : count + 1
                );
                totalWords++;
            }
        }
        System.out.println();
        System.out.println("Word count: " + wordCount);
        System.out.println();
        System.out.println("Total words: " + totalWords);
}

}

class TextFile extends ArrayList<String> {
   
    // Read a file, split by any regular expression:
    public TextFile(String fileName, String splitter) {
        //super(Arrays.asList());
	read(fileName).split(splitter);
        // Regular expression split() often leaves an empty
        // String at the first position:
        if(get(0).equals(""))
            remove(0);
    }
   
    // Normally read by lines:
    public TextFile(String fileName) {
        this(fileName, "\n");
    }   
   
    // Read a file as a single string:
    public String read(String fileName) {
        StringBuilder sb = new StringBuilder();       
        try {
            BufferedReader in = new BufferedReader(
                new FileReader(
                    new File(fileName).getAbsoluteFile()
                )
            );
               
            try {
                String s;
                while((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            }
            finally {
                in.close();
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
}
