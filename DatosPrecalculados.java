import java.util.List;
import java.util.stream.IntStream;

public class DatosPrecalculados {
    
    private List<List<Integer>> mapaAgenteTareaCoste;
    
    private List<Integer> agentes;
    
    private List<Integer> tareas;
    
    private List<Integer> mapaAgenteMinimoCoste;
    
    public DatosPrecalculados(List<List<Integer>> mapaAgenteTareaCoste) {
        
        this.mapaAgenteTareaCoste = mapaAgenteTareaCoste;
        
        this.agentes = IntStream.range(0, mapaAgenteTareaCoste.size()).boxed().toList();
        
        this.tareas = IntStream.range(0, mapaAgenteTareaCoste.get(0).size()).boxed().toList();
        
        this.mapaAgenteMinimoCoste = mapaAgenteTareaCoste.stream().map(mapaTareaCoste -> {
           
            Integer minimoCoste = null;
        
            for (Integer coste : mapaTareaCoste) {
                
                if (minimoCoste == null || minimoCoste > coste) {
                    
                    minimoCoste = coste;
                    
                }
                
            }
            
            return minimoCoste;
            
        }).toList();
        
    }
    
    public List<List<Integer>> getMapaAgenteTareaCoste() {
        
        return this.mapaAgenteTareaCoste.stream().map(matc -> matc.stream().toList()).toList();
        
    }
    
    public List<Integer> getAgentes() {
        
        return this.agentes.stream().toList();
        
    }
    
    public List<Integer> getTareas() {
        
        return this.tareas.stream().toList();
        
    }
    
    public List<Integer> getMapaAgenteMinimoCoste() {
        
        return this.mapaAgenteMinimoCoste.stream().toList();
        
    }
    
}
