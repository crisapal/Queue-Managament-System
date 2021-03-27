public class Clock implements Runnable {

    private int time=0;
    private int perioadaSimulare=0;

    public Clock(int simT){
        perioadaSimulare=simT;
    }
    @Override
    public void run() {
        while(time<=perioadaSimulare) {
            time++;
            try {
                Thread.sleep(1000); ///numar cate o secunda
            }catch(Exception e) {
                Thread.currentThread().interrupted();
            }
        }

    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

}
