
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JTextArea;

/**
 *
 * @author osmar
 */
public class Cliente {

    private String enderecoServidor;
    private int portaServidor;
    private String nome;

    private JTextArea txtMensagensEntrada;

    //Socket com o servidor
    private DatagramSocket datagramServidor;
    
    public String getEnderecoServidor() {
        return enderecoServidor;
    }

    public void setEnderecoServidor(String enderecoServidor) {
        this.enderecoServidor = enderecoServidor;
    }

    public int getPortaServidor() {
        return portaServidor;
    }

    public void setPortaServidor(int portaServidor) {
        this.portaServidor = portaServidor;
    }

    public JTextArea getTxtMensagensEntrada() {
        return txtMensagensEntrada;
    }

    public void setTxtMensagensEntrada(JTextArea txtMensagensEntrada) {
        this.txtMensagensEntrada = txtMensagensEntrada;
    }
        
    public Cliente(String enderecoServidor, int portaServidor, String nome, JTextArea txtMensagensEntrada) {
        this.enderecoServidor = enderecoServidor;
        this.portaServidor = portaServidor;
        this.nome = nome;
        this.txtMensagensEntrada = txtMensagensEntrada;
    }

    public void conecta() {
        txtMensagensEntrada.append("\nConectando com o servidor " + enderecoServidor + " na porta " + portaServidor);

        try {
            datagramServidor = new DatagramSocket();//cria o objeto
            
            enviaMensagem("conecta:" + nome);
 
             //Cria a Thread para receber as mensagens
//            Runnable cliente = new TratamentoServidor(this, datagramServidor);
//            Thread threadCliente = new Thread(cliente);
//            threadCliente.start();
             
        } catch (IOException ioe) {
            txtMensagensEntrada.append("\nProblemas de IO");
        }
    }

    public void desconecta() {

        //Envia o fim da conexao para o servidor
        enviaMensagem("#fim");

        txtMensagensEntrada.append("\nDesconectando do servidor.");
        //Fecha a conexão            
        datagramServidor.close();

        txtMensagensEntrada.append("\nServidor desconectado.");
    }
    
    /**
     * Envia mensagem pelo fluxo
     *
     * @param mensagem     
     */
    public void enviaMensagem(String mensagem) {
        try {        
            //Converte a mensagem em um vetor de byte
            byte dado[] = mensagem.getBytes();
            //Configura o pacote
            DatagramPacket pacote = new DatagramPacket(dado, dado.length, InetAddress.getByName(getEnderecoServidor()), getPortaServidor());
            //envia o pacote
            datagramServidor.send(pacote);
        } catch (IOException io) {
          getTxtMensagensEntrada().append("\nProblemas de IO");
        }
    }

    /**
     * Recupera mensagem do fluxo
     *
     * @return
     * @throws IOException
     */
    public String leituraMensagem() throws IOException {
        //Tamanho dos dados do pacote
        byte dado[] = new byte[100];
        //Pacote do datagrama
        DatagramPacket pacote = new DatagramPacket(dado, dado.length);
        //Recupera o pacote do datagrama
        if (datagramServidor != null) {
            datagramServidor.receive(pacote);
        }
        //Recupera o texto recebido        
        String mensagem = new String(pacote.getData());
        
        return mensagem;
    }
    
}
