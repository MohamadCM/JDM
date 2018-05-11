import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyJFrame extends JFrame{
        TrayIcon trayIcon;
        SystemTray tray;
        MyJFrame(String title){
            super(title);
            if(SystemTray.isSupported()){
                System.out.println("system tray supported");
                tray=SystemTray.getSystemTray();
                Image image=Toolkit.getDefaultToolkit().getImage("Images/ICON.png");
                ActionListener exitListener=new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };
                PopupMenu popup=new PopupMenu();
                MenuItem defaultItem=new MenuItem("Exit");
                defaultItem.addActionListener(exitListener);
                popup.add(defaultItem);
                defaultItem=new MenuItem("Open");
                defaultItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setVisible(true);
                        setExtendedState(JFrame.NORMAL);
                    }
                });
                popup.add(defaultItem);
                trayIcon=new TrayIcon(image, "SystemTray Demo", popup);
                trayIcon.setImageAutoSize(true);
            }else{
                System.out.println("system tray not supported");
            }
            addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                public void windowClosing(WindowEvent e){
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                    } catch (AWTException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                    } catch (AWTException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                @Override
                public void windowIconified(WindowEvent e) { }

                @Override
                public void windowDeiconified(WindowEvent e) { }

                @Override
                public void windowActivated(WindowEvent e) { }

                @Override
                public void windowDeactivated(WindowEvent e) { }
            });
            setIconImage(Toolkit.getDefaultToolkit().getImage("Images/ICON.png"));

            setVisible(true);
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}
