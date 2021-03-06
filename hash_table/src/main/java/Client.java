import org.jgroups.protocols.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws Exception {
        new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.0.2.15"));

        DistributedMap map = new DistributedMap("OperationChannel");

        boolean running = true;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            System.out.print("> ");
            System.out.flush();
            String line = in.readLine();
            String[] tokens = line.split("\\s+");
            if (tokens.length < 1) continue;

            switch (tokens[0]) {
                case "exit":
                case "quit":
                    running = false;
                    break;
                case "get":
                    Integer val = map.get(tokens[1]);
                    System.out.println("  " + val);
                    break;
                case "getAll":
                    map.printAll();
                    break;
                case "put":
                    map.put(tokens[1], Integer.parseInt(tokens[2]));
                    break;
                case "contains":
                case "containsKey":
                    Boolean result = map.containsKey(tokens[1]);
                    System.out.println("  " + tokens[1] + " : " + result);
                    break;
                case "remove":
                    map.remove(tokens[1]);
                    break;
                case "":
                    break;
                default:
                    System.out.println("  error: unknown operation.");
                    break;
            }
        }
        map.delete();
    }
}
