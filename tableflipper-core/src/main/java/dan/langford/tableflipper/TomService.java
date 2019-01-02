package dan.langford.tableflipper;

import dan.langford.tableflipper.tom.TableObjectModel;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStreamReader;
import java.io.Reader;

import static java.util.Objects.requireNonNull;

/**
 * someday this will need to do more to allow at run time the reading in of a new Table Object Model and merging it with existing entries
 */
public class TomService {

    private TableObjectModel model;

    public TomService() {
        this.load(new InputStreamReader(requireNonNull(getClass().getClassLoader().getResourceAsStream("5e-srd-5_1.yml"))));
    }

    public void unload(){
        this.model=null;
    }

    public void load(Reader io){
        if(this.model==null) {
            this.model = new Yaml(new Constructor(TableObjectModel.class)).load(io);
        } else {
            this.model.putAll(new Yaml(new Constructor(TableObjectModel.class)).load(io));
        }
    }

    public TableObjectModel getModel() {
        if(this.model==null){
            throw new IllegalStateException("model is not loaded");
        }
        return this.model;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TomService)) return false;
        final TomService other = (TomService) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$model = this.getModel();
        final Object other$model = other.getModel();
        if (this$model == null ? other$model != null : !this$model.equals(other$model)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TomService;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $model = this.getModel();
        result = result * PRIME + ($model == null ? 43 : $model.hashCode());
        return result;
    }

    public String toString() {
        return "TomService(model=" + this.getModel() + ")";
    }
}
