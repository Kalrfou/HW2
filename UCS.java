package ai_hw2;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Alrfou
 */
public final class UCS {
      private final Set<Node> Exapand;
    private final Set<String> OK;
    private final PriorityQueue<Node> priorityQueue;
    private final Node State;
    private final Node Goal;
    
    public UCS(Node initial, Node Goaln){
        this.priorityQueue = new PriorityQueue<>(7,new Node(initial.get_array()));
        this.Exapand=new HashSet<Node>();
        this.OK=new HashSet<String>();
        this.Goal=Goaln;
        this.State=initial;// initialize state node
        State.set_lable(0);// set label by zero
        State.set_path_cost(0);
        State.set_h1(State.H1(State));//call h1
        State.set_data(State.get_data_position(0,0));//get the position of initial state which (0,0).
        State.set_concat(0, 0);
        //State.set_h2(State.Manhattan_Distance(State,Goal));
        State.set_h2(0);
        State.set_Hlable(0);
        Goal.set_data("G");
        priorityQueue.add(State);// add initial state to priority queue.
        State.set_concat(0, 0);
        OK.add(State.get_concat());
     boolean x=true;
     Node Node_name =new Node();
     while(!priorityQueue.isEmpty()/*&&x*/){
         Node_name=get_Node_WithMin_Distance_FromQueue();// get the node with min value.
         Exapand.add(Node_name);
         //print the path and the actual cost from initial state to goal state, and complexity space if there is a solution.
         if(Node_name.get_data().equals( (Goal.get_data()))){//compare with the unique number not the name of the node.
            x=false;
            Node_name.print_Routing(Node_name);// call function to print the path.
             System.out.println("space complexity  = "+Exapand.size());
             return ;
         }
        if(x){
         Frontiers(Node_name);// call the fromtiers function to generate new nodes from parent node
        }
     }
     if(priorityQueue.isEmpty() && x==true){// print the number of nodes that expand when there is no solution.
                System.out.println("\nThe Frontier is Empty\n No Solution found!");
                System.out.println("space complexity  = "+Exapand.size());
     }
    }
     public void Frontiers(Node node_get_child){
         //call the generate_seccussor to genrete the childern from parent.
          List<Node> successors = node_get_child.Generate_Seccussor(node_get_child);
           for (Node child : successors) {
               if(!OK.contains(child.get_concat())|| child.get_data().equals("O")){
                   OK.add(child.get_concat());
                if (!Exapand.contains(child) && !priorityQueue.contains(child)){
                    // find get the min actual cost from initial state to child node
                   if(child.get_lable()>child.get_parent().get_lable()+child.get_cost_path()){
                       // claculate the actual cost, and replace it with min cost.
                        child.set_lable((double)child.get_parent().get_lable()+child.get_cost_path());
                        //f(n) function =g(n)
                         child.set_Hlable((double) (child.get_parent().get_lable()+child.get_cost_path()));
                      
                                }
                                priorityQueue.add(child);// add the new child to priority queue.
                            }
               }
           }
     }
     //return  the node with mini value.
     public Node get_Node_WithMin_Distance_FromQueue()
    {
        Node n=priorityQueue.remove();// get the node with min value.
        return n;
    }
}
