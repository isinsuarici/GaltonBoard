package estu.ceng.edu;
import java.util.Random;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

class Options { //for taking various arguments.
    @Option(name = "-numThread", usage = "Number of threads")
    int numThreads;
    @Option(name = "-numBins", usage = "Number of bins")
    int numBins;
}
public class Main {
    public static void main(String[] args) throws CmdLineException, InterruptedException {
        Options options= new Options();
        CmdLineParser parser= new CmdLineParser(options);
        parser.parseArgument(args);

        int sum=0;
        data d = new data();
        d.val= new int[options.numBins];

        for (int i = 0; i < options.numThreads; i++) {
            PlaceBall placeBall = new PlaceBall(d);
            Thread thread_i = new Thread(placeBall);
            thread_i.start();
            thread_i.join();
        }
        for (int i = 0; i < d.val.length; i++) {
            System.out.println(i + "      " + d.val[i]);
            sum+=d.val[i];
        }
        System.out.println("Number of requested thread: " + options.numThreads);
        System.out.println("Sum of bin values: "+ sum);
        if(sum==options.numThreads)
            System.out.println("Nice work! Both of them are equal!");
    }
}
class PlaceBall implements Runnable {
    data d;
    public PlaceBall(data d) {
        this.d = d;
    }

    @Override
    public void run() {
        Random rand = new Random();
        synchronized (d) {
            int bin_no = 0;
            int select;
            for (int i=0; i<d.val.length; i++) { //make choose operation number of numBins times
                select = rand.nextInt(2);
                if (select==1 && bin_no < d.val.length-1)
                    bin_no+= 1; //if select is equal to 1, move 1 to right.
                //if select is equal to 0, keep the ball in the same place
            }
            d.val[bin_no] += 1; //place ball
        }
    }
}
class data{
    int[] val;
}
