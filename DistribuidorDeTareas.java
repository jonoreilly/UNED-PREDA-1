import java.util.List;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class DistribuidorDeTareas {
    
    private static class Nodo {
        
        private List<Integer> mapaAgenteTarea = new ArrayList<>();
        
        private Integer cota;

        private Integer valor;
        
        private Boolean esSolucion;
        
        public Nodo(List<Integer> mapaAgenteTarea, Integer cota, Integer valor, Boolean esSolucion) {
            
            this.mapaAgenteTarea.addAll(mapaAgenteTarea);
            
            this.cota = cota;
            
            this.valor = valor;
            
            this.esSolucion = esSolucion;
            
        }
        
        public List<Integer> getMapaAgenteTarea() {
            
            return new ArrayList<>(mapaAgenteTarea);
            
        }
        
        public Integer getCota() {
            
            return this.cota;
            
        }
        
        public Integer getValor() {
            
            return this.valor;
            
        }
        
        public Boolean getEsSolucion() {
            
            return this.esSolucion;
            
        }
        
    }
    
    public static void distribuirTareas(List<List<Integer>> mapaAgenteTareaCoste) throws Exception {
        
        if (mapaAgenteTareaCoste.size() == 0 || mapaAgenteTareaCoste.stream().anyMatch(x -> x.size() == 0)) {
            
            throw new Exception("Entrada invalida");
            
        }
        
        List<Integer> agentes = IntStream.range(0, mapaAgenteTareaCoste.size()).boxed().toList();
        
        List<Integer> tareas = IntStream.range(0, mapaAgenteTareaCoste.get(0).size()).boxed().toList();
        
        Nodo nodoInicial = new Nodo(new ArrayList<Integer>(), 0, 0, false);
        
        Function<Nodo, Boolean> esSolucion = (nodo) -> nodo.getEsSolucion();
        
        Function<Nodo, Integer> getCota = (nodo) -> nodo.getCota();
        
        Function<Nodo, Integer> getValor = (nodo) -> nodo.getValor();
        
        Function<Nodo, List<Nodo>> explorarNodo = (nodo) -> getExplorarNodos(mapaAgenteTareaCoste, nodo);
        
        Nodo solucion = Solucionador.ramificacionYPoda(
            nodoInicial,
            esSolucion,
            getCota,
            getValor,
            explorarNodo
            );
            
        System.out.println(solucion);
        
    }
    
    private static List<Nodo> getExplorarNodos(List<List<Integer>> mapaAgenteTareaCoste, Nodo nodo) {
    
        List<Integer> agentes = IntStream.range(0, mapaAgenteTareaCoste.size()).boxed().toList();
        
        List<Integer> nodoMapaAgenteTarea = nodo.getMapaAgenteTarea();
        
        if (nodo.getEsSolucion() || nodoMapaAgenteTarea.size() >= agentes.size()) {
            
            return new ArrayList<>();
            
        }
        
        Integer proximoAgente = agentes.get(nodoMapaAgenteTarea.size());
        
        List<Integer> mapaTareaCoste = mapaAgenteTareaCoste.get(proximoAgente);
        
        List<Nodo> nodosNuevos = new ArrayList<>();
        
        for (int tarea = 0; tarea < mapaTareaCoste.size(); tarea++) {
            
            Integer coste = mapaTareaCoste.get(tarea);
            
            List<Integer> nuevoNodoMapaAgenteTarea = new ArrayList<>(nodoMapaAgenteTarea);
            
            nuevoNodoMapaAgenteTarea.add(tarea);
            
            Integer nuevoNodoCota = getCotaOptimista(mapaAgenteTareaCoste, nuevoNodoMapaAgenteTarea);
            
            Integer nuevoNodoValor = getCoste(mapaAgenteTareaCoste, nuevoNodoMapaAgenteTarea);
            
            boolean nuevoNodoEsSolucion = nuevoNodoMapaAgenteTarea.size() == agentes.size();
            
            nodosNuevos.add(new Nodo(nuevoNodoMapaAgenteTarea, nuevoNodoCota, nuevoNodoValor, nuevoNodoEsSolucion));
            
        }
        
        return nodosNuevos;
        
    }
    
    private static Integer getCoste(List<List<Integer>> mapaAgenteTareaCoste, List<Integer> mapaAgenteTarea) {
        
        Integer coste = 0;
        
        for (int agente = 0; agente < mapaAgenteTarea.size(); agente++) {
            
            Integer tarea = mapaAgenteTarea.get(agente);
            
            coste += mapaAgenteTareaCoste.get(agente).get(tarea);
            
        }
        
        return coste;
        
    }
    
    private static Integer getCotaOptimista(List<List<Integer>> mapaAgenteTareaCoste, List<Integer> mapaAgenteTarea) {
        
        Integer coste = getCoste(mapaAgenteTareaCoste, mapaAgenteTarea);
        
        Integer minimoRestante = 0;
        
        for (int agente = mapaAgenteTarea.size(); agente < mapaAgenteTareaCoste.size(); agente++) {
            
            Integer minimoDelAgente = null;    
            
            for (int tarea = 0; tarea < mapaAgenteTareaCoste.get(agente).size(); tarea++) {
                
                if (mapaAgenteTarea.contains(tarea)) {
                    
                    continue;
                    
                }
                
                Integer costeDeLaTarea = mapaAgenteTareaCoste.get(agente).get(tarea);
                
                if (minimoDelAgente == null || minimoDelAgente > costeDeLaTarea) {
                    
                    minimoDelAgente = costeDeLaTarea;
                    
                }
                    
            }
            
            if (minimoDelAgente != null) {
                
                minimoRestante += minimoDelAgente;
            
            }
            
        }
        
        return coste + minimoRestante;
        
    }
    
}
