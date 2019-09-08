/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ai_hw2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Alrfou
 */
public final class DFS {
    private final Set<Node> Exapand;
    private final Set<String> OK;
    private final Stack<Node> satck;
    private final Node State;
    private final Node Goal;
    public int k;
    public DFS(Node initial, Node Goaln){
        this.satck =new Stack<>();// create Stack contains all nodes that need to expand
        this.Exapand=new HashSet<Node>();// expantion list
        this.OK=new HashSet<String>();//
        this.Goal=Goaln;
        this.State=initial;// initial state
        State.set_lable(0);
        State.set_path_cost(0);
        State.set_h1(State.H1(State));// set the h1 to the first node.
        State.set_data(State.get_data_position(0,0));
        State.set_concat(0, 0);
        //State.set_h2(State.Manhattan_Distance(State,Goal));
        State.set_h2(0);
        State.set_Hlable(0);
        Goal.set_data("G");
        State.set_concat(0, 0);
        satck.add(State);// add the initial node to stack.
        OK.add(State.get_concat());
        boolean x=true;
        Node Node_name =new Node();
        while(!satck.isEmpty()){
            Node_name=satck.pop();
            Exapand.add(Node_name);
            if(Node_name.get_data().equals( (Goal.get_data()))){//compare with the unique number not the name of the node.
               x=false;
               Node_name.print_Routing(Node_name);// print the path from initial state to the goal state, and print the actual cost.
                System.out.println("space complexity = "+Exapand.size());// print the number of node that expand.
                return;
            }
           if(x){
            Frontiers(Node_name);// call the function to generate new childern from the parent.
           }
           }
            if(satck.isEmpty() && x==true){// print the total number of node that were expanded when there is no solution found.
                System.out.println("\nThe Frontier is Empty\n No Solution found!");
                System.out.println("space complexity  = "+Exapand.size());
            }
    }
     public void Frontiers(Node node_get_child){// used to generate new nodes from parent node.
          List<Node> successors = node_get_child.Generate_Seccussor(node_get_child);
           for (Node child : successors) {// check the child if is already in the stack or not
               if(!OK.contains(child.get_concat())|| child.get_data().equals("O")){
                   OK.add(child.get_concat());
                if (!Exapand.contains(child) && !satck.contains(child)){// caculate the actual cost g(n)
                    // claculate the actual cost, and replace it with min cost.
                   if(child.get_lable()>child.get_parent().get_lable()+child.get_cost_path()){// caculate the actual cost.
                        child.set_lable((double)child.get_parent().get_lable()+child.get_cost_path());
                                }
                                satck.push(child);// add the new child to the stack.
                            }
               }
        }
     }
}
