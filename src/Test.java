

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Test {

    private static Scanner sc;
    private static int customersNumber = 0, queuesNumber = 0, simT = 0, minAriv = 0, maxServ = 0, minServ = 0, maxAriv = 0,i=0;


    public static void main(String[] args) { //THIS APP SHOULD BE LAUNCHED VIA A JAR FILE
        if (args.length != 2) {
            System.out.println("ERROR IN READING THE ARGS");

        }
        try {
            readFile(args);
            QueueSimulator q = new QueueSimulator(queuesNumber, simT, customersNumber, args[1]);
            for (int i = 1; i <= customersNumber; i++)
                setRandomClient(q, i);
            q.idMake();
            q.simulate();
            q.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void readFile(String[] args) throws Exception {
        sc = new Scanner(new File(args[0]));
        customersNumber = sc.nextInt();
        queuesNumber = sc.nextInt();
        simT = sc.nextInt();
        String buffer = sc.nextLine();
        buffer = sc.nextLine();
        while (i < buffer.length() && buffer.charAt(i) != ',')
            minAriv = minAriv * 10 + (int) (buffer.charAt(i++) - '0');
        i++;
        while (i < buffer.length())
            maxAriv = maxAriv * 10 + (int) (buffer.charAt(i++) - '0');
        buffer = sc.nextLine();
        i = 0;
        while (i < buffer.length() && buffer.charAt(i) != ',')
            minServ = minServ * 10 + (int) (buffer.charAt(i++) - '0');
        i++;
        while (i < buffer.length())
            maxServ = maxServ * 10 + (int) (buffer.charAt(i++) - '0');
    }

    public static void setRandomClient(QueueSimulator q, int i) {
        Random rand = new Random();
        q.addClients(i, rand.nextInt(maxAriv - minAriv + 1) + minAriv,
                rand.nextInt(maxServ - minServ + 1) + minServ);
    }


}

