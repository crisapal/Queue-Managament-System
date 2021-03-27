public class Client implements Comparable<Client> {
    private int ID = 0;
    private int timeArrival = 0, timeService = 0;


    public Client(int ID, int timeArrival, int timeService) {
        this.ID = ID;
        this.timeArrival = timeArrival;
        this.timeService = timeService;
    }

    public void setTimeService(int timeService) {
        this.timeService = timeService;
    }

    @Override
    public int compareTo(Client x) {
        if ((x.timeArrival - timeArrival)==0){
            return timeService - x.timeService;}
        else
            return timeArrival - x.timeArrival;
    }


    public int getTimeArrival() {
        return timeArrival;
    }

    public int getTimeService() {
        return timeService;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "(" + ID +
                ", " + timeArrival +
                ", " + timeService +
                ") ";
    }
}
