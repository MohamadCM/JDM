import java.awt.*;
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

        ArrayList<DownloadInfo> output = new ArrayList<DownloadInfo>();
        for(Download d : queue.getDownloads())
            output.add(d.getDownloadInfo());
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
            objectOutputStream.writeObject(output);
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
        ArrayList<DownloadInfo> downloadInfos = new ArrayList<DownloadInfo>();
        ArrayList<Download> output = new ArrayList<Download>();

        File file = new File("./files/list.jdm");
        if(!file.exists())
            return null;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            downloadInfos = (ArrayList<DownloadInfo>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(DownloadInfo downloadInfo : downloadInfos)
        {
            output.add(new Download(downloadInfo.getName(),downloadInfo.getAddress(),downloadInfo.getVolume(),downloadInfo.getDownloadedVolume(),downloadInfo.getPercentDownload(),downloadInfo.getDownloadRate(),downloadInfo.getLink()));
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

    /**
     * Writes name and link of a removed Download to a file (removed.jdm)
     * @param download is removed download
     */
    public static void writeRemovedDownload(Download download)
    {
       File file = new File("./files/removed.jdm");
       if(!file.exists()) {
           try {
               file.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true))){
            bufferedWriter.write("Name: " + download.getName() + "\n");
            bufferedWriter.write("Link: " + download.getLink() + "\n\n\n");
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    /**
     * Opens the list of removed Downloads
     */
    public static void openRemovedList()
    {
        File file = new File("./files/removed.jdm");
        if(!file.exists())
            return;
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write blocked links into a text file (filter.jdm)
     * @param string is a String consists of filtered links
     */
    public static void writeBlockedLinks(String string)
    {
        File file = new File("./files/filter.jdm");
        string = string.toLowerCase();
        if(file.exists())
            file.delete();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            bufferedWriter.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads blocked links from filter.jdm
     * @return an array list of Strings
     */
    public static ArrayList<String> readBlockedLinks(){
        File file = new File("./files/filter.jdm");

        ArrayList<String> output = new ArrayList<String>();

        if(!file.exists())
            return null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String str;
            while ((str = bufferedReader.readLine()) != null)
                output.add(str);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Write queue in a file
     * @param queue is a given Queue to write
     */
    public static void writeQueue(Queue queue){
        File file = new File("./files/queue.jdm");

        if(file.exists())
            file.delete();

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))){
            objectOutputStream.writeObject(queue);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Queue readQueue( ) {
        File file;
        file = new File("./files/queue.jdm");
        Queue queue = null;
        if(!file.exists())
            return queue;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))){
            queue = (Queue) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return queue;
    }
}
