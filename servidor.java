
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author inferken
 */
import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class servidor {
    
    static final int PUERTO = 6666;
    static final String KEYSTORE_FILE = "./miAlmacen.p12";
    static final String KEYSTORE_PASS = "miPassword123";
    
    public static void main(String[] args) {
        String texto_plano;
        DataInputStream in;
        DataOutputStream out;
       

        try {
            // Cargamos el certificado (tu identidad)
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASS.toCharArray());

            // Configuramos el gestor de llaves
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, KEYSTORE_PASS.toCharArray());

            // Creamos el contexto TLS
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), null, null);

            // Obtenemos la fábrica de Sockets Seguros
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();

            try (SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(PUERTO)) {
                System.out.println("[Servidor TLS] Escuchando en puerto " + PUERTO);
                
                while (true) {
                    // El accept() ahora devuelve un SSLSocket automáticamente
                    try (SSLSocket cs = (SSLSocket) ss.accept()) {
                        in = new DataInputStream(cs.getInputStream());
                        out = new DataOutputStream(cs.getOutputStream());
                        
                        System.out.println("[Servidor TLS] Cliente conectado de forma segura");
                        
                        // Ejemplo: leer lo que dice el cliente
                        texto_plano = in.readUTF();
                        System.out.println("Cliente dice: " + texto_plano);
                        
                        String texto_hacker = "";
                        
                        for (int i = 0; i < texto_plano.length(); i++) {
                            switch (texto_plano.charAt(i)) {
                                case 'a':
                                case 'A':
                                    texto_hacker += "4";
                                    break;
                                case 'e':
                                case 'E':
                                    texto_hacker += "3";
                                    break;
                                case 'i':
                                case 'I':
                                    texto_hacker += "1";
                                    break;
                                case 'o':
                                case 'O':
                                    texto_hacker += "0";
                                    break;
                                case 'u':
                                case 'U':
                                    texto_hacker += "6";
                                    break;
                                default: 
                                    texto_hacker += texto_plano.charAt(i);
                            }
                        }
                        
                        out.writeUTF(texto_hacker);
                        
                        
                        cs.close();
                        System.out.println("[Servidor TLS] Cliente desconectado de forma segura");

                    
                    } catch (IOException e) {
                        System.err.println("[Servidor] Error con un cliente: " + e.getMessage());
                    }
                }
            }
            
        } catch (Exception ex) {
            System.err.println("[Servidor] Error de configuración SSL: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}