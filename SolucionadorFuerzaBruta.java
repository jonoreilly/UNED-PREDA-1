import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SolucionadorFuerzaBruta {
    
    public static List<Integer> getSolucion(List<List<Integer>> mapaAgenteTareaCoste) throws Exception {
    /*
        IO.traza("");
        
        IO.traza("Distributyendo las siguientes tareas:");
        
        for(List<Integer> fila : mapaAgenteTareaCoste) {
        
            IO.traza(fila.toString());
        
        }
        
        if (mapaAgenteTareaCoste.size() == 0 || mapaAgenteTareaCoste.stream().anyMatch(x -> x.size() == 0)) {
            
            throw new Exception("Entrada invalida");
            
        }
*/        
        DatosPrecalculados datosPrecalculados = new DatosPrecalculados(mapaAgenteTareaCoste);
        
        List<List<Integer>> solucionesParciales = new ArrayList<List<Integer>>();
        
        solucionesParciales.add(new ArrayList<Integer>());
        
        for (Integer agente : datosPrecalculados.getAgentes()) {
            
            List<List<Integer>> nuevasSolucionesParciales = new ArrayList<>();
            
            for (Integer tarea : datosPrecalculados.getTareas()) {
                
                for (List<Integer> solucionParcial : solucionesParciales) {
                    
                    if (solucionParcial.contains(tarea)) {
                        
                        continue;
                        
                    }
                
                    List<Integer> nuevaSolucionParcial = new ArrayList<>();
                    
                    nuevaSolucionParcial.addAll(solucionParcial);
                    
                    nuevaSolucionParcial.add(tarea);
                    
                    nuevasSolucionesParciales.add(nuevaSolucionParcial);
                
                }
            
            }
            
            solucionesParciales = nuevasSolucionesParciales;
            
        }
        
        List<Nodo> nodosSolucion = solucionesParciales.stream().map(mapaAgenteTarea -> {
                
                Integer valor = getCoste(datosPrecalculados, mapaAgenteTarea);
                
                return new Nodo(mapaAgenteTarea, valor, valor, true);
            
            }).toList();
            
        Nodo mejorSolucion = Collections.min(nodosSolucion, (a, b) -> a.getValor() - b.getValor());
            
        return mejorSolucion.getMapaAgenteTarea();
        
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
