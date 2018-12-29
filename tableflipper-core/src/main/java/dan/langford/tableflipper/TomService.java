package dan.langford.tableflipper;

import dan.langford.tableflipper.tom.TableObjectModel;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * someday this will need to do more to allow at run time the reading in of a new Table Object Model and merging it with existing entries
 */
public class TomService {

    private final TableObjectModel model = new Yaml(new Constructor(TableObjectModel.class)).load(getClass().getClassLoader().getResourceAsStream("5e-srd-5_1.yml"));

    public TomService() {
    }

    public TableObjectModel getModel() {
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
