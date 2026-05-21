package AI_Medical.Postgres.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Table("pet_emergency_analysis")
public class PetEmergencyAnalysis {
    @Id
    private Long id;
    
    private String petType;
    private String emergency;
    private String language;
    private String analysisResult;
    private LocalDateTime createdAt;
    private Boolean deleted = false;
}
