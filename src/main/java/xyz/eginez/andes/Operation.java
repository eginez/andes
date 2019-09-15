package xyz.eginez.andes;

import java.util.Optional;

public interface Operation {
  String getName();

  int getArgsLen();

  Optional<Object> getAux();

  Value interpret(String[] arguments, State state);
}

