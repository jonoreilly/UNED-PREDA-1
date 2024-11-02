import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main() throws Exception {
        
        List<List<Integer>> mapaAgenteTareaCoste = new ArrayList<>();
        
        mapaAgenteTareaCoste.add(new ArrayList<>());
        mapaAgenteTareaCoste.add(new ArrayList<>());
        mapaAgenteTareaCoste.add(new ArrayList<>());
        mapaAgenteTareaCoste.add(new ArrayList<>());
        
        mapaAgenteTareaCoste.get(0).add(11);
        mapaAgenteTareaCoste.get(0).add(12);
        mapaAgenteTareaCoste.get(0).add(18);
        mapaAgenteTareaCoste.get(0).add(40);
        
        mapaAgenteTareaCoste.get(1).add(14);
        mapaAgenteTareaCoste.get(1).add(15);
        mapaAgenteTareaCoste.get(1).add(13);
        mapaAgenteTareaCoste.get(1).add(22);
        
        mapaAgenteTareaCoste.get(2).add(11);
        mapaAgenteTareaCoste.get(2).add(17);
        mapaAgenteTareaCoste.get(2).add(19);
        mapaAgenteTareaCoste.get(2).add(23);
        
        mapaAgenteTareaCoste.get(3).add(17);
        mapaAgenteTareaCoste.get(3).add(14);
        mapaAgenteTareaCoste.get(3).add(20);
        mapaAgenteTareaCoste.get(3).add(28);
        
        Nodo solucion = DistribuidorDeTareas.distribuirTareas(mapaAgenteTareaCoste);
        
        System.out.println("mapaAgenteTareaCoste: " + mapaAgenteTareaCoste);
        
        System.out.println("solucion: " + solucion);
        
    }
    
}
