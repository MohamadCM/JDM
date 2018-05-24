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
        FileUtils.writeRemovedDownload(downloads.get(i));
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
    public int getIndex(Download d)
    {
        return downloads.indexOf(d);
    }

    /**
     * Updates the queue by swapping download and etc.
     * @param indexOfDownload1
     * @param indexOfDownload2
     * @return {@code true} if the updating was successful, retrun {@code false} otherwise
     */
    public boolean updateQueue(int indexOfDownload1, int indexOfDownload2) {
        try {
            Collections.swap(downloads, indexOfDownload1, indexOfDownload2);
            return true;
        }catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * sorts downloads by size , name and startTime
     * @param type is type of sort( "size" "name" "time" are acceptable
     * @param ascending if {@code true} sorts ascending , descending otherwise
     */
    public void sortBy(String type, boolean ascending)
    {
        for(int i = 0 ; i < downloads.size() - 1 ; i++)
            for (int j = 0 ; j < downloads.size() - i - 1 ; j++)
            {
                if(type.equals("name")) {
                    if(ascending == true)
                        if(downloads.get(j).getName().compareTo(downloads.get(j + 1).getName()) < 0)
                            Collections.swap(downloads, j , j + 1);
                    else
                        if(downloads.get(j).getName().compareTo(downloads.get(j + 1).getName()) > 0)
                            Collections.swap(downloads, j , j + 1);

                }
                else if(type.equals("size")){
                    if(ascending = true)
                        if(downloads.get(j).getVolume() < downloads.get(j + 1).getVolume())
                            Collections.swap(downloads, j , j + 1);
                else
                    if(downloads.get(j).getVolume() > downloads.get(j + 1).getVolume())
                        Collections.swap(downloads, j , j + 1);

                }

                else {
                    if(ascending = true)
                        if(downloads.get(j).getStartTime().compareTo(downloads.get(j + 1).getStartTime()) < 0)
                            Collections.swap(downloads, j , j + 1);
                        else
                            if(downloads.get(j).getStartTime().compareTo(downloads.get(j + 1).getStartTime()) > 0)
                            Collections.swap(downloads, j , j + 1);
                }
            }
    }

    public void setDownloads(ArrayList<Download> downs)
    {
         downloads = downs;
    }



}
