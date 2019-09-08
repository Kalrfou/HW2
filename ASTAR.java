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
public final class ASTAR {
    private final Set<Node> Exapand;
    private final Set<String> OK;
    private final PriorityQueue<Node> priorityQueue;
    private Node State;
    private Node Goal;
    private final int type_h;
    public ASTAR(Node initial, Node Goaln, int ty){
        this.type_h=ty;
        this.priorityQueue = new PriorityQueue<>(7,new Node(initial.get_array()));// contains the nodes that will be expanded.
        this.Exapand=new HashSet<Node>();// contains the nodes that already expanded
        this.OK=new HashSet<String>();
        initial(initial,Goaln);//call the function initial to initiate the initial State and the goal State.
        // caclulate the heuristic function h1 and h2.
        if(ty==1)
            State.set_h1(State.H1(State));
        else
            State.set_h2(6.0);
         priorityQueue.add(State);// add the initil state to priority queue for expanding.
         State.set_concat(0, 0);
         OK.add(State.get_concat());
         boolean x=true;
         Node Node_name =new Node();
         while(!priorityQueue.isEmpty()){// start the process of ASTAR
                Node_name=get_Node_WithMin_Distance_FromQueue();// get the node with min value
                Exapand.add(Node_name);// add the node to expand list
                // check the node if it is the goal node
                if(Node_name.get_data().equals( (Goal.get_data()))){//compare with the unique number not the name of the node.
                   x=false;
                   Node_name.print_Routing(Node_name);// print the path from initial state to the goal state, and print the actual cost.
                   // print the total number of nodes generated.
                   System.out.println("space complexity  = "+Exapand.size());// print the space complexity
                   return;
                }

               if(x){
                  Frontiers(Node_name);// call Frontiers to generate new childern
               }
            }
            if(priorityQueue.isEmpty() && x==true){// print the space complexity if there is no solution
                System.out.println("\nThe Frontier is Empty\n No Solution found!");
                System.out.println("space complexity= "+Exapand.size());

           }
    }
     public void Frontiers(Node node_get_child){
          List<Node> successors = node_get_child.Generate_Seccussor(node_get_child);
           for (Node child : successors) {
               if(!OK.contains(child.get_concat())|| child.get_data().equals("O")){
                   OK.add(child.get_concat());
                if (!Exapand.contains(child) && !priorityQueue.contains(child)){
                    // claculate the actual cost, and replace it with min cost.
                   if(child.get_lable()>child.get_parent().get_lable()+child.get_cost_path()){
                        child.set_lable(child.get_parent().get_lable()+child.get_cost_path());
                        if(type_h==1)// calculate the f(n)=g(n) + h(n). h(n) is h1.
                         child.set_Hlable((double) (child.get_parent().get_lable()+child.get_cost_path()+child.get_H1()));
                        else// calculate the f(n)=g(n) + h(n). h(n) is h2 which is Manhattan Distance.
                            child.set_Hlable((double) (child.get_parent().get_lable()+child.get_cost_path()+child.get_h2()));
                  // System.out.println(child.get_h2());             
                   }
                   // add the child to the priority queue.
                                priorityQueue.add(child);
                            }
               }
          }
     }
     // generate the initial state and goal state.
     public void  initial(Node initial, Node Goaln){
        this.Goal=Goaln;
        this.State=initial;
        State.set_lable(0);
        State.set_path_cost(0);
        State.set_data(State.get_data_position(0,0));
        State.set_concat(0, 0);
        State.set_h2(0);
        State.set_Hlable(0);
        Goal.set_data("G");
     }
     // this function return the node with min value from priority queue.
     public Node get_Node_WithMin_Distance_FromQueue()
    {
        Node n=priorityQueue.remove();
        return n;
    }
    
}
