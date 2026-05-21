package AI_Medical.Postgres.repository;

import AI_Medical.Postgres.model.PetEmergencyAnalysis;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PetEmergencyAnalysisRepository extends ReactiveCrudRepository<PetEmergencyAnalysis, Long> {
    Flux<PetEmergencyAnalysis> findByDeletedFalse();
}
