
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TratamentoServidor implements Runnable {

    private Cliente cliente;
    private DatagramSocket datagram;
   
    /**
     * Construtor com parâmetros.
     *     
     * @param cliente
     * @param datagram     
     */
    public TratamentoServidor(Cliente cliente, DatagramSocket datagram) {
        this.cliente = cliente;     
        this.datagram = datagram;
    }

    public void run() {
        try {
            while (true) {
                String mensagem = leituraMensagem();
                if (mensagem != null) {
                    cliente.getTxtMensagensEntrada().append("\n" + mensagem);
                }
            }
        } catch (UnknownHostException uhe) {
            cliente.getTxtMensagensEntrada().append("\nConexao Terminada!");
        } catch (IOException ioe) {
           // txtMensagens.append("\nProblemas de IO");
        }
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
            DatagramPacket pacote = new DatagramPacket(dado, dado.length, InetAddress.getByName(cliente.getEnderecoServidor()), cliente.getPortaServidor());
            //envia o pacote
            datagram.send(pacote);
        } catch (IOException io) {
          cliente.getTxtMensagensEntrada().append("\nProblemas de IO");
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
        if (datagram != null) {
            datagram.receive(pacote);
        }
        //Recupera o texto recebido
        String mensagem = new String(pacote.getData(), 0, pacote.getLength());

        return mensagem;
    }
}
