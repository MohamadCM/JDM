import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

/**
 * This class is used to add a new download
 * to a Queue
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class NewDownloadForm {
    private JFrame mainFrame;
    private JTextField link;
    private JTextField name;
    private JButton selectAddress;
    private JFileChooser fileChooser;
    private JPanel upPanel;
    private String saveAdress;
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
    private JTextField queuName;
    private JButton cancelButton;
    private JButton okButton;
    private Queue queue;
    long size;
    public NewDownloadForm(String saveAddress, Queue queue)
    {
        this.queue = queue;
        mainFrame = new JFrame("Start a new download");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(700,400);
        mainFrame.setLocation(600,300);
        mainFrame.setLayout(new BorderLayout());
        upPanel = new JPanel(new GridLayout(3,2,5,5));
        upPanel.setBorder(new EmptyBorder(5,5,5,5));
        JLabel label1 = new JLabel("Link:");
        label1.setFocusable(false);
        link = new JTextField();
        //link.setFocusable(false);
        JLabel label2 = new JLabel("Name:");
        name = new JTextField();
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
        afterXTimer.setValue((Integer)5);
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
        midPanel = new JPanel(new GridLayout(2,2,5,5));
        JPanel rightNowPanel = new JPanel();
        rightNowPanel.add(rightNow);
        JPanel schPanel = new JPanel();
        schPanel.add(label4);
        midPanel.add(schPanel);
        midPanel.add(rightNowPanel);
        midPanel.add(afterXPanel);
        midPanel.add(atYPanel);
        mainFrame.add(midPanel, BorderLayout.CENTER);
        downPanel = new JPanel(new GridLayout(2,2,5,5));
        downPanel.add(new JLabel("Add to queue: "));
        queuName = new JTextField("Default Queue");
        queuName.setFocusable(false);
        downPanel.add(queuName);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        downPanel.add(cancelButton);
        cancelButton.addMouseListener(new MyMouseListener());
        downPanel.add(okButton);
        okButton.addMouseListener(new MyMouseListener());
        downPanel.setBorder(new EmptyBorder(5,5,5,5));
        mainFrame.add(downPanel, BorderLayout.SOUTH);
    }

    /**
     * Shows the new Ddnwload form
     */
    public void showForm(){
        mainFrame.setVisible(true);
    }

    /**
     * Makes this form invisible
     */
    private void hideForm()
    {
        mainFrame.setVisible(false);
    }
    private class MyMouseListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getSource().equals(selectAddress)){
                fileChooser.showDialog(null, "Confirm");
                saveAdress = fileChooser.getCurrentDirectory().toString();
            }
            if(mouseEvent.getSource().equals(cancelButton))
                hideForm();
            else if(mouseEvent.getSource().equals(okButton)){
                queue.getDownloads().add(new Download(name.getText(),saveAdress, size,link.getText()));
                MainForm.updateDownloadList();
                hideForm();
            }
        }
    }
    private class MyKeyboardListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if(keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
                queue.getDownloads().add(new Download(name.getText(),saveAdress, size,link.getText()));
                MainForm.updateDownloadList();
                hideForm();
            }
        }
    }
}
