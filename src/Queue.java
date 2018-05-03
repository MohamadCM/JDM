import java.util.ArrayList;

/**
 * Each queue consist of a number Download Objects
 * that gonna be downloaded in order
 * getQueue should be used to get the arrayList of downloads
 * @author Mohamad Chaman-Motalgh
 * @version 1
 */
public class Queue {
    private ArrayList<Download> downloads;
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
        downloads.add(download);
    }
    /**
     * Used to remove a  Download from queue
     * @param download is the download that gonna be removed
     */
    public void removeDownload(Download download)
    {
        downloads.remove(download);
    }

    /**
     * @return the arrayList of Downloads
     */
    public ArrayList<Download> getDownloads() {
        return downloads;
    }
}
