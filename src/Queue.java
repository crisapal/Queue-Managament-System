import java.util.concurrent.TimeUnit;

public class Queue implements Runnable {
    private int queueNumber = 0;
    private boolean isOpen = false;
    private int timpTotal = 0;
    private Client servingClient = new Client(-1, 0, 0);

    public Queue(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public boolean isOpen() {
        return isOpen;
    }


    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Client getServingClient() {
        return servingClient;
    }

    public void setServingClient(Client servingClient) { ///de fiecare data cand am un client nou in coada, inseamna ca il setez ca serving client
        this.servingClient = servingClient;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                Thread.currentThread().interrupted();
            }
            if (isOpen == true)
                timpTotal++;
            if (servingClient.getTimeService() - 1 == 0) {
                isOpen = false;
            }
            servingClient.setTimeService(servingClient.getTimeService() - 1);

        }
    }

    public int getTimpTotal() {
        return timpTotal;
    }
}
