package AI_Medical.Postgres.rest;

import AI_Medical.Postgres.model.PetEmergencyRequest;
import AI_Medical.Postgres.model.PetEmergencyAnalysis;
import AI_Medical.Postgres.service.PetEmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pet-emergency")
@RequiredArgsConstructor
public class PetEmergencyController {
    private final PetEmergencyService service;
    
    @PostMapping("/analyze")
    public Mono<Object> analyze(@RequestBody PetEmergencyRequest request) {
        return service.analyzeAndSave(request);
    }
    
    @GetMapping("/list")
    public Flux<PetEmergencyAnalysis> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public Mono<PetEmergencyAnalysis> getById(@PathVariable Long id) {
        return service.findById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<PetEmergencyAnalysis> update(@PathVariable Long id, @RequestBody PetEmergencyRequest request) {
        return service.update(id, request);
    }
    
    @PatchMapping("/logical/{id}")
    public Mono<PetEmergencyAnalysis> deleteLogical(@PathVariable Long id) {
        return service.deleteLogical(id);
    }

    @PatchMapping("/restore/{id}")
    public Mono<PetEmergencyAnalysis> restoreLogical(@PathVariable Long id) {
        return service.restoreLogical(id);
    }
    
    @DeleteMapping("/physical/{id}")
    public Mono<Void> deletePhysical(@PathVariable Long id) {
        return service.deletePhysical(id);
    }
}
