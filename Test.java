import java.util.List;
import java.util.Arrays;

public class Test {
    
    public static void test() throws Exception {
        
        List<List<Integer>> entradaFinal = Arrays.asList(
            Arrays.asList(14, 11, 17, 19, 2, 5, 7, 6, 10, 4, 4, 14, 0, 2, 9, 17, 4, 11, 15, 5),
            Arrays.asList(12, 14, 10, 14, 17, 3, 9, 11, 15, 12, 3, 19, 14, 17, 10, 1, 13, 6, 1, 4),
            Arrays.asList(17, 6, 17, 17, 15, 12, 9, 12, 9, 6, 13, 11, 19, 6, 1, 19, 18, 5, 10, 14),
            Arrays.asList(2, 8, 18, 13, 7, 6, 5, 16, 14, 10, 7, 1, 15, 9, 15, 7, 2, 4, 14, 6),
            Arrays.asList(16, 7, 3, 19, 7, 2, 0, 10, 1, 18, 9, 14, 2, 0, 9, 16, 16, 12, 18, 16),
            Arrays.asList(18, 0, 5, 12, 15, 0, 3, 6, 8, 5, 1, 0, 12, 4, 8, 6, 6, 7, 5, 10),
            Arrays.asList(19, 17, 3, 11, 18, 8, 7, 3, 19, 13, 2, 19, 1, 0, 19, 8, 19, 4, 19, 9),
            Arrays.asList(19, 9, 1, 18, 17, 3, 11, 6, 6, 14, 19, 11, 12, 17, 16, 6, 14, 9, 4, 9),
            Arrays.asList(0, 10, 19, 10, 19, 18, 6, 16, 13, 12, 3, 14, 1, 4, 8, 19, 18, 5, 5, 5),
            Arrays.asList(17, 0, 17, 17, 12, 13, 18, 4, 16, 11, 8, 13, 1, 13, 0, 12, 0, 8, 15, 16),
            Arrays.asList(5, 0, 0, 2, 8, 13, 16, 11, 5, 5, 5, 12, 12, 14, 1, 6, 14, 9, 12, 2),
            Arrays.asList(5, 1, 14, 17, 10, 6, 7, 11, 15, 15, 5, 19, 7, 14, 15, 9, 13, 3, 8, 15),
            Arrays.asList(6, 7, 13, 16, 10, 11, 15, 5, 0, 7, 4, 9, 1, 19, 19, 12, 9, 4, 9, 7),
            Arrays.asList(7, 11, 16, 6, 3, 8, 4, 1, 6, 18, 12, 17, 13, 2, 6, 15, 9, 9, 11, 11),
            Arrays.asList(12, 14, 13, 18, 14, 15, 9, 10, 3, 7, 7, 17, 17, 2, 14, 13, 3, 0, 10, 1),
            Arrays.asList(9, 19, 5, 4, 6, 14, 14, 0, 1, 17, 14, 2, 0, 10, 13, 11, 18, 2, 4, 6),
            Arrays.asList(18, 14, 2, 12, 0, 18, 10, 12, 8, 13, 2, 14, 18, 11, 19, 5, 13, 6, 2, 13),
            Arrays.asList(11, 16, 18, 0, 15, 13, 15, 17, 5, 14, 16, 1, 10, 9, 13, 9, 9, 7, 12, 7),
            Arrays.asList(8, 7, 2, 1, 1, 14, 1, 19, 13, 14, 8, 2, 18, 10, 2, 13, 8, 16, 19, 11),
            Arrays.asList(6, 11, 14, 10, 14, 18, 11, 19, 8, 3, 0, 19, 10, 6, 15, 10, 1, 3, 12, 9)
            ); 
            
        IO.TRAZA = true;
            
        for (int i = 16; i <= entradaFinal.size(); i++) {
            
            int ii = i;
            
            System.out.println("Test: " + i);
            
            List<List<Integer>> entrada = entradaFinal
                .subList(0, i)
                .stream()
                .map(f -> f.subList(0, ii))
                .toList();
                
                
            DatosPrecalculados datosPrecalculados = new DatosPrecalculados(entrada);
                
            long inicioRyP = System.currentTimeMillis();
            
            List<Integer> solucionRyP = SolucionadorRamificacionYPoda.getSolucion(entrada);
                
            long finRyP = System.currentTimeMillis();
            
            long tiempoEjecucionRyP = finRyP - inicioRyP;
            
            Integer costeRyP = getCoste(datosPrecalculados, solucionRyP);
            
            System.out.println("RyP [" + costeRyP + "] (" + (tiempoEjecucionRyP/1000) + "s): " + solucionRyP);
            /*
            long inicioVA = System.currentTimeMillis();
            
            List<Integer> solucionVA = SolucionadorVueltaAtras.getSolucion(entrada);
                
            long finVA = System.currentTimeMillis();
            
            long tiempoEjecucionVA = finVA - inicioVA;
            
            Integer costeVA = getCoste(datosPrecalculados, solucionVA);
            
            System.out.println("VA [" + costeVA + "] (" + (tiempoEjecucionVA/1000) + "s): " + solucionVA);
            
            long inicioFB = System.currentTimeMillis();
            
            List<Integer> solucionFB = SolucionadorFuerzaBruta.getSolucion(entrada);
                
            long finFB = System.currentTimeMillis();
            
            long tiempoEjecucionFB = finFB - inicioFB;
            
            Integer costeFB = getCoste(datosPrecalculados, solucionFB);
            
            System.out.println("FB [" + costeFB + "] (" + (tiempoEjecucionFB/1000) + "s): " + solucionFB);
            */
        }
        
    }
    
    
    private static Integer getCoste(DatosPrecalculados datosPrecalculados, List<Integer> mapaAgenteTarea) {
        
        List<List<Integer>> mapaAgenteTareaCoste = datosPrecalculados.getMapaAgenteTareaCoste();
        
        Integer coste = 0;
        
        for (int agente = 0; agente < mapaAgenteTarea.size(); agente++) {
            
            Integer tarea = mapaAgenteTarea.get(agente);
            
            coste += mapaAgenteTareaCoste.get(agente).get(tarea);
            
        }
        
        return coste;
        
    }
    
}
