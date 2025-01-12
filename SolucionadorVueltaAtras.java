import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SolucionadorVueltaAtras {
    
    public static List<Integer> getSolucion(List<List<Integer>> mapaAgenteTareaCoste) throws Exception {
             
        DatosPrecalculados datosPrecalculados = new DatosPrecalculados(mapaAgenteTareaCoste);
        
        Nodo nodoInicial = new Nodo(new ArrayList<Integer>(), 0, 0, false);
        
        Nodo solucion = obtenerNodoSolucion(datosPrecalculados, nodoInicial);
        
        return solucion.getMapaAgenteTarea();
        
    }
    
    public static Nodo obtenerNodoSolucion(DatosPrecalculados datosPrecalculados, Nodo nodo) {
                
        List<Integer> nodoMapaAgenteTarea = nodo.getMapaAgenteTarea();
        
        List<Integer> agentes = datosPrecalculados.getAgentes();
        
        List<Integer> tareas = datosPrecalculados.getAgentes();
        
        List<List<Integer>> mapaAgenteTareaCoste = datosPrecalculados.getMapaAgenteTareaCoste();
        
        if (nodo.getEsSolucion() || nodoMapaAgenteTarea.size() >= agentes.size()) {
            
            return nodo;
            
        }
        
        Integer proximoAgente = agentes.get(nodoMapaAgenteTarea.size());
        
        List<Integer> mapaTareaCoste = mapaAgenteTareaCoste.get(proximoAgente);
        
        List<Integer> tareasDisponibles = tareas.stream().filter(t -> !nodoMapaAgenteTarea.contains(t)).toList();    
        
        List<Nodo> nodosNuevos = tareasDisponibles.stream().map(tarea -> {
            
            List<Integer> nuevoNodoMapaAgenteTarea = new ArrayList<>(nodoMapaAgenteTarea);
            
            nuevoNodoMapaAgenteTarea.add(tarea);
            
            Integer nuevoNodoValor = getCoste(datosPrecalculados, nuevoNodoMapaAgenteTarea);
            
            boolean nuevoNodoEsSolucion = nuevoNodoMapaAgenteTarea.size() == agentes.size();
            
            return new Nodo(nuevoNodoMapaAgenteTarea, nuevoNodoValor, nuevoNodoValor, nuevoNodoEsSolucion);
            
        }).toList();
        
        List<Nodo> nodosSolucion = nodosNuevos.stream().map(n -> obtenerNodoSolucion(datosPrecalculados, n)).toList();
        
        Nodo mejorSolucion = Collections.min(nodosSolucion, (a, b) -> a.getValor() - b.getValor());
        
        return mejorSolucion;
        
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
