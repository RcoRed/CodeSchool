package org.generation.italy.codeSchool.model.data.exceptions;
import static org.generation.italy.codeSchool.model.data.Constants.*;

public class EntityNotFoundException extends Exception{

   public EntityNotFoundException(String message) {
      super(message);
   }
   public EntityNotFoundException() {
      super(ENTITY_NOT_FOUND);
   }
}
