import java.util.function.Function;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

public class Solucionador {
    
    public static <Nodo> Nodo ramificacionYPoda(
        Nodo nodoInicial,
        Function<Nodo, Boolean> esSolucion,
        Function<Nodo, Integer> getCota,
        Function<Nodo, Integer> getValor,
        Function<Nodo, List<Nodo>> explorarNodo
        ) {
             
        PriorityQueue<Nodo> pila = new PriorityQueue<Nodo>((Nodo a, Nodo b) -> {
        
            Integer valorA = esSolucion.apply(a) ? getValor.apply(a) : getCota.apply(a);
            
            Integer valorB = esSolucion.apply(b) ? getValor.apply(b) : getCota.apply(b);
            
            return valorA - valorB;
            
        });
        
        pila.add(nodoInicial);
        
        while (pila.size() > 0) {
            
            Nodo nodo = pila.poll();
            
            if (esSolucion.apply(nodo)) {
                
                return nodo;
                
            }
            
            pila.addAll(explorarNodo.apply(nodo));
            
        }
        
        return pila.poll();
        
    }
    
}
