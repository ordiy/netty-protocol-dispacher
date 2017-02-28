package my.ordiy.git;

/**
 * Created by pingxin on 2016/12/18.
 */
public class BootStrapServer {
    public static void main(String[] args) {
        new Thread(new TransportLaunchPad(8090)).start();
    }
}
