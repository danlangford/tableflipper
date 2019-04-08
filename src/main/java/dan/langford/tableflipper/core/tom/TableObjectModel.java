package dan.langford.tableflipper.core.tom;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Table Object Model, or TOM describes how the YAML (currently) file is to be structured */
public class TableObjectModel {

  private Map<String, Table> tables = Collections.emptyMap();
  private Map<String, String> descriptions = Collections.emptyMap();

  public TableObjectModel() {}

  public Map<String, Table> getTables() {
    return this.tables;
  }

  public Map<String, String> getDescriptions() {
    return this.descriptions;
  }

  public void setTables(Map<String, Table> tables) {
    this.tables = tables;
  }

  public void setDescriptions(Map<String, String> descriptions) {
    this.descriptions = descriptions;
  }

  public String toString() {
    return "TableObjectModel(tables="
        + this.getTables()
        + ", descriptions="
        + this.getDescriptions()
        + ")";
  }

  public static class Table {
    String roll, name;
    Map<String, String> results;
    List<String> oneOf;

    public Table() {}

    public String getRoll() {
      return roll;
    }

    public void setRoll(String roll) {
      this.roll = roll;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Map<String, String> getResults() {
      return results;
    }

    public void setResults(Map<String, String> results) {
      this.results = results;
    }

    public List<String> getOneOf() {
      return oneOf;
    }

    public void setOneOf(List<String> oneOf) {
      this.oneOf = oneOf;
    }

    // TODO see if i can create a RollGroup that can test given numbers against a range
  }
}
