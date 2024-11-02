import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static boolean TRAZA = false;
    
    public static void mostrarAyuda() {
        
        System.out.println("SINTAXIS: tareas [-t] [-h] [fichero entrada [fichero salida]]");
        System.out.println("    [-t]                    Traza el algoritmo");
        System.out.println("    [-h]                    Muestra esta ayuda");
        System.out.println("    [fichero entrada]       Nombre del fichero de entrada");
        System.out.println("    [fichero salida]        Nombre del fichero de salida");
        
    }
    
    public static List<List<Integer>> leerArchivoCostes(String nombre) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            
            List<List<Integer>> lineas = br
                .lines()
                .map(l -> 
                    Arrays
                        .stream(l
                            .split(" "))
                        .filter(s -> 
                            s.length() > 0)
                        .map(s -> 
                            Integer.parseInt(s))
                        .toList())
                .toList();
            
            if (lineas.size() == 0) {
                
                throw new Exception("El archivo esta vacio");
                
            }
            
            if (lineas.get(0).size() != 2) {
                
                throw new Exception("La primera linea del archivo no es valida");
                
            }
            
            Integer filas = lineas.get(0).get(0);

            Integer columnas = lineas.get(0).get(1);
            
            List<List<Integer>> tablaCostes = lineas.subList(1, lineas.size());
            
            if (tablaCostes.size() != filas) {
                
                throw new Exception("La tabla no tiene las filas especificadas");
                
            }
            
            if (tablaCostes.stream().anyMatch(fila -> fila.size() != columnas)) {
                
                throw new Exception("La tabla no tiene las columnas especificadas");
                
            }
            
            if (tablaCostes.stream().anyMatch(fila -> fila.stream().anyMatch(valor -> valor < 0))) {
                
                throw new Exception("La tabla tiene valores negativos, solo se aceptan positivos");
                
            }
            
            return tablaCostes;
            
        } catch(Exception ex) {
            
            System.out.println("Error leyendo archivo " + nombre + " : " + ex);
        
        }
        
        return null;
        
    }
    
    public static List<List<Integer>> leerCostesPorConsola() {
        
        System.out.println();
        
        Integer agentes = leerNumero("Numero de agentes: ");
        
        Integer tareas = leerNumero("Numero de tareas: ");
        
        List<List<Integer>> mapaAgenteTareaCoste = new ArrayList<>();
        
        for (int agente = 0; agente < agentes; agente++) {
            
            mapaAgenteTareaCoste.add(new ArrayList<>());
            
            for (int tarea = 0; tarea < tareas; tarea++) {
                
                mapaAgenteTareaCoste.get(agente).add(leerNumero("Coste del agente " + (agente + 1) + " para la tarea " + (tarea + 1) + ": "));
                
            }
            
        }
        
        return mapaAgenteTareaCoste;
        
    }
    
    private static Integer leerNumero(String mensaje) {
        
        while (true) {
        
            System.out.println(mensaje);
            
            String entrada = scanner.nextLine().strip();
            
            try {
                
                return Integer.parseInt(entrada);
                
            } catch (Exception ex) {
            
                System.out.println("Valor invalido, debe ser un numero positivo");
                
            }
        
        }
        
    }
    
    public static void escribirResultado(String nombre, List<Integer> mapaAgenteTarea) {
        
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nombre), "utf-8"))) {
            
            for (int agente = 0; agente < mapaAgenteTarea.size(); agente++) {
            
                Integer tarea = mapaAgenteTarea.get(agente);
                
                wr.append((agente + 1) + " " + (tarea + 1) + "\n");
                
            }
            
        } catch(Exception ex) {
            
            System.out.println("Error escribiendo a archivo " + nombre + " : " + ex);
        
        }
        
    }
    
    public static void mostrarResultadoPorPantalla(List<Integer> mapaAgenteTarea) {
    
        System.out.println();
        
        System.out.println("Solucion:");
        
        for (int agente = 0; agente < mapaAgenteTarea.size(); agente++) {
        
            Integer tarea = mapaAgenteTarea.get(agente);
            
            System.out.println((agente + 1) + " " + (tarea + 1));
            
        }
        
    }
    
    public static void traza(String mensaje) {
        
        if (TRAZA) {
            
            System.out.println(mensaje);
            
        }
        
    }
    
}
