/*
 * Main.java
 *
 * Created on April 30, 2007, 3:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package FalkonMonitor;

/**
 *
 * @author iraicu
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {

    }
    
    public void initialize()
    {
                        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonitorGUI().setVisible(true);
            }
        });

        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting up the Falkon Monitor v0.9...");
        Main falkonMonitor = new Main();
        falkonMonitor.initialize();
        
        System.out.println("Exiting the Falkon Monitor v0.9...");
        
        
        
        // TODO code application logic here
    }
    
}
