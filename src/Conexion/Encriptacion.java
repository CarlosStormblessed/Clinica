package Conexion;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encriptacion {
    private String clave = "VidaAntesQueMuerte";
    
    /**
     * Método que construye una clave de encriptación mediante una cadena.
     * @param llave Cadena con la cual se generará una clave de encriptación.
     * @return secretKeySpec
     */
    public SecretKeySpec crearClave(String llave){
        try{
            byte[] cadena = llave.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            cadena = md.digest(cadena);
            cadena = Arrays.copyOf(cadena, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(cadena, "AES");
            return secretKeySpec;
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Método para encriptar una cadena usando una clave de encriptación.
     * @param encriptar Cadena a encriptar.
     * @return cadenaDesencriptada.
     */
    public String encriptar(String encriptar){
        try{
            SecretKeySpec sks = crearClave(this.clave);
            Cipher cripto = Cipher.getInstance("AES");
            cripto.init(Cipher.ENCRYPT_MODE, sks);
            byte[] cadena = encriptar.getBytes("UTF-8");
            byte[] encriptada = cripto.doFinal(cadena);
            String cadenaEncriptada = Base64.encode(encriptada);
            return cadenaEncriptada;
        }catch (Exception e){
            return "";
        }
    }
    
    /**
     * Método para desencriptar una cadena usando una clave de encriptación.
     * @param desencriptar Cadena a desencriptar.
     * @return cadenaDesencriptada.
     */
    public String desencriptar(String desencriptar){
        try{
            SecretKeySpec sks = crearClave(this.clave);
            Cipher cripto = Cipher.getInstance("AES");
            cripto.init(Cipher.DECRYPT_MODE, sks);
            byte[] cadena = Base64.decode(desencriptar);
            byte[] desencriptada = cripto.doFinal(cadena);
            String cadenaDesencriptada = new String(desencriptada);
            return cadenaDesencriptada;
        }catch (Exception e){
            return "";
        }
    }
}
