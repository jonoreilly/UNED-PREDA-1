import java.util.List;
import java.util.ArrayList;

public class Nodo {
    
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
    
    public String toString() {
        
        return "{ mapaAgenteTarea: " + mapaAgenteTarea.stream().map(t -> t + 1).toList() + ", cota: " + cota + ", valor: " + valor + ", esSolucion: " + esSolucion + " }";
        
    }
    
}