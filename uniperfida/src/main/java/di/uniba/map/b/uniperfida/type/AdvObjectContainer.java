
package di.uniba.map.b.uniperfida.type;

import java.util.ArrayList;
import java.util.List;

// oggetto contenitore che puo contenere altri oggetti, estende AdvObject e aggiunge un attributo list che contiene oggetti
public class AdvObjectContainer extends AdvObject {

    private List<AdvObject> list = new ArrayList<>();

    public AdvObjectContainer(int id) {
        super(id);
    }

    public AdvObjectContainer(int id, String name) {
        super(id, name);
    }

    public AdvObjectContainer(int id, String name, String description) {
        super(id, name, description);
    }

    public List<AdvObject> getList() {
        return list;
    }

    public void setList(List<AdvObject> list) {
        this.list = list;
    }

    public void add(AdvObject o) {
        list.add(o);
    }

    public void remove(AdvObject o) {
        list.remove(o);
    }

}
