<br clear="both">

<h1 align="center">AI Medical Diagnosis - API de Análisis de Síntomas</h1>

###

<div><img style="100%" src="https://capsule-render.vercel.app/api?type=waving&height=70&section=header&reversal=false&text=%C2%BFQu%C3%A9%20hace%20esta%20Api?&fontSize=50&fontColor=FFFFFF&fontAlign=50&fontAlignY=50&stroke=-&animation=blink&descSize=20&descAlign=50&descAlignY=50&textBg=false&color=#FFC5D3"  /></div>

###

<img align="right" height="250" src="https://cdn-icons-png.flaticon.com/512/2913/2913133.png"  />

###

<h4 align="left">AI Medical Diagnosis API es un servicio que permite analizar síntomas médicos y almacenar los resultados en una base de datos PostgreSQL (Neon). La API recibe información del paciente junto con sus síntomas, consulta un servicio externo de análisis médico, y guarda tanto la solicitud como el resultado del análisis.<br><br>El sistema procesa datos como edad, género, historial médico, medicamentos actuales, alergias y estilo de vida del paciente. Devuelve un análisis detallado con posibles condiciones, nivel de riesgo, recomendaciones y recursos educativos. Toda la información se persiste en la base de datos para consultas futuras.</h4>

###

<br clear="both">

###

<img align="left" height="180" src="https://i.imgflip.com/65efzo.gif"  />

###

<h4 align="right"><strong>Tecnologías utilizadas:</strong><br><br>Java: JDK 17<br>IDE: IntelliJ IDEA | Visual Studio Code <br>Base de datos: PostgreSQL (Neon)<br>Gestor de dependencias: Apache Maven<br>Framework: Spring Boot</h4>

###

<div align="right">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="postgresql logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vscode/vscode-original.svg" height="40" alt="vscode logo"  />
</div>

###

<br clear="both">

###

<h3 align="left">📦 Dependencias del proyecto</h3>

<p align="left">
• <strong>spring-boot-starter-web</strong> - Para crear APIs REST<br>
• <strong>spring-boot-starter-data-jpa</strong> - Integración con bases de datos relacionales<br>
• <strong>postgresql</strong> - Driver de PostgreSQL<br>
• <strong>jackson-databind</strong> - Serialización/deserialización JSON<br>
• <strong>lombok</strong> - Reducir código boilerplate<br>
• <strong>spring-boot-starter-test</strong> - Para testing
</p>

###

## Spring Boot Web

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Spring Boot Data JPA

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## PostgreSQL Driver

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Jackson Databind

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

## Lombok

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

## Spring Boot Test

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

###

<h3 align="left">🚀 Cómo usar la API</h3>

### Endpoint Principal

**POST** `http://localhost:8080/api/symptoms/analyze`

### Headers

```
Content-Type: application/json
```

### Body (JSON)

```json
{
  "symptoms": ["severe headache", "fever", "fatigue", "sensitivity to light"],
  "patientInfo": {
    "age": 35,
    "gender": "female",
    "height": 165,
    "weight": 65,
    "medicalHistory": ["hypertension", "seasonal allergies"],
    "currentMedications": ["lisinopril 10mg", "cetirizine 10mg"],
    "allergies": ["penicillin"],
    "lifestyle": {
      "smoking": false,
      "alcohol": "occasional",
      "exercise": "moderate",
      "diet": "balanced"
    }
  },
  "lang": "en"
}
```

### Respuesta Esperada

```json
{
  "status": "success",
  "message": "Symptoms analysis completed successfully",
  "result": {
    "disclaimer": "This analysis is for educational purposes only...",
    "analysis": {
      "possibleConditions": [...],
      "generalAdvice": {...},
      "educationalResources": {...}
    }
  }
}
```

###

<h3 align="left">⚙️ Configuración</h3>

### application.yaml

```yaml
spring:
  application:
    name: Postgres
  datasource:
    url: jdbc:postgresql://[HOST]/[DATABASE]?sslmode=require
    username: [USERNAME]
    password: [PASSWORD]
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

###

<h3 align="left">📊 Estructura de la Base de Datos</h3>

```sql
CREATE TABLE IF NOT EXISTS symptom_analysis (
    id BIGSERIAL PRIMARY KEY,
    symptoms TEXT NOT NULL,
    patient_age INTEGER,
    patient_gender VARCHAR(20),
    patient_height DECIMAL,
    patient_weight DECIMAL,
    medical_history TEXT,
    current_medications TEXT,
    allergies TEXT,
    lifestyle TEXT,
    lang VARCHAR(10),
    analysis_result TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

###

<h3 align="left">🏗️ Arquitectura del Proyecto</h3>

```
Postgres/
├── src/main/java/AI_Medical/Postgres/
│   ├── model/
│   │   ├── SymptomAnalysis.java
│   │   └── SymptomRequest.java
│   ├── repository/
│   │   └── SymptomAnalysisRepository.java
│   ├── service/
│   │   └── SymptomService.java
│   ├── rest/
│   │   └── SymptomController.java
│   └── PostgresApplication.java
└── src/main/resources/
    ├── application.yaml
    └── schema.sql
```

###

<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&height=70&section=footer&color=gradient" />
</div>
