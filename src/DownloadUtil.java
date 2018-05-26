import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is used to download a file
 * and fill the progress bar
 */

public class DownloadUtil extends Thread{
    private Download download;

    /**
     * Each download task need a Download object to complete
     * @param download
     */
    public DownloadUtil(Download download) {
        this.download = download;
    }

    @Override
    public void run() {
        super.run();
        try(BufferedInputStream in = new BufferedInputStream(new URL(download.getLink()).openStream());
            FileOutputStream fout = new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + download.getName())) {
            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
            for(int i = 0 ; i < 100 ; i++)
            {
                download.getProgressBar().setValue(i);
                Thread.sleep(500);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
