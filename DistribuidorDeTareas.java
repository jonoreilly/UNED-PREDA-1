import java.util.List;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class DistribuidorDeTareas {
        
    public static Nodo distribuirTareas(List<List<Integer>> mapaAgenteTareaCoste) throws Exception {
        
        IO.traza("");
        
        IO.traza("Distributyendo las siguientes tareas:");
        
        for(List<Integer> fila : mapaAgenteTareaCoste) {
        
            IO.traza(fila.toString());
        
        }
        
        if (mapaAgenteTareaCoste.size() == 0 || mapaAgenteTareaCoste.stream().anyMatch(x -> x.size() == 0)) {
            
            throw new Exception("Entrada invalida");
            
        }
        
        DatosPrecalculados datosPrecalculados = new DatosPrecalculados(mapaAgenteTareaCoste);
        
        Nodo nodoInicial = new Nodo(new ArrayList<Integer>(), 0, 0, false);
        
        Function<Nodo, Boolean> esSolucion = (nodo) -> nodo.getEsSolucion();
        
        Function<Nodo, Integer> getCota = (nodo) -> nodo.getCota();
        
        Function<Nodo, Integer> getValor = (nodo) -> nodo.getValor();
        
        Function<Nodo, List<Nodo>> explorarNodo = (nodo) -> getExplorarNodos(datosPrecalculados, nodo);
        
        Nodo solucion = Solucionador.ramificacionYPoda(
            nodoInicial,
            esSolucion,
            getCota,
            getValor,
            explorarNodo
            );
            
        return solucion;
        
    }
    
    
    private static List<Nodo> getExplorarNodos(DatosPrecalculados datosPrecalculados, Nodo nodo) {
        
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
