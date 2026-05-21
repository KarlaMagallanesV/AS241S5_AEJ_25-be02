package AI_Medical.Postgres.model;

import lombok.Data;

@Data
public class PetEmergencyRequest {
    private String petType;
    private String emergency;
    private String language;
}
