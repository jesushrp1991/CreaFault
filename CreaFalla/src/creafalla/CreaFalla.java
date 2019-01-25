/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creafalla;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Jesus
 */
public class CreaFalla {

    public static int EntraIncial = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ArrayList<Integer> profundidades = new ArrayList<>();
        ArrayList<Double> magnitudes = new ArrayList<>();
        File carpetacopiar = new File("D:\\Plantilla\\Testcentro");
        File carpetapegar = new File("D:\\");
        File archivoleer = null;
        FileReader fr = null;
        BufferedReader br = null;
        archivoleer = new File("D:\\archivo.txt");
        fr = new FileReader(archivoleer);
        br = new BufferedReader(fr);
        String linea;
        int contadorlinea = 1;
        int iterador = 1;
        try{
        while ((linea = br.readLine()) != null) {
             StringTokenizer tokenizer = new StringTokenizer(linea);
            if (contadorlinea == 1) {
                while (tokenizer.hasMoreElements()) {
                    magnitudes.add(Double.parseDouble(tokenizer.nextToken()));
                }
                System.out.println(magnitudes);
            } else if (contadorlinea == 2) {
                while (tokenizer.hasMoreElements()) {
                    profundidades.add(Integer.parseInt(tokenizer.nextToken()));
                }
            } else {
                double dip = 45.00;
                 double lat = Double.parseDouble(tokenizer.nextToken());
                        double lon = Double.parseDouble(tokenizer.nextToken());
                        double strikedeg = Double.parseDouble(tokenizer.nextToken());
                for (int i = 0; i < magnitudes.size(); i++) {
                    for (int j = 0; j < profundidades.size(); j++) {
                        carpetacopiar.renameTo(new File("D:\\Plantilla\\0000" + iterador));
                        carpetacopiar = new File("D:\\Plantilla\\0000" + iterador);
                        Copiar(carpetacopiar, carpetapegar);
                        EntraIncial = 0;
                        double faultoriginlon, faultoriginlat, magnitude = magnitudes.get(i), depthkm = profundidades.get(j), depthm, faultlengthm, faultwidthm;
                        faultlengthm = Math.pow(10, -2.28 + 0.55 * magnitude) * 1000;
                        faultwidthm = Math.pow(10, -1.8 + 0.45 * magnitude) * 1000;
                        depthm = (depthkm - Math.pow(10, -1.8 + 0.45 * magnitude) / 2 * Math.sin(dip / 180)) * 1000;
                        double g48 = Math.atan(Math.pow(10, -1.8 + 0.45 * magnitude) / Math.pow(10, -2.28 + 0.55 * magnitude)) * 180 / Math.PI;
                        double h48 = 0.0;
                        if (strikedeg > 0 && strikedeg <= 90) {
                            h48 = 90 - g48 - strikedeg;
                        } else if (strikedeg > 90 && strikedeg <= 180) {
                            h48 = 180 - g48 - strikedeg;
                        } else if (strikedeg > 180 && strikedeg <= 270) {
                            h48 = 270 - g48 - strikedeg;
                        } else if (strikedeg > 270 && strikedeg <= 360) {
                            h48 = 360 - g48 - strikedeg;
                        }
                        if (strikedeg > 0 && strikedeg <= 180) {
                            faultoriginlon = lon - Math.sqrt(Math.pow(Math.pow(10, -2.28 + 0.55 * magnitude) / 2, 2)
                                    + Math.pow(Math.pow(10, -1.8 + 0.45 * magnitude) / 2, 2)) * Math.sin(h48 * Math.PI / 180) / 110;
                        } else {
                            faultoriginlon = lon + Math.sqrt(Math.pow(Math.pow(10, -2.28 + 0.55 * magnitude) / 2, 2)
                                    + Math.pow(Math.pow(10, -1.8 + 0.45 * magnitude) / 2, 2)) * Math.sin(h48 * Math.PI / 180) / 110;
                        }
                        if (strikedeg > 90 && strikedeg <= 270) {
                            faultoriginlat = lat + Math.sqrt(Math.pow(Math.pow(10, -2.28 + 0.55 * magnitude) / 2, 2)
                                    + Math.pow(Math.pow(10, -1.8 + 0.45 * magnitude) / 2, 2)) * Math.cos(h48 * Math.PI / 180) / 110;
                        } else {
                            faultoriginlat = lat - Math.sqrt(Math.pow(Math.pow(10, -2.28 + 0.55 * magnitude) / 2, 2)
                                    + Math.pow(Math.pow(10, -1.8 + 0.45 * magnitude) / 2, 2)) * Math.cos(h48 * Math.PI / 180) / 110;
                        }
                        String ruta = "D:\\0000" + iterador + "\\Cntrl\\fault.txt";
                        File archivo = new File(ruta);
                        BufferedWriter bw;
                        bw = new BufferedWriter(new FileWriter(archivo));
                        bw.write("# Initial Water Elevation #\n");
                        bw.write("Index for Initial condition  : 1\n");
                        bw.write("(0:Nonuse)\n");
                        bw.write("(1;Fault parameter)\n");
                        bw.write("(2;Read data)\n");
                        bw.write("(3;Gaussian distribution)\n\n");
                        bw.write("# Fault parameter #\n");
                        bw.write("Number of faults             : 1\n");
                        bw.write("Horizontal effect(No:0,Yes:1): 0\n\n");
                        bw.write("--- Fault 01 ---\n");
                        bw.write("Cartesian => 0,Spherical => 1: 1\n");
                        bw.write("Fault origin (lon or x-grid) : " + String.format("%.2f",faultoriginlon) + "\n");
                        bw.write("Fault origin (lat or y-grid) : " + String.format("%.2f",faultoriginlat) + "\n");
                        bw.write("Rupture time (sec)           : 0.0\n");
                        bw.write("Depth (m)                    : " + String.format("%.2f",depthm) + "\n");
                        bw.write("Slip  (m)                    : 5.00\n");
                        bw.write("Dip                          : 45.00\n");
                        bw.write("Strike                       : " + String.format("%.2f",strikedeg) + "\n");
                        bw.write("Slip angle                   : 90.00\n");
                        bw.write("Fault length                 : " + String.format("%.2f",faultlengthm) + "\n");
                        bw.write("Fault width                  : " + String.format("%.2f",faultwidthm) + "\n");
                        bw.write("Cal. area (m)                : " + String.format("%.2f",faultlengthm * faultwidthm) + "\n");
                        bw.close();
                        iterador++;
                    }
                }
                fr.close();
            }
            contadorlinea++;
        }
        }catch(Exception e){
            carpetacopiar.renameTo(new File("D:\\Plantilla\\Testcentro"));
            System.err.println(e);
            return;
        }

    }

    public static void Copiar(File FOrigen, File FDestino) {
        //si el origen no es una carpeta
        if (!FOrigen.isDirectory()) {
            //Llamo la funcion que lo copia
            CopiarFichero(FOrigen, FDestino);
        } else {
            //incremento el contador de entradas a esta función 
            EntraIncial++;
            //solo se entra acá cuando se quiera copiar una carpeta y sea la primera es decir la carpeta padre
            if (EntraIncial == 1) {
                //Cambio la ruta destino por el nombre que tenia mas el nombre de la carpeta padre
                FDestino = new File(FDestino.getAbsolutePath() + "/" + FOrigen.getName());
                //si la carpeta no existe la creo
                if (!FDestino.exists()) {
                    FDestino.mkdir();
                }
            }
            //obtengo el nombre de todos los archivos y carpetas que pertenecen a este fichero(FOrigen)
            String[] Rutas = FOrigen.list();
            //recorro uno a uno el contenido de la carpeta
            for (String Ruta : Rutas) {
                //establezco el nombre del nuevo archivo origen
                File FnueOri = new File(FOrigen.getAbsolutePath() + "/" + Ruta);
                //establezco el nombre del nuevo archivo destino
                File FnueDest = new File(FDestino.getAbsolutePath() + "/" + Ruta);
                //si no existe el archivo destino lo creo
                if (FnueOri.isDirectory() && !FnueDest.exists()) {
                    FnueDest.mkdir();
                }
                //uso recursividad y llamo a esta misma funcion has llegar al ultimo elemento    
                Copiar(FnueOri, FnueDest);
            }
        }
    }

    public static void CopiarFichero(File FOrigen, File FDestino) {
        try {
            //Si el archivo a copiar existe
            if (FOrigen.exists()) {
                String copiar = "S";
                //si el fichero destino ya existe
                if (FDestino.exists()) {
                    System.out.println("El fichero ya existe, Desea Sobre Escribir:S/N ");
                    copiar = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                }
                //si puedo copiar
                if (copiar.toUpperCase().equals("S")) {
                    //Flujo de lectura al fichero destino(donde se va a copiar)
                    try ( //Flujo de lectura al fichero origen(que se va a copiar)
                            FileInputStream LeeOrigen = new FileInputStream(FOrigen); //Flujo de lectura al fichero destino(donde se va a copiar)
                            OutputStream Salida = new FileOutputStream(FDestino)) {
                        //separo un buffer de 1MB de lectura
                        byte[] buffer = new byte[1024];
                        int tamaño;
                        //leo el fichero a copiar cada 1MB
                        while ((tamaño = LeeOrigen.read(buffer)) > 0) {
                            //Escribe el MB en el fichero destino
                            Salida.write(buffer, 0, tamaño);
                        }
                        System.out.println(FOrigen.getName() + " Copiado con Exito!!");
                    }
                }
            } else {//l fichero a copiar no existe                
                System.out.println("El fichero a copiar no existe..." + FOrigen.getAbsolutePath());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
