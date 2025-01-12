import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalInt;

public class SolucionadorRamificacionYPoda {
    
    public static List<Integer> getSolucion(List<List<Integer>> mapaAgenteTareaCoste) throws Exception {
    
        IO.traza("");
        
        IO.traza("Distributyendo las siguientes tareas:");
        
        for(List<Integer> fila : mapaAgenteTareaCoste) {
        
            IO.traza(fila.toString());
        
        }
        
        if (mapaAgenteTareaCoste.size() == 0 || mapaAgenteTareaCoste.stream().anyMatch(x -> x.size() == 0)) {
            
            throw new Exception("Entrada invalida");
            
        }
        
        DatosPrecalculados datosPrecalculados = new DatosPrecalculados(mapaAgenteTareaCoste);
        
        Nodo solucion = obtenerNodoSolucion(datosPrecalculados);
        
        return solucion.getMapaAgenteTarea();
        
    }
    
    private static Nodo obtenerNodoSolucion(DatosPrecalculados datosPrecalculados) {
        
        PriorityQueue<Nodo> pila = new PriorityQueue<Nodo>((Nodo a, Nodo b) -> a.getCota() - b.getCota());
        
        Nodo nodoInicial = new Nodo(new ArrayList<Integer>(), 0, 0, false);
        
        Integer cotaSolucionMejor = obtenerCotaSolucionDiagonal(datosPrecalculados);
        
        pila.add(nodoInicial);
        
        while (pila.size() > 0) {
            
            IO.traza("pila: (" + pila.size()+ ") " + pila);
            
            Nodo nodo = pila.poll();
                        
            if (nodo.getEsSolucion()) {
                
                IO.traza("Solucion encontrada: " + nodo);
                
                return nodo;
                
            }
            
            List<Nodo> nuevosNodos = explorarNodo(datosPrecalculados, nodo, cotaSolucionMejor);
            
            IO.traza("Explorando nodo " + nodo + " -> (" + nuevosNodos.size() + ") " + nuevosNodos);
            
            pila.addAll(nuevosNodos);
            
            List<Integer> nuevasCotasSolucion = nuevosNodos
                .stream()
                .filter(n -> n.getEsSolucion())
                .map(n -> n.getCota())
                .toList();
            
            if (nuevasCotasSolucion.size() > 0) {
                
                Integer nuevaCotaSolucionMejor = Collections.min(nuevasCotasSolucion);
                
                if (nuevaCotaSolucionMejor < cotaSolucionMejor) {
                
                    cotaSolucionMejor = nuevaCotaSolucionMejor;
                    
                    IO.traza("Podando pila por solucion: " + nuevaCotaSolucionMejor);
                    
                    pila.removeIf(n -> n.getCota() > nuevaCotaSolucionMejor);
                    
                }
                
            }
            
        }
        
        return null;
        
    }
    
    private static Integer obtenerCotaSolucionDiagonal(DatosPrecalculados datosPrecalculados) {
        
        List<List<Integer>> mapaAgenteTareaCoste = datosPrecalculados.getMapaAgenteTareaCoste();
        
        Integer coste = 0; 
        
        for (int i = 0; i < mapaAgenteTareaCoste.size(); i++) {
            
            coste += mapaAgenteTareaCoste.get(i).get(i);
            
        }
        
        return coste;
        
    }
    
    private static List<Nodo> explorarNodo(DatosPrecalculados datosPrecalculados, Nodo nodo, Integer cotaSolucionMejor) {
        
        List<Integer> nodoMapaAgenteTarea = nodo.getMapaAgenteTarea();
        
        List<Integer> agentes = datosPrecalculados.getAgentes();
        
        List<Integer> tareas = datosPrecalculados.getAgentes();
        
        List<List<Integer>> mapaAgenteTareaCoste = datosPrecalculados.getMapaAgenteTareaCoste();
        
        if (nodo.getEsSolucion() || nodoMapaAgenteTarea.size() >= agentes.size()) {
            
            return new ArrayList<>();
            
        }
        
        Integer proximoAgente = agentes.get(nodoMapaAgenteTarea.size());
        
        List<Integer> mapaTareaCoste = mapaAgenteTareaCoste.get(proximoAgente);
        
        List<Integer> tareasDisponibles = tareas.stream().filter(t -> !nodoMapaAgenteTarea.contains(t)).toList();    
        
        List<Nodo> nodosNuevos = new ArrayList<>();    
        
        for (Integer tarea : tareasDisponibles) {
            
            Integer coste = mapaTareaCoste.get(tarea);
            
            List<Integer> nuevoNodoMapaAgenteTarea = new ArrayList<>(nodoMapaAgenteTarea);
            
            nuevoNodoMapaAgenteTarea.add(tarea);
            
            Integer nuevoNodoCota = getCotaOptimista(datosPrecalculados, nuevoNodoMapaAgenteTarea);
            
            if (nuevoNodoCota > cotaSolucionMejor) {
                
                IO.traza("Podando nodo nuevo: cotaSolucionMejor = " + cotaSolucionMejor + ", nuevoNodoCota = " + nuevoNodoCota);
                
                continue;
                
            }
            
            Integer nuevoNodoValor = getCoste(datosPrecalculados, nuevoNodoMapaAgenteTarea);
            
            boolean nuevoNodoEsSolucion = nuevoNodoMapaAgenteTarea.size() == agentes.size();
            
            nodosNuevos.add(new Nodo(nuevoNodoMapaAgenteTarea, nuevoNodoCota, nuevoNodoValor, nuevoNodoEsSolucion));
            
        }
        
        return nodosNuevos;
        
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
    
    private static Integer getCotaOptimista(DatosPrecalculados datosPrecalculados, List<Integer> mapaAgenteTarea) {
        
        List<Integer> mapaAgenteMinimoCoste = datosPrecalculados.getMapaAgenteMinimoCoste();
        
        List<Integer> agentes = datosPrecalculados.getAgentes();
        
        List<Integer> agentesLibres = agentes.subList(mapaAgenteTarea.size(), agentes.size());
        
        Integer coste = getCoste(datosPrecalculados, mapaAgenteTarea);
        
        Integer minimoRestante = 0;
        
        for (Integer agente : agentesLibres) {
            
            minimoRestante += mapaAgenteMinimoCoste.get(agente);
            
        }
        
        return coste + minimoRestante;
        
    }
    
}
