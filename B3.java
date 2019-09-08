/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ai_hw2;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
/**
 *
 * @author Alrfou
 */
public final class B3 {
     private final Set<Node> Exapand;// node already expanded
    private final Set<String> OK;
    private PriorityQueue<Node> priorityQueue;// contains the nodes that ready to expand
    private final Node State;
    private final Node Goal;
    private final int type_h;
    private final int width;// width of bea search
    public B3(Node initial, Node Goaln, int heur, int widths){
        this.width=widths;// add the width of beam search
        this.type_h=heur;// get the type of the heuristic function which is include h1 and h2.
        //contains the nodes that will be expanded.
        this.priorityQueue = new PriorityQueue<>(7,new Node(initial.get_array())); //contains the nodes that ready to expand
        this.Exapand=new HashSet<Node>();// create the list of expand nodes
        this.OK=new HashSet<String>();
        this.Goal=Goaln;
        this.State=initial;// assign the intial state
        State.set_lable(0);
        State.set_path_cost(0);
        // caclulate the heuristic function h1 and h2.
        if(type_h==1)
        State.set_h1(State.H1(State));
        else
            State.set_h2(0);
        // get the initial state position which is (0,0)
        State.set_data(State.get_data_position(0,0));
        State.set_concat(0, 0);
        //State.set_h2(State.Manhattan_Distance(State,Goal));
        State.set_h2(0);
        State.set_Hlable(0);
        Goal.set_data("G");
            priorityQueue.add(State);
            State.set_concat(0, 0);
            OK.add(State.get_concat());
     boolean x=true;
     Node Node_name =new Node();
     while(!priorityQueue.isEmpty()/*&&x*/){
         Node_name=get_Node_WithMin_Distance_FromQueue();// get the node that has min value
          Exapand.add(Node_name);
          // check if the node is the goal state
         if(Node_name.get_data().equals( (Goal.get_data()))){//compare with the unique number not the name of the node.
            x=false;
            // print the result if the solution is found
            System.out.println();
            System.out.println("The width is = "+this.width);// print the width
            // print the path from initial state to the goal state, and print the actual cost.
           Node_name.print_Routing(Node_name);
            // print the total number of nodes generated
             System.out.println("space complexity = "+Exapand.size());
             return;
         }
        if(x){
         Frontiers(Node_name);// call Frontiers to generate new childern
        }
     }
     if(priorityQueue.isEmpty() && x==true){
         // print the space complexity if there is no solution
         System.out.println("\nThe Frontier is Empty\n No Solution found with width ="+ this.width);
         System.out.println("space complexity  = "+Exapand.size());
     }
    }
     public void Frontiers(Node node_get_child){// use to generate node from parent node
          List<Node> successors = node_get_child.Generate_Seccussor(node_get_child);
           for (Node child : successors) {
               if(!OK.contains(child.get_concat())|| child.get_data().equals("O")){
                   if(priorityQueue.size()<width)// check the width
                        OK.add(child.get_concat());
                if (!Exapand.contains(child) && !priorityQueue.contains(child)){// caculate the actual cost g(n) and then calculate the f(n)
                   if(child.get_lable()>child.get_parent().get_lable()+child.get_cost_path()){
                       // claculate the actual cost, and replace it with min cost.
                        child.set_lable(child.get_parent().get_lable()+child.get_cost_path());
                        if(type_h==1)
                            // calculate the f(n)=g(n) + h(n). h(n) is h1.
                            child.set_Hlable((double) (child.get_parent().get_lable()+child.get_cost_path()+child.get_H1()));
                        if(type_h==2)
                            // calculate the f(n)=g(n) + h(n). h(n) is h2 which is Manhattan Distance.
                            child.set_Hlable((double) (child.get_parent().get_lable()+child.get_cost_path()+child.get_h2()));
                                }
                   // add the child to the priority queue.
                      priorityQueue.add(child);
                            }}
     }
           if(priorityQueue.size()>width){// check the width 
            priorityQueue= PQ_with_size3();
           }
     }
     public PriorityQueue<Node>  PQ_with_size3(){//  remove all nodes that are out of range.
         PriorityQueue<Node> pps=new PriorityQueue<>(7,new Node(State.get_array()));;
         for(int i=1; i<width; i++){
             Node pr=priorityQueue.remove();
             pps.add(pr);
         }
         return pps;
     }
     public Node get_Node_WithMin_Distance_FromQueue()// get the node with min value.
    {
        Node n=priorityQueue.remove();
        return n;
    }
    
}
