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
public final class BFS {
    private final Set<Node> Exapand;
    private final Set<String> OK;
    private final Queue<Node> queue;
    private final Node State;
    private final Node Goal;
    public BFS(Node initial, Node Goaln){
        this.queue =  new LinkedList<>();// create queue contains all nodes that need to expand
        this.Exapand=new HashSet<Node>();// expantion list
        this.OK=new HashSet<String>();
        this.Goal=Goaln;// goal state
        this.State=initial;// initial state
        State.set_lable(0);
        State.set_path_cost(0);
        State.set_data(State.get_data_position(0,0));
        State.set_concat(0, 0);
        Goal.set_data("G");
        queue.add(State);
        State.set_concat(0, 0);
        OK.add(State.get_concat());
        boolean x=true;
        Node Node_name =new Node();
        while(!queue.isEmpty()){// check if the queue is empty
           Node_name=queue.remove();// get the node from queue to expand
           Exapand.add(Node_name);
           // check if the node is the goal state
           if(Node_name.get_data().equals( (Goal.get_data()))){//compare with the unique number not the name of the node.
                 x=false;
                 // print the result if the solution is found
                 Node_name.print_Routing(Node_name);
                 System.out.println("space complexity = "+Exapand.size());
                 return;
              }
            if(x){
              //Exapand.add(Node_name);
              Frontiers(Node_name);
             }
          }
          if(queue.isEmpty() && x==true){
              System.out.println("\nThe Frontier is Empty\n No Solution found!");
              System.out.println("space complexity = "+Exapand.size());
          }
    }
     public void Frontiers(Node node_get_child){// used to generate new nodes from parent node.
          List<Node> successors = node_get_child.Generate_Seccussor(node_get_child);
           for (Node child : successors) {
               if(!OK.contains(child.get_concat())|| child.get_data().equals("O")){// caculate the actual cost g(n) 
                   OK.add(child.get_concat());
                   if (!Exapand.contains(child) && !queue.contains(child)){
                       // claculate the actual cost, and replace it with min cost.
                     if(child.get_lable()>child.get_parent().get_lable()+child.get_cost_path()){
                        child.set_lable((double)child.get_parent().get_lable()+child.get_cost_path());
                                }
                     queue.add(child);// add the child to queue
                            }
               }
            }
     }
}
