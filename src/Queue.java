import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Each queue consist of a number Download Objects
 * that gonna be downloaded in order
 * getQueue should be used to get the arrayList of downloads
 * @author Mohamad Chaman-Motalgh
 * @version 1
 */
public class Queue implements Serializable {
    private static ArrayList<Download> downloads;
    private String name;

    /**
     * Each Queue needs a name to get created
     * @param name
     */
    public Queue(String name)
    {
        downloads = new ArrayList<Download>();
        this.name = name;
    }
    /**
     * Used to add a new Download to queue
     * @param download is the download that gonna be added
     */
    public void addDownload(Download download)
    {
        if(download == null)
            return;
        downloads.add(download);
    }
    /**
     * Used to remove a  Download from queue
     * @param i is index of the download that gonna be removed
     */
    public void removeDownload(int i)
    {
        downloads.remove(i);
    }

    /**
     * @return the arrayList of Downloads
     */
    public ArrayList<Download> getDownloads() {
        return downloads;
    }

    /**
     * @param d in given download
     * @return index of the download in queue
     */
    public static int getIndex(Download d)
    {
        return downloads.indexOf(d);
    }

    /**
     * Updates the queue by swapping download and etc.
     * @param indexOfDownload1
     * @param indexOfDownload2
     * @return {@code true} if the updating was successful, retrun {@code false} otherwise
     */
    public static boolean updateQueue(int indexOfDownload1, int indexOfDownload2) {
        try {
            Collections.swap(downloads, indexOfDownload1, indexOfDownload2);
            return true;
        }catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static void setDownloads(ArrayList<Download> downs)
    {
         downloads = downs;
    }

}
