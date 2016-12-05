package project;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;

public class readdata {
	
	public static HashMap<String, HashMap<String , Float>> outerMap=new HashMap<String, HashMap<String,Float>>();
    public static void main(String[] args) throws IOException, InterruptedException{
    	//USER INPUT FILE
    	ArrayList<String> files = new ArrayList<>();
    	String desc="yes";
        while(desc.equals("yes")){
    	Scanner userinput=new Scanner(System.in);
    	System.out.println("Enter the name of router file");
    	String fname=userinput.nextLine();
    	files.add(fname);
    	read(fname);
    	//READING NEW NODES YES/NO
    	System.out.println("Read more nodes- yes/no?");
    	Scanner des=new Scanner(System.in);
    	String decision=des.nextLine();
    	desc=decision.toLowerCase();
    	}      
        dvrp();
        Thread.sleep(15000);
        //WAITING FOR 15 SECONDS        
        while(true){
        	//CALCULATING SHORTEST PATHS EVERY 15 SECONDS
        	for(int i=0;i<files.size();i++){
        		read(files.get(i));
        	}
        	dvrp();
        	Thread.sleep(15000);
        }  
    }
    
    public static void read(String filename) throws IOException{
    	HashMap<String,Float> innerMap=new HashMap<String, Float>();
    	String x;
    	File file=new File(filename);
    	String filepath=file.getAbsolutePath();
    	//SETTING OUTER KEYS
    	String keyname=filename.substring(0,1);
        outerMap.put(keyname,innerMap);
    	//READING CONTENTS OF FILES
        BufferedReader input=new BufferedReader(new FileReader(filepath));
        ArrayList<String>  arr=new ArrayList<String>();
        while((x=input.readLine())!= null){
            	arr.add(x);
    	}
    		for(int i=1;i<arr.size();i++){
                 String spl[]=arr.get(i).split("\\s");
                 innerMap.put(spl[0],Float.parseFloat( spl[1]))	; 
    		}
    	input.close();    	
    }
    public static void dvrp(){
    	
    	int size=outerMap.size();
    	float vector[][]=new float[size][size];
    	for(int i=0;i<size;i++){
    		for(int j=0;j<size;j++){
    			vector[i][j]=999;
    		}
    	}
    		//PRINTING INITIAL MATRIX
    		for(int k=0;k<size;k++){
        		for(int j=0;j<size;j++){
        			//System.out.print(vector[k][j]);
        			//System.out.println(" \t");
        		}
        		//System.out.println("\n");
        	}
    	//STORING KEYS
    	String[] index=new String[size];
    	int q=0;
    	for(String in:outerMap.keySet()){
    		index[q]=in;
    	    q++;
    	}
    	for(int w=0;w<size;w++){
    	//System.out.println(index[w]);
    	}
    	//STORING IN DISTANCE VECTORS
    	for(String k: outerMap.keySet()){
        	//System.out.println(k);
        //	for(String iMap:outerMap.get(k).keySet()){
        	for(int i=0;i<size;i++){
        	   if(!outerMap.get(k).keySet().contains(index[i])){
        		   
        		   outerMap.get(k).put(index[i], (float) 999);
        	}
        	//System.out.println(iMap);
        }
    	}
    	int row=0;
    	int col=0;
    	for(String k: outerMap.keySet()){
    	for(String iMap:outerMap.get(k).keySet()){
    		vector[row][col]=outerMap.get(k).get(iMap);
    		col++;
    		}
        	row++;
        	col=0;
        	}
        	
        //PRINTING POPULATED VECTOR
    	/*
    	for(int k=0;k<size;k++){
    		for(int j=0;j<size;j++){
    			if(k==j){
    			vector[k][j]=0;
    			}
    			System.out.print(vector[k][j]+" ");
    			//System.out.println(" \t");
    		}
    		System.out.println("");
    	}*/
    	
    //CALCULATING DISTANCE VECTORS
    	for(int currentnode=0;currentnode<size;currentnode++){
    		//System.out.println(index[currentnode]);
    		for(int incoming=0;incoming<size;incoming++){
    			float fhop=vector[currentnode][incoming];
    			for(int nextnode=0;nextnode<size;nextnode++){
    				if(fhop+vector[incoming][nextnode]<vector[currentnode][nextnode]){
    					vector[currentnode][nextnode]=fhop+vector[incoming][nextnode];
    					
    			}
    		}
    		}

    	}
    	//PRINTING FINAL VECTORS
    	System.out.println("\n");
    	System.out.println("ROUTING TABLE");
    	for(int k=0;k<size;k++){
		System.out.print(" "+" "+index[k]+ " \t");
    	}
    	System.out.println("");
    	for(int k=0;k<size;k++){
    		System.out.print(index[k]+ " ");
    		for(int j=0;j<size;j++){
    			if(k==j){
    			vector[k][j]=0;
    			}
    			System.out.print(vector[k][j]+"\t ");
    			//System.out.println(" \t");
    		}
    		System.out.println("");
    	}
    }
    }
