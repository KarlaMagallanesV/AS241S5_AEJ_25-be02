CREATE TABLE IF NOT EXISTS pet_emergency_analysis (
    id BIGSERIAL PRIMARY KEY,
    pet_type VARCHAR(50) NOT NULL,
    emergency TEXT NOT NULL,
    language VARCHAR(10),
    analysis_result TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
