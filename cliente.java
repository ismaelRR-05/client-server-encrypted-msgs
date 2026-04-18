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
import java.util.Scanner;

public class cliente {

    static final String HOST = "localhost";
    static final int PUERTO = 6666;
    static final String TRUSTSTORE_FILE = "miAlmacen.p12"; // el mismo certificado del servidor
    static final String TRUSTSTORE_PASS = "miPassword123";
    
    public static void main(String[] args) {
        String separador = "\s3par4dor/";
        DataInputStream in;
        DataOutputStream out;

        try {
            // Cargamos el certificado del servidor para confiar en él
            KeyStore ts = KeyStore.getInstance("PKCS12");
            ts.load(new FileInputStream(TRUSTSTORE_FILE), TRUSTSTORE_PASS.toCharArray());

            // Configuramos el gestor de confianza
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            // Creamos el contexto TLS
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);

            // Obtenemos la fábrica de Sockets Seguros
            SSLSocketFactory sf = sc.getSocketFactory();

            try (SSLSocket socket = (SSLSocket) sf.createSocket(HOST, PUERTO)) {
                System.out.println("[Cliente TLS] Conectado al servidor de forma segura");

                in  = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                // Leemos el mensaje a enviar por teclado
                Scanner scanner = new Scanner(System.in);
                System.out.print("Escribe un mensaje: ");
                String texto = scanner.nextLine();
                
                String mensaje = "1" + separador + texto;
                // Enviamos el mensaje al servidor
                out.writeUTF(mensaje);
                System.out.println("[Cliente] Mensaje enviado");

                // Recibimos la respuesta cifrada del servidor
                String respuesta = in.readUTF();
                if (respuesta.split(separador)[0].equals("2")) {
                    String texto_respuesta = respuesta.split(separador)[1]; // tomo el 2º elemento, ya que el primero es el codigo del mensaje
                    System.out.println("[Cliente] Respuesta del servidor: " + texto_respuesta);
                }else{
                    System.out.println("[Cliente] Respuesta del servidor erronea");
                }
            }

        } catch (Exception ex) {
            System.err.println("[Cliente] Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}