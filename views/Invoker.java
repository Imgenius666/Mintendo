package views;
import java.util.*;

public class Invoker{

    PriorityQueue<Command> commandQueue;

        public Invoker(){
            commandQueue = new PriorityQueue<>();
        }

        public void placeCommand(Command c){
            commandQueue.add(c);
        }

        public void execute(){
            if(commandQueue.isEmpty()) return;
            Command c = commandQueue.remove();
            c.execute();
        }



}




