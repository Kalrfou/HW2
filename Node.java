/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ai_hw2;

import java.util.*;
import java.util.Comparator;
import java.lang.Math;


/**
 *
 * @author Alrfou
 */
/*
L:LEFT, R: RIGHT, U: UP, UR:UP-RIGHT, UL: UP-LEFT, DR: DOWN-RIGHT, DOWN-LEFT.
*/
public class Node implements Comparator<Node> {
    private double label; // the actual cost from initail state to the current state
    private String data;// the direction value inside the block (x,y).
    private double H_lable;// the value of F function = g(n) + h(n)
    private int size;// The size of array.
    private Node parent;// get the parent of the child generated
    private String xy;// the value of contact x and y accesses.
    private double path_cost;// the actual cost from the parent to  the child
    private double H1;// huersitic function 1
    private double h2;// huersitic function 2
    private double h3;// huersitic function 3 for the Optimization search
    private int x_access;// get x access for the child and parent
    private int y_access;// get the y access for child and parent
    private String[][]a;// the Maze array
    public Node(){
        
    }
    public Node(String b[][]){
        this.a=b;
        this.size=a.length;
    }
    public String get_data_position(int x, int y){// return the direction inside the block.
        return a[x][y];
    }
    public void set_h1(double h1){// set the value for the huersitic function 1
        this.H1=h1;
    }
    public void set_h2(double h2){// set the value for the huersitic function 2
        this.h2=h2;
    }
    public double get_H1(){// get the value of the huersitic function 1
        return this.H1;
    }
    public double get_h2(){// get the value of the huersitic function 2
        return this.h2;
    }
    public double get_h3(){// get the value of the huersitic function 
        return this.h3;
    }
    public String[][] get_array(){// return the array that contains the map
        return this.a;
    }
    public void set_array(String[][] arr){
        this.a=arr;
    }
    public void set_lable(double l){//set the actual cost from start node to current node ==> g(n)
        this.label=l;
    }
    public double get_lable(){ // return the actual cost from start node to current node ==> g(n)
        return this.label;
    }
    public String get_data(){// get the diraction in that position.
        return data;
    }
    public void set_data(String d){
        this.data=d;
    }
   
    public double get_Hlable(){ // f(n)=g(n) + h(n)
        return this.H_lable;
    }
    public void set_Hlable(double x){//f(n)=g(n) + h(n)
        this.H_lable=x;
    }
    public Node get_parent(){ // return the parent of the node
       return this.parent;
    }
    public void set_parent(Node n){// set the parent of the node.
        this.parent=n;
    }
    public int get_x_access(){// return the the x access of the current node
        return this.x_access;
    }
    public void set_x_access(int x){// set the x access of the current node
        this.x_access=x;
    }
    public int get_y_access(){// return the the y access of the current node
        return this.y_access;
    }
    public double get_cost_path(){// return the actual cost to the current node
         return this.path_cost;
    }
    public void set_path_cost(double x){// set the actual cost to the current node
        this.path_cost=x;
    }
    public void set_y_access(int y){// set the the y access of the current node
        this.y_access=y;
    }
    // find the Euclidean distance from parent to child 
    public double Euclidean_Distance(int x1,int x2, int y1, int y2){
       // System.out.println("the ED is "+ x1+" "+x2+" "+y1+"  "+y2+"  "+(Math.sqrt(Math.abs(Math.pow((x1-x2), 2)+ Math.pow((y1-y2), 2)))));
        return (double)(Math.sqrt(Math.abs(Math.pow((x1-x2), 2)+ Math.pow((y1-y2), 2))));
    }
    // find the manhattan distance from parent to child ==>The heuristic function h2
    public double Manhattan_Distance(Node n1, Node n2){
        double x=Math.abs((Math.abs(n1.get_x_access()-n2.get_x_access())+ Math.abs(n1.get_y_access()-n2.get_y_access())));
        x=x/2;
        x=Math.round(x);
        return x;
    }
    // Calculate the heuristic function h1
    public double H1(Node n1){
        double h=0;
        if(n1.get_x_access()!=a.length-1)
            h+=1;
        if(n1.get_y_access()!=a.length-1)
            h+=1;
        return h;
    }
    public void set_concat(int x, int y){// concat x and y access. use to check if the node is already visited or not.
        this.xy= Integer.toString(x)+ Integer.toString(y);
    }
    public String get_concat(){// accessory function that return the cancat between x and y access.
        return xy;
    }
    // For the hill climbing the heuristic function H3
    public double Hill_climbing_H3(Node n1, Node n2){
        return 0;
    }
    public boolean check_x_border(int x){// check if x hit the left or right edge of the matrix.
        return x >= 0 && x < a.length;
    }
     public boolean check_y_border(int y){// check if x hit the up or down edge of the matrix.
        return y >= 0 && y < a.length;
    }
     // this function called helper function,calculate the cost path,, heuristic functions (h1 and h2), between two nodes, 
    public Node Add_Node_data(Node parent, Node child){
               child.set_path_cost(child.Euclidean_Distance(parent.get_x_access(), child.get_x_access(), parent.get_y_access(), child.get_y_access()));
               child.set_lable(Double.MAX_VALUE);// initial value will be infinity
               child.set_h2(Manhattan_Distance(parent, child));// find the heuristic function h2
               child.set_Hlable(Double.MIN_VALUE);// initial value will be infinity
               child.set_h1(H1(child));// find the heuristic function h2
               child.set_parent(parent);// add the parent node 
               child.set_array(parent.a);
               child.set_concat(child.get_x_access(), child.get_y_access());
               return child;
    }
   public List<Node> Generate_Seccussor(Node n){
       List<Node> successors = new ArrayList<Node>();
       if(n.get_data().equals("R")){// generate all childern nodes if the current node is right
           for( int i=n.get_y_access()+1;i< a.length;i++){
               Node p=new Node();
               p.set_x_access(n.get_x_access());
               p.set_y_access(i);
               p.set_data(a[n.get_x_access()][i]);
               p=Add_Node_data(n, p);
               successors.add(p);
           }
       }
          if(n.get_data().equals("L")){// generate all childern if the current node is left
           for( int i=n.get_y_access()-1;i>=0;i--){
               Node p=new Node();
               p.set_x_access(n.get_x_access());
               p.set_y_access(i);
               p.set_data(a[p.get_x_access()][p.get_y_access()]);
               p=Add_Node_data(n, p);
               successors.add(p);
           }
       }
         if(n.get_data().equals("U")){// generate all childern if the current node is up
            int i=n.get_x_access();
             while(i>0){
                 i=i-1;
               Node p=new Node();
               p.set_y_access(n.get_y_access());
               p.set_x_access(i);
               p.set_data(a[p.get_x_access()][p.get_y_access()]);
               p=Add_Node_data(n, p);
               successors.add(p);
           }
       }
         if(n.get_data().equals("D")){// generate all childern if the current node is Down
          int i=n.get_x_access();
             while(i<a.length-1){
                 i=i+1;
               Node p=new Node();
               p.set_y_access(n.get_y_access());
               p.set_x_access(i);
               p.set_data(a[p.get_x_access()][p.get_y_access()]);
               p=Add_Node_data(n, p);
               successors.add(p);
           }
       }
         if(n.get_data().equals("DR")){// generate all childern if the current node is  down right
             int i=n.get_x_access();
             int j= n.get_y_access();
             while( i < a.length-1 && j< a.length-1){
               i=i+1;
               j=j+1;
               Node p=new Node();
               p.set_y_access(j);
               p.set_x_access(i);
               p.set_data(a[i][j]);
               p=Add_Node_data(n, p);
               successors.add(p);
             }
         }
         if(n.get_data().equals("DL")){// generate all childern if the current node is down left
             int i=n.get_x_access();
             int j= n.get_y_access();
             while( i <a.length-1 && j>0){
               i=i+1;
               j=j-1;
               Node p=new Node();
               p.set_y_access(j);
               p.set_x_access(i);
               p.set_data(a[i][j]);
               p=Add_Node_data(n, p);
               successors.add(p);
             }
         }
         if(n.get_data().equals("UL")){// generate all childern if the current node is up left
             
             int i=n.get_x_access();
             int j= n.get_y_access();
             while( i >0 && j>0){
               i=i-1;
               j=j-1;
               Node p=new Node();
               p.set_y_access(j);
               p.set_x_access(i);
               p.set_data(a[i][j]);
               p=Add_Node_data(n, p);
               successors.add(p);
             }
         }
         if(n.get_data().equals("UR")){// generate all childern if the current node is up right
               int i=n.get_x_access();
             int j= n.get_y_access();
             while( i >0 && j< a.length-1){
               i=i-1;
               j=j+1;
               Node p=new Node();
               p.set_y_access(j);
               p.set_x_access(i);
               p.set_data(a[i][j]);
               p=Add_Node_data(n, p);
               successors.add(p);
             }
         }
         if(n.get_data().equals("O")){// generate all childern if the current node is O
             if(n.get_parent().get_data().equals("R")){
                 // increse y only
                 int yy=n.get_parent().get_y_access();
                 if(check_y_border(yy))
                 {
                     while(yy>0){
                         yy=yy-1;
                     Node p=new Node();
                     p.set_x_access(n.get_parent().get_x_access());
                     p.set_y_access(yy);
                     p.set_data(a[p.get_x_access()][p.get_y_access()]); 
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("L")){
                 int yy=n.get_parent().get_y_access();
                 if(check_y_border(yy))
                 {
                     while(yy<a.length-1){
                         yy=yy+1;
                     Node p=new Node();
                     p.set_x_access(n.get_parent().get_x_access());
                     p.set_y_access(yy);
                      p.set_data(a[p.get_x_access()][p.get_y_access()]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("U")){
                 //decrese x only
                 int yy=n.get_parent().get_x_access();
                 if(check_x_border(yy))
                 {
                     while(yy<a.length-1){
                         yy=yy+1;
                     Node p=new Node();
                     p.set_x_access(yy);
                     p.set_y_access(n.get_parent().get_y_access());
                      p.set_data(a[p.get_x_access()][p.get_y_access()]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("D")){
                 //increase x only
                 int yy=n.get_parent().get_x_access();
                 if(check_x_border(yy))
                 {
                     while(yy>0){
                         yy=yy-1;
                     Node p=new Node();
                     p.set_x_access(yy);
                     p.set_y_access(n.get_parent().get_y_access());
                      p.set_data(a[p.get_x_access()][p.get_y_access()]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("DR")){
                 //decrease x and y
                 int yy=n.get_parent().get_y_access();
                 int xx=n.get_parent().get_x_access();
                 if(check_x_border(xx)&& check_y_border(yy))
                 {
                     while(yy>0 && xx> 0){
                         yy=yy-1;
                         xx=xx-1;
                     Node p=new Node();
                     p.set_x_access(xx);
                     p.set_y_access(yy);
                     p.set_data(a[xx][yy]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("DL")){
                //decrease x and increase y 
                 int yy=n.get_parent().get_y_access();
                 int xx=n.get_parent().get_x_access();
                 if(check_x_border(xx)&& check_y_border(yy))
                 {
                     while(yy<a.length-1 && xx>0){
                         yy=yy+1;
                         xx=xx-1;
                     Node p=new Node();
                     p.set_x_access(xx);
                     p.set_y_access(yy);
                      p.set_data(a[xx][yy]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("UL")){
               //increase x and decrease y 
                 int yy=n.get_parent().get_y_access();
                 int xx=n.get_parent().get_x_access();
                 if(check_x_border(xx)&& check_y_border(yy))
                 { 
                     while(yy <a.length-1 && xx< a.length-1){
                         yy=yy+1;
                        xx=xx+1;
                     Node p=new Node();
                     p.set_x_access(xx);
                     p.set_y_access(yy);
                      p.set_data(a[xx][yy]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
             if(n.get_parent().get_data().equals("UR")){
                //increse x and y 
                 int yy=n.get_parent().get_y_access();
                 int xx=n.get_parent().get_x_access();
                  if(check_x_border(xx)&& check_y_border(yy))
                 {
                     while(yy >0 && xx<a.length-1){
                         yy=yy-1;
                         xx=xx+1;
                     Node p=new Node();
                     p.set_x_access(xx);
                     p.set_y_access(yy);
                      p.set_data(a[xx][yy]);
                     p=Add_Node_data(n.get_parent(), p);
                     p.set_parent(n);
                     successors.add(p);
                     }
                 }
             }
         }
       return successors;
   }
    public List<Node> Generate_sequence(Node n1){
        //use Rondom method to generate nodes
       List<Node> successors = new ArrayList<Node>();
       //We need to generate a sequence of nodes to find the path from initial node
       //to goal node using Hill-climbing.  We need to generate sequence from Start node to the Goal Node.
       //
       Random rand = new Random();
       for(int i=0; i<this.a.length; i++){
             int  n = rand.nextInt(7);
             if(n!=0 && n!=this.a.length-1){
                 Node p=new Node();
                 p.set_x_access(i);
                 p.set_y_access(n);
                 p.set_array(n1.a);
                 p.set_data(p.a[i][n]);
                 p.set_path_cost(p.Euclidean_Distance(n1.get_x_access(), p.get_x_access(), n1.get_y_access(), p.get_y_access()));
                 p.set_h1(H1(p));
                 p.set_h2(p.Manhattan_Distance(p, n1));
                 p.set_parent(n1);
                 p.set_concat(p.get_x_access(), p.get_y_access());
                 p.set_Hlable(Double.MAX_VALUE);
                 p.set_lable(Double.MAX_VALUE);
                 successors.add(p);
             }
       }
       return successors;
    }
    @Override
     public int compare(Node node1, Node node2)
    {
        if (node1.H_lable < node2.H_lable)
            return -1;
        if (node1.H_lable > node2.H_lable)
            return 1;
        if (node1.H_lable < node2.H_lable)
            return -1;
        return 0;
    }
    
    public void print_Routing(Node n){// this function print the path from the initial state to goal state.
     if (null == n) { //print if there is no solution
			System.out.print("\nNo solution found.");
		}else{
                        System.out.println("\nSolution");
			List<Node> path = new ArrayList<Node>();// get the path from parent to the state.
			Node state = n;
                        while(null!=state) {
				path.add(state);
				state = state.get_parent();
			}
                        int depth = path.size() - 1;
                        System.out.println("The depth is "+(depth+1));
			for (int i = depth; i >= 0; i--) {// print from initial state to the goal state.
				state = path.get(i) ;
                                // print the valid sequence from initial 
				System.out.print((state.get_data()+"("+path.get(i).get_x_access()+","+path.get(i).get_y_access()+") ==> "));
			}// print the cost from initial state to the goal state.
                        System.out.println("\nPath by number ");
                        System.out.print("[");
                        for (int i = depth; i >= 0; i--) {// print from initial state to the goal state.
				state = path.get(i) ;
                                // print the valid sequence from initial 
				System.out.print((state.get_x_access()* this.a.length+state.get_y_access()) +"  ");
			}
                        System.out.println("]");
                        System.out.println("\nThe cost of this : " + n.get_lable());
     }
 }
}
