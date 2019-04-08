package dan.langford.tableflipper.core.plugin;

import java.util.Optional;

public interface TomPlugin {

  default Optional<String> resolve(String input) {
    return resolve(input, false);
  }

  Optional<String> resolve(String input, boolean verbose);
}
