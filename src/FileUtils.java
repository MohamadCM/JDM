import java.io.*;
import java.util.ArrayList;

/**
 * This class contains Utilities to read and write
 * needed things from file
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */

public  class FileUtils {
    /**
     * Writes each Download in a file (list.jdm)
     * @param queue is given queue
     */
    public static void writeDownload(Queue queue) {
        File file;

        file = new File("./files/list.jdm");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, true);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            for(Download d : queue.getDownloads())
                objectOutputStream.writeObject(d);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads Downloads from  list.jdm
     * @return
     */
    public static ArrayList<Download> readDownload()
    {
        ArrayList<Download> output = new ArrayList<Download>();
        Download download = null;
        File file = new File("./files/list.jdm");
        if(!file.exists())
            return null;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            download = (Download) objectInputStream.readObject();
            output.add(download);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Read default settings from setting.jdm
     * @return a Default Objects contains defaults
     */
    public static Defaults readDefaults()
    {
        Defaults output = null;
        File file = new File("./files/setting.jdm");
        if(!file.exists())
            return output;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            output = (Defaults) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Write Default settings in a file
     * @param defaults is a Default objects that contains default settings
     */
    public static void writeDefaults(Defaults defaults) {
        File file;
        file = new File("./files/setting.jdm");
        if(file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(defaults);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}