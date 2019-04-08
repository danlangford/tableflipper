package dan.langford.tableflipper.core;

import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

import dan.langford.tableflipper.core.tom.TableObjectModel;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * someday this will need to do more to allow at run time the reading in of a new Table Object Model
 * and merging it with existing entries
 */
public class TomService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static Pattern yaml = Pattern.compile(".*\\.ya?ml$", CASE_INSENSITIVE);

  private TableObjectModel model;

  public TomService() {

    try (ScanResult scanResult = new ClassGraph().whitelistPathsNonRecursive("tables").scan()) {
      scanResult
          .getResourcesMatchingPattern(yaml)
          .forEachInputStream(
              (Resource res, InputStream stream) -> {
                this.load(new InputStreamReader(requireNonNull(stream)));
              });
    }

    String userHome = System.getProperty("user.home");
    if (userHome != null && !userHome.trim().isEmpty()) {
      try {
        Files.newDirectoryStream(
                get(userHome + "/tableflipper"),
                entry -> entry.toString().endsWith(".yml") || entry.toString().endsWith(".yaml"))
            .forEach(
                p -> {
                  try {
                    this.load(Files.newBufferedReader(p));
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                });
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void unload() {
    this.model = null;
  }

  public void load(Reader io) {
    if (model == null) {
      model = new Yaml(new Constructor(TableObjectModel.class)).load(io);
    } else {
      putAll(new Yaml(new Constructor(TableObjectModel.class)).load(io));
    }
  }

  public void putAll(TableObjectModel altModel) {
    Map<String, TableObjectModel.Table> tempTab = new HashMap<>();
    tempTab.putAll(model.getTables());
    tempTab.putAll(altModel.getTables());
    model.setTables(tempTab);

    Map<String, String> tempDesc = new HashMap<>();
    tempDesc.putAll(model.getDescriptions());
    tempDesc.putAll(altModel.getDescriptions());
    model.setDescriptions(tempDesc);
  }

  public TableObjectModel getModel() {
    if (this.model == null) {
      throw new IllegalStateException("model is not loaded");
    }
    return this.model;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof TomService)) return false;
    final TomService other = (TomService) o;
    if (!other.canEqual(this)) return false;
    final Object this$model = this.getModel();
    final Object other$model = other.getModel();
      return this$model == null ? other$model == null : this$model.equals(other$model);
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
