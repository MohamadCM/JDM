import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class is used to download a file
 * and fill the progress bar
 */

public class DownloadUtil extends SwingWorker<Void, Integer> {
    private Download download;
    private HttpURLConnection urlConnection;
    private URL url;
    private int contentLenth;
    private InputStream inputStream;
    private boolean paused = false;

    /**
     * Each download task need a Download object to complete
     *
     * @param download
     */
    public DownloadUtil(Download download) throws IOException {
        this.download = download;
        if (!download.getLink().startsWith("http://") && !download.getLink().startsWith("https://"))
            url = new URL("http://" + download.getLink());
        else
            url = new URL(download.getLink());

    }

    /**
     * Pause this download task
     */
    public void pause() {
        paused = true;
    }

    /**
     * Resume this downloads (if paused)
     */
    public synchronized void resume() {
        paused = false;
        this.notify();
    }

    @Override
    protected void process(List<Integer> list) {
        super.process(list);
        MainForm.repaintForm();
        QueueFrame.repaintForm();
    }

    @Override
    protected Void doInBackground() throws IOException {
        if(!netIsAvailable()){
            JOptionPane.showMessageDialog(null, "Internet not available", "Error" ,JOptionPane.ERROR_MESSAGE);
            return null;
        }
        urlConnection = (HttpURLConnection) url.openConnection();


        int responseCode = -1;

        try {
            responseCode = urlConnection.getResponseCode();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "          Wrong link", "Error" ,JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            contentLenth = urlConnection.getContentLength();
            inputStream = urlConnection.getInputStream();
        //    System.out.println("Connected!");
        }else{
            JOptionPane.showConfirmDialog(null, "Cannot connect to server!");
            return null;
        }
        if (inputStream == null)
            throw new IOException();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream(new File(download.getAddress() + "/" + download.getName()));
        int bytesRead = 0;
        int totalRead = 0;
        byte[] buffer = new byte[2048];
        int size = contentLenth;
        int percentDownloaded = 0;
        long startTime = System.nanoTime();

        download.setVolume(size);
        download.setDownloadedVolume(totalRead);
        percentDownloaded = totalRead * 100 / size;
        download.setPercentDownload(percentDownloaded);
        download.getProgressBar().setValue((int) Math.abs(download.getPercentDownload()));
        download.setDownloadRate((totalRead) / ((System.nanoTime() - startTime) / 1000000));
        download.setDownloadedVolume(totalRead / 1000);
        download.getDownloadInfoForm().updateForm(percentDownloaded, size / 1000, totalRead / 1000, Math.toIntExact((totalRead) / ((System.nanoTime() - startTime) / 1000000)));
        download.getDownloadInfo().update(size, totalRead, percentDownloaded, (totalRead) / ((System.nanoTime() - startTime) / 1000000), size == totalRead);

        while (download.getDownloadInfo().getStartTime().isEqual(LocalDateTime.now()) || download.getDownloadInfo().getStartTime().isAfter(LocalDateTime.now()))
            ;

        while (true) {
            if (paused) {
                try {
                    synchronized (this) {
                        wait(1000);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Background interrupted");
                }
            } else {
                bytesRead = bufferedInputStream.read(buffer);
                if (Thread.interrupted() || bytesRead <= 0)
                    break;
                outputStream.write(buffer, 0, bytesRead);
                if (bytesRead > 0)
                    totalRead += bytesRead;
                percentDownloaded = (totalRead * 100) / size;
                download.setDownloadedVolume(totalRead);
                download.setPercentDownload(percentDownloaded);
                download.getProgressBar().setValue((int) Math.abs(download.getPercentDownload()));
                download.setDownloadRate((totalRead) / ((System.nanoTime() - startTime) / 1000000));
                download.setDownloadedVolume(totalRead / 1000);
                download.getDownloadInfoForm().updateForm(percentDownloaded, size / 1000, totalRead / 1000, Math.toIntExact((totalRead) / ((System.nanoTime() - startTime) / 1000000)));
                download.getDownloadInfo().update(size, totalRead, percentDownloaded, (totalRead) / ((System.nanoTime() - startTime) / 1000000), size == totalRead);
                MainForm.repaintForm();
                QueueFrame.repaintForm();
                publish(getProgress());
            }
            download.setFinished(true);
            if (!download.isCancelled())
                download.getDownloadInfo().update(size, totalRead, 100, (totalRead) / ((System.nanoTime() - startTime) / 1000000), true);
            MainForm.repaintForm();
            QueueFrame.repaintForm();
            publish(getProgress());
        }
        return null;
    }

    @Override
    protected void done() {
        super.done();
    }


    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("https://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
