
/**
 *
 * @author osmar
 */
public class Principal {

    public static void main(String[] args) {
     /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCliente().setVisible(true);
            }
        });
    }
}
