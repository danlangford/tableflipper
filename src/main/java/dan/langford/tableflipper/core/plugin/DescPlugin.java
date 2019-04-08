package dan.langford.tableflipper.core.plugin;

import dan.langford.tableflipper.core.TomService;
import java.util.Optional;
import javax.inject.Inject;

public class DescPlugin implements TomPlugin {

  private final TomService toms;

  @Inject
  public DescPlugin(TomService toms) {
    this.toms = toms;
  }

  @Override
  public Optional<String> resolve(String desc, boolean verbose) {
    return Optional.ofNullable(toms.getModel().getDescriptions().get(desc));
  }

  public TomService getToms() {
    return this.toms;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof DescPlugin)) return false;
    final DescPlugin other = (DescPlugin) o;
    if (!other.canEqual(this)) return false;
    final Object this$toms = this.getToms();
    final Object other$toms = other.getToms();
      return this$toms == null ? other$toms == null : this$toms.equals(other$toms);
  }

  protected boolean canEqual(final Object other) {
    return other instanceof DescPlugin;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $toms = this.getToms();
    result = result * PRIME + ($toms == null ? 43 : $toms.hashCode());
    return result;
  }

  public String toString() {
    return "DescPlugin(toms=" + this.getToms() + ")";
  }
}
