/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ai_hw2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Alrfou
 */
public final class HC {
   private final Set<Node> Exapand;
    private final Set<String> OK;
    private final PriorityQueue<Node> priorityQueue;
    private final Node State;
    private Queue<Node> qu;
    private final Node Goal;
    private final int type_h;
    public HC(Node initial, Node Goaln, int ty){
        this.type_h=ty;
        this.priorityQueue = new PriorityQueue<>(7,new Node(initial.get_array()));
        this.Exapand=new HashSet<Node>();
        this.OK=new HashSet<String>();
        qu=new LinkedList<>();
        this.Goal=Goaln;
        this.State=initial;
        State.set_lable(0);
        State.set_path_cost(0);
        if(ty==1)
        State.set_h1(State.H1(State));
        else
            State.set_h2(0);
        State.set_data(State.get_data_position(0,0));
        State.set_concat(0, 0);
        //State.set_h2(State.Manhattan_Distance(State,Goal));
        State.set_h2(0);
        State.set_Hlable(0);
        Goal.set_data("G");
            priorityQueue.add(State);
            State.set_concat(0, 0);
            OK.add(State.get_concat());
    // System.out.println("Start for checking the Shortest path");
     boolean x=true;
     Node Node_name =new Node();
     qu.add(State);
     while( !qu.isEmpty()){
         if(priorityQueue.isEmpty()) 
            priorityQueue.add(qu.remove());
         Node_name=get_Node_WithMin_Distance_FromQueue();
         insert();
         priorityQueue.clear();
          Exapand.add(Node_name);
         if(Node_name.get_data().equals( (Goal.get_data()))){//compare with the unique number not the name of the node.
            x=false;
            Node_name.print_Routing(Node_name);
             System.out.println("space complexity = "+Exapand.size());
             return;
           // System.out.println(Arrays.toString(Node_name.get_Parent_Node(Node_name).get_array()));
         }
           
         //System.out.println("The Total Nodes = "+Exapand.size());
        if(x){
        // Exapand.add(Node_name);
        // OK.add(Node_name.get_concat());
         Frontiers(Node_name);
         
        }
       
           
     }
     if( qu.isEmpty()){
         System.out.println("\nspace complexity = "+Exapand.size());
     }
        
        
    }
     public void Frontiers(Node node_get_child){// this function used to generte the new childern from parent node.
          List<Node> successors = node_get_child.Generate_Seccussor(node_get_child);
         // System.out.println(node_get_child.get_concat()+"    "+ node_get_child.get_data());
           for (Node child : successors) {// check if the child already in the  queue or not
               if(!OK.contains(child.get_concat())|| child.get_data().equals("O")){
                   OK.add(child.get_concat());
                if (!Exapand.contains(child) && !priorityQueue.contains(child)){
                   if(child.get_lable()>child.get_parent().get_lable()+child.get_cost_path()){
                       // claculate the actual cost, and replace it with min cost.
                        child.set_lable(child.get_parent().get_lable()+child.get_cost_path());
                        if(type_h==1)
                         child.set_Hlable((child.get_H1()));//f(n)=h(n)
                        else
                            child.set_Hlable((child.get_h2()));// f(n)=h(n)
                                    }
                                priorityQueue.add(child);
                                
                               // insert();
                                //qu.add(child);
                            }}
     }
     }
     public Node get_Node_WithMin_Distance_FromQueue()
    {
        Node n=priorityQueue.remove();// return the node with min value
        return n;
    }
  public void insert(){// add the Neighbors after take the node that has min value.
      while(!priorityQueue.isEmpty()){
          qu.add(priorityQueue.remove());
      }
  }
}
