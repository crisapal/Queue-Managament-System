import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


public class QueueSimulator {
    private Thread[] queueThreads;
    private CopyOnWriteArrayList<Queue> queues;
    private CopyOnWriteArrayList<Client> clients = new CopyOnWriteArrayList<Client>();   ///vector is a structure since the beginning of Java which is Thread safety, even if iteration could be really costly in matter of time consumption

    private final int queuesNumber;
    private int simulationMaxTime = 0;
    private int averageTime = 0; ///the total time spent of a person after he was pushed into a queue
    private Clock time;
    private Thread timeThread;
    private int ok = 0;
    private int clientsNumber = 0;
    private FileWriter writer;

    public QueueSimulator(int queuesNumber, int simulationMaxTime, int clientsNumber, String fisier) throws IOException {
        queues = new CopyOnWriteArrayList<Queue>();
        this.queuesNumber = queuesNumber;
        this.simulationMaxTime = simulationMaxTime;
        this.clientsNumber = clientsNumber;
        queueThreads = new Thread[queuesNumber];

        writer = new FileWriter(fisier, false);
        for (int i = 0; i < queuesNumber; i++) {
            queues.add(new Queue(i + 1));
            queueThreads[i] = new Thread(queues.get(i));
        }
        time = new Clock(simulationMaxTime);
        timeThread = new Thread(time);
        timeThread.start();


    }

    public void addClients(int i, int timeAr, int timeService) { /// I am going to add clients in a sorted way by the the time they arrive and has to be served
        this.clients.add(new Client(i, timeAr, timeService));
        Collections.sort(this.clients);
    }

    public void printWaitinglist() throws IOException {
        writer.write("Waiting list: ");
        if (clients.isEmpty()) {
            writer.write("NO WAITING CLIENT\n");
        } else {
            int i;
            for (i = 0; i < clients.size() - 1; i++)
                writer.write(clients.get(i) + " ; ");
            writer.write(clients.get(i) + "\n");
        }

    }

    public void idMake(){
        int j=1;
        for(Client c:clients)
            c.setID(j++);
    }

    public void finish() {
        for (int i = 0; i < queues.size(); i++)
            try {
                queueThreads[i].stop();

            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            timeThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void printQueues() throws IOException {

        for (int i = 0; i < queues.size(); i++) {
            writer.write("Queue " + (i + 1) + ": ");
            if (queues.get(i).isOpen() == false)
                writer.write("is closed \n");
            else
                writer.write(queues.get(i).getServingClient() + "\n");
        }

    }


    public int stop() {
        if (clients.isEmpty()) {
            for (Queue c : queues)
                if (c.isOpen() == false)
                    ok++;
            if (ok != queues.size())
                return 0;
        }
        return ok;
    }

    public void processAvgTime() throws IOException {
        int customers = clientsNumber - clients.size(); ///number of customers which were processed before the queue ended
        double timp = 0;

        for (Queue c : queues)
            timp += c.getTimpTotal();
        writer.write("Average time is " + (timp / customers) + "\n");
    }

    public void simulate() throws IOException {
        while (time.getTime() <= simulationMaxTime && ok == 0) { //time will pass anyway
            ok = stop();
            writer.write("Time " + time.getTime() + "\n");
            for (int j = 0; j < queues.size(); j++)
                if (queues.get(j).isOpen() == false && !clients.isEmpty()) {
                    if (time.getTime() >= clients.get(0).getTimeArrival()) {
                        if (!queueThreads[j].isAlive())
                            queueThreads[j].start();
                        queues.get(j).setOpen(true);
                        queues.get(j).setServingClient(clients.get(0));
                        clients.remove(0);
                    }
                }
            printWaitinglist();
            printQueues();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        processAvgTime();
    }

    public void close() throws IOException {
        writer.close();
    }
}


