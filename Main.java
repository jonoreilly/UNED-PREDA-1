import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        
        List<String> argumentos = Arrays.asList(args);
        
        List<String> opciones = Arrays.asList("-h", "-t");
        
        List<String> ficheros = argumentos.stream().filter(s -> !opciones.contains(s)).toList();
        
        if (argumentos.contains("-h")) {
            
            IO.mostrarAyuda();
            
            return;
            
        }
        
        IO.TRAZA = argumentos.contains("-t");
        
        List<List<Integer>> mapaAgenteTareaCoste;
        
        if (ficheros.size() > 0) {
            
            String ficheroEntrada = ficheros.get(0);
            
            mapaAgenteTareaCoste = IO.leerArchivoCostes(ficheroEntrada);
            
        } else {
            
            mapaAgenteTareaCoste = IO.leerCostesPorConsola();
            
        }
        
        Nodo solucion = DistribuidorDeTareas.distribuirTareas(mapaAgenteTareaCoste);
        
        if (ficheros.size() > 1) {
            
            IO.escribirResultado(ficheros.get(1), solucion.getMapaAgenteTarea());
            
        } else {
            
            IO.mostrarResultadoPorPantalla(solucion.getMapaAgenteTarea());
            
        }
        
    }
    
}
