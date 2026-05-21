package AI_Medical.Postgres.service;

import AI_Medical.Postgres.model.PetEmergencyRequest;
import AI_Medical.Postgres.model.PetEmergencyAnalysis;
import AI_Medical.Postgres.repository.PetEmergencyAnalysisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PetEmergencyService {
    private final PetEmergencyAnalysisRepository repository;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Value("${rapidapi.key:}")
    private String rapidApiKey;
    
    public Mono<Object> analyzeAndSave(PetEmergencyRequest request) {
        if (request.getLanguage() == null || request.getLanguage().isEmpty()) {
            request.setLanguage("es");
        }
        
        String apiUrl = "https://ai-pet-symptom-checker-api-vet-diagnosis-emergency-help.p.rapidapi.com/emergencyGuidance";
        
        Map<String, String> requestBody = Map.of(
            "petType", request.getPetType(),
            "emergency", request.getEmergency()
        );
        
        return webClientBuilder.build()
            .post()
            .uri(apiUrl + "?language=" + request.getLanguage() + "&noqueue=1")
            .header("x-rapidapi-key", rapidApiKey)
            .header("x-rapidapi-host", "ai-pet-symptom-checker-api-vet-diagnosis-emergency-help.p.rapidapi.com")
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Object.class)
            .flatMap(apiResponse -> {
                try {
                    PetEmergencyAnalysis analysis = new PetEmergencyAnalysis();
                    analysis.setPetType(request.getPetType());
                    analysis.setEmergency(request.getEmergency());
                    analysis.setLanguage(request.getLanguage());
                    analysis.setAnalysisResult(objectMapper.writeValueAsString(apiResponse));
                    analysis.setCreatedAt(LocalDateTime.now());
                    analysis.setDeleted(false);
                    
                    return repository.save(analysis)
                        .thenReturn(apiResponse);
                } catch (JsonProcessingException e) {
                    return Mono.error(e);
                }
            });
    }
    
    public Flux<PetEmergencyAnalysis> findAll() {
        return repository.findByDeletedFalse();
    }
    
    public Mono<PetEmergencyAnalysis> findById(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Registro no encontrado con id: " + id)))
            .filter(analysis -> !analysis.getDeleted())
            .switchIfEmpty(Mono.error(new RuntimeException("Registro eliminado con id: " + id)));
    }
    
    public Mono<PetEmergencyAnalysis> update(Long id, PetEmergencyRequest request) {
        return repository.findById(id)
            .filter(analysis -> !analysis.getDeleted())
            .flatMap(analysis -> {
                String newLanguage = request.getLanguage();
                if (newLanguage == null || newLanguage.isEmpty()) {
                    return Mono.error(new IllegalArgumentException("El idioma es requerido"));
                }

                String apiUrl = "https://ai-pet-symptom-checker-api-vet-diagnosis-emergency-help.p.rapidapi.com/emergencyGuidance";

                Map<String, String> requestBody = Map.of(
                    "petType", analysis.getPetType(),
                    "emergency", analysis.getEmergency()
                );

                return webClientBuilder.build()
                    .post()
                    .uri(apiUrl + "?language=" + newLanguage + "&noqueue=1")
                    .header("x-rapidapi-key", rapidApiKey)
                    .header("x-rapidapi-host", "ai-pet-symptom-checker-api-vet-diagnosis-emergency-help.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .flatMap(apiResponse -> {
                        try {
                            analysis.setLanguage(newLanguage);
                            analysis.setAnalysisResult(objectMapper.writeValueAsString(apiResponse));
                            return repository.save(analysis);
                        } catch (JsonProcessingException e) {
                            return Mono.error(e);
                        }
                    });
            });
    }
    
    public Mono<PetEmergencyAnalysis> deleteLogical(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Registro no encontrado con id: " + id)))
            .flatMap(analysis -> {
                analysis.setDeleted(true);
                return repository.save(analysis);
            });
    }

    public Mono<PetEmergencyAnalysis> restoreLogical(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Registro no encontrado con id: " + id)))
            .flatMap(analysis -> {
                analysis.setDeleted(false);
                return repository.save(analysis);
            });
    }
    
    public Mono<Void> deletePhysical(Long id) {
        return repository.deleteById(id);
    }
}
