/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ai_hw2;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Alrfou
 */
public class AI_HW2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String[][] from_file = null;
/*

UR L  L  UR U  UR G*/
        File f=null;
                Scanner scan = new Scanner(System.in);
                System.out.println("Please enter 1 to choose maze 1 or enter 2 to choose maze 2 or enter 3 to choose maze 3:");
                int input=scan.nextInt();
                if(input== 1 || input==2||input==3){
                if(input==1){
                    f = new File("C:\\Users\\Alrfou\\Documents\\NetBeansProjects\\AI_HW2\\src\\ai_hw2\\Input2.txt");
                }else if(input==2){
                     f = new File("C:\\Users\\Alrfou\\Documents\\NetBeansProjects\\AI_HW2\\src\\ai_hw2\\Input.txt");
                }else if(input==3){
                    f = new File("C:\\Users\\Alrfou\\Documents\\NetBeansProjects\\AI_HW2\\src\\ai_hw2\\Input3.txt");
                }

        try {
            Scanner sc = new Scanner(f);
             int row=0;
             int size=0;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] details = line.split(",");
                size=details.length;
                if(from_file==null)
                     from_file=new String[size][size];
                int i=0;
                for( i=0; i<details.length;i++){
                    from_file[row][i]=details[i];
                }
            row++;
            }
        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
         Scanner scan1 = new Scanner(System.in);
        System.out.println("Enter the width for Beam Search");
        int width=scan1.nextInt();
        Node ini=new Node(from_file);
        ini.set_x_access(0);
        ini.set_y_access(0);
        Node go=new Node(from_file);
         System.out.print("=========ASTAR with Manhattan Distance===============");
         long startTime = System.nanoTime();
        ASTAR astar= new ASTAR(ini,go,2);
        long endTime = System.nanoTime();
       long duration = (endTime - startTime)/1000000;
       System.out.println("The duration time is "+duration +" milliseconds");
        System.out.print("=========ASTAR with h1===============");
        startTime = System.nanoTime();
        ASTAR astar1= new ASTAR(ini,go,1);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
         System.out.println("The duration time is "+duration +" milliseconds");
        System.out.print("========UCS================");
        startTime = System.nanoTime();
        UCS usc=new UCS(ini, go);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        System.out.println("The duration time is "+duration +" milliseconds");
         System.out.print("=========DFS===============");
         startTime = System.nanoTime();
        DFS dfs=new DFS(ini, go);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        System.out.println("The duration time is "+duration +" milliseconds");
        System.out.print("==========BFS==============");
         startTime = System.nanoTime();
         BFS Bfs=new BFS(ini, go);
         endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        System.out.println("The duration time is "+duration +" milliseconds");
          System.out.print("=========Beam Search with Manhattan Distance===============");
           startTime = System.nanoTime();
           // b3=new b3(initial state, goal state, type of h(n), width)
        B3 b31= new B3(ini,go,2,width);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        System.out.println("The duration time is "+duration +" milliseconds");
        System.out.print("=========Beam Search with h1===============");
        startTime = System.nanoTime();
        B3 b32= new B3(ini,go,1,width);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        System.out.println("The duration time is "+duration +" milliseconds");
          System.out.print("=========HC with Manhattan Distance===============");
          startTime = System.nanoTime();
        HC hc= new HC(ini,go,2);
         endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
       System.out.println("The duration time is "+duration +" milliseconds");
        System.out.print("=========HC with h1===============");
        startTime = System.nanoTime();
        HC ch= new HC(ini,go,1);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
         System.out.println("The duration time is "+duration +" milliseconds");
                }else{
                    System.out.println("This input file does not exist!");
                }
    }
    
    
}
