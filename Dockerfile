# Usa un'immagine base con JRE 17 (più leggera del JDK)
FROM eclipse-temurin:17-jre-alpine

# Crea un utente non-root per sicurezza
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Directory di lavoro
WORKDIR /app

# Copia il JAR dall'host
COPY app.jar app.jar

# Esponi la porta dell'applicazione
EXPOSE 8080

# Variabili d'ambiente opzionali
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENV SPRING_PROFILES_ACTIVE=production

# Comando per avviare l'applicazione
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
