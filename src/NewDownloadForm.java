import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class is used to add a new download
 * to a Queue
 *
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class NewDownloadForm {
    long size;
    private JFrame mainFrame;
    private JTextField link;
    private JTextField name;
    private JButton selectAddress;
    private JFileChooser fileChooser;
    private JPanel upPanel;
    private String saveAdress;
    private LocalDateTime startTime;
    private JPanel midPanel;
    private ButtonGroup buttonGroup;
    private JRadioButton rightNow;
    private JRadioButton afterX;
    private JPanel afterXPanel;
    private JSpinner afterXTimer;
    private JRadioButton atY;
    private JPanel atYPanel;
    private JSpinner atYTime;
    private JPanel downPanel;
    private JCheckBox queueName;
    private JButton cancelButton;
    private JButton okButton;
    private Queue queue;

    public NewDownloadForm(String saveAddress, Queue queue) {
        this.saveAdress = saveAddress;
        this.queue = queue;
        mainFrame = new JFrame("Start a new download");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(700, 400);
        mainFrame.setLocation(600, 300);
        mainFrame.setLayout(new BorderLayout());
        upPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        upPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel label1 = new JLabel("Link:");
        label1.setFocusable(false);
        link = new JTextField("");
        link.setText(null);
        link.addKeyListener(new MyKeyboardListener());
        //link.setFocusable(false);
        JLabel label2 = new JLabel("Name:");
        name = new JTextField("");
        name.setText(null);
        name.addKeyListener(new MyKeyboardListener());
        //name.setFocusable(false);
        label2.setFocusable(false);
        JLabel label3 = new JLabel("Default save location");
        selectAddress = new JButton("Select Location");
        selectAddress.setFocusable(false);
        selectAddress.addMouseListener(new MyMouseListener());
        selectAddress.addKeyListener(new MyKeyboardListener());
        label3.setFocusable(false);
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(saveAddress));
        fileChooser.setDialogTitle("Choose place to save this file");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        upPanel.add(label1);
        upPanel.add(link);
        upPanel.add(label2);
        upPanel.add(name);
        upPanel.add(label3);
        upPanel.add(selectAddress);
        mainFrame.add(upPanel, BorderLayout.NORTH);
        JLabel label4 = new JLabel("Starting at...: ");
        rightNow = new JRadioButton("Right Now: ");
        afterX = new JRadioButton("Start after: ");
        afterX.addKeyListener(new MyKeyboardListener());
        atY = new JRadioButton("Start at: ");
        atY.addKeyListener(new MyKeyboardListener());
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rightNow);
        rightNow.addKeyListener(new MyKeyboardListener());
        buttonGroup.add(afterX);
        afterXTimer = new JSpinner(new SpinnerNumberModel());
        afterXTimer.setFocusable(false);
        afterXTimer.setValue((Integer) 5);
        afterXPanel = new JPanel();
        afterXPanel.add(afterX);
        afterXPanel.add(afterXTimer);
        afterXPanel.add(new JLabel("mins."));
        buttonGroup.add(atY);
        atYPanel = new JPanel();
        atYTime = new JSpinner(new SpinnerDateModel());
        atYTime.setFocusable(false);
        atYTime.setValue(new Date());
        atYPanel.add(atY);
        atYPanel.add(atYTime);
        midPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JPanel rightNowPanel = new JPanel();
        rightNowPanel.add(rightNow);
        JPanel schPanel = new JPanel();
        schPanel.add(label4);
        midPanel.add(schPanel);
        midPanel.add(rightNowPanel);
        midPanel.add(afterXPanel);
        midPanel.add(atYPanel);
        mainFrame.add(midPanel, BorderLayout.CENTER);
        downPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        downPanel.add(new JLabel("Do you want to add to queue? "));
        queueName = new JCheckBox("Add to queue");
        queueName.setHorizontalAlignment(SwingConstants.RIGHT);
        queueName.setFocusable(false);
        downPanel.add(queueName);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        downPanel.add(cancelButton);
        cancelButton.addMouseListener(new MyMouseListener());
        downPanel.add(okButton);
        okButton.addMouseListener(new MyMouseListener());
        downPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainFrame.add(downPanel, BorderLayout.SOUTH);

    }

    /**
     * Shows the new Download form
     */
    public void showForm() {
        mainFrame.setVisible(true);
    }

    /**
     * Makes this form invisible
     */
    private void hideForm() {
        mainFrame.setVisible(false);
    }

    private boolean isBlocked(String url) {
        for (String string : FileUtils.readBlockedLinks())
            if (url.startsWith(string) || url.startsWith("http://" + string) || url.startsWith("https://" + string))
                if (!string.equals("") && !string.equals("\n") && !string.equals(" ") && !string.equals(null) && string.length() != 0)
                    return true;

        return false;

    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getSource().equals(selectAddress)) {
                fileChooser.showDialog(null, "Confirm");
                if (fileChooser.getCurrentDirectory().toString() != null && !fileChooser.getCurrentDirectory().toString().equals(""))
                    try {
                        if (fileChooser.getSelectedFile() != null)
                            saveAdress = fileChooser.getSelectedFile().toString();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
            }
            if (mouseEvent.getSource().equals(cancelButton))
                mainFrame.dispose();
            else if (mouseEvent.getSource().equals(okButton)) {
                if (isBlocked(link.getText())) {
                    JOptionPane.showMessageDialog(null, "This URL is blocked from setting,\n" +
                            "You can't download from it!");
                    return;
                }
                if (!link.getText().equals("") && !name.getText().equals("")) {
                    Download d;
                    if (queueName.isSelected()) {
                        if (afterX.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now().plusMinutes(Long.parseLong(afterXTimer.getValue().toString())), false, false);
                        else if (atY.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.ofInstant(((Date) atYTime.getValue()).toInstant(), ZoneId.systemDefault()), false, false);
                        else
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now(), false, false);
                        QueueFrame.getQueue().addDownload(d);
                        d.setIndexInDownloads(QueueFrame.getQueue().getIndex(d));
                    } else {
                        if (afterX.isSelected()) {
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now().plusMinutes(Long.parseLong(afterXTimer.getValue().toString())), false, false);
                        } else if (atY.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.ofInstant(((Date) atYTime.getValue()).toInstant(), ZoneId.systemDefault()), false, false);
                        else
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now(), false, false);
                        queue.addDownload(d);
                        d.setIndexInDownloads(queue.getIndex(d));
                    }
                    mainFrame.dispose();
                    MainForm.updateDownloadList();
                    QueueFrame.updateDownloadList();
                }
            }
        }
    }

    private class MyKeyboardListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER) {
                if (isBlocked(link.getText())) {
                    JOptionPane.showMessageDialog(null, "The URL is blocked from setting");
                    return;
                }
                if (!link.getText().equals("") && !name.getText().equals("")) {
                    Download d;
                    if (queueName.isSelected()) {
                        if (afterX.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now().plusMinutes(Long.parseLong(afterXTimer.getValue().toString())), false, false);
                        else if (atY.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.ofInstant(((Date) atYTime.getValue()).toInstant(), ZoneId.systemDefault()), false, false);
                        else
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now(), false, false);

                        QueueFrame.getQueue().addDownload(d);
                        d.setIndexInDownloads(QueueFrame.getQueue().getIndex(d));
                    } else {
                        if (afterX.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now().plusMinutes(Long.parseLong(afterXTimer.getValue().toString())), false, false);
                        else if (atY.isSelected())
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.ofInstant(((Date) atYTime.getValue()).toInstant(), ZoneId.systemDefault()), false, false);
                        else
                            d = new Download(name.getText(), saveAdress, size, 0, 0, 0, link.getText(), QueueFrame.getQueue(), LocalDateTime.now(), false, false);
                        queue.addDownload(d);
                        System.out.print("");
                        d.setIndexInDownloads(queue.getIndex(d));
                    }
                    System.out.print("");
                    mainFrame.dispose();
                    MainForm.updateDownloadList();
                    QueueFrame.updateDownloadList();
                }
            }
        }
    }
}
