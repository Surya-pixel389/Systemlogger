import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Systemlog{
    //we will get the gateway to the operating system
    private OperatingSystemMXBean os ;

    //we will give a time stamp so we can get the exact date of the memory usage
    public String timeGet(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //for operating system
    public Systemlog() {
        try{
            os =(OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        }catch(ClassCastException e){
            System.err.println("Failed to cast the operating system mx bean: "+ e.getMessage());
            os= null;
        }
    }

    //CPU
    public double getCPUload(){
        try{
            return os != null ? os.getCpuLoad() * 100 : -1;
        }catch(Exception e){
            System.err.println("Error fetching Cpu load: " + e.getMessage());
            return -1;
        }
    }
    //using the file library to get the disk usage
    public long getdiskUsage(){
        try{File f = new File("/"); // here the / is used to get the specific disk
            long totalspace = f.getTotalSpace();
            long freespace = f.getFreeSpace();
            return (totalspace - freespace) /1024/1024/1024; //calculate the used disk size in GB
        }catch(SecurityException e){
            System.err.println("Access denied to check disk: " + e.getMessage());
            return -1;
        }
    }
    //Ram
    public long getRamUsage(){
        try {
            long totalMemory = os.getTotalPhysicalMemorySize();
            long freeMemory = os.getFreePhysicalMemorySize();
            return (totalMemory - freeMemory) / 1024 / 1024; //calculate the used ram in MB
        }catch(Exception e){
            System.err.println("Error fetching the ram usage: "+ e.getMessage());
            return -1;
        }
    }

    //print the stats
    public void printStat(){

        System.out.println("Timestamp: " + timeGet() );
        System.out.println("Cpu usage:" + getCPUload());
        System.out.println("Disk usage: "+ getdiskUsage() + "GB");
        System.out.println("Ram usage: " + getRamUsage() + "MB");

    }
    public static void main(String[] args){
        Systemlog sy = new Systemlog();
        sy.printStat();
       
    }

}