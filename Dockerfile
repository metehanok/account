# Derleme aşaması
FROM openjdk:11 AS build

# Çalışma dizinini ayarla
WORKDIR /account

# Maven Wrapper ve bağımlılık dosyalarını kopyala
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Bağımlılıkları indir (hatayı kontrol etmek için)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -DskipTests

# Kaynak dosyaları kopyala ve derleme işlemini başlat
COPY src src
RUN ./mvnw package -DskipTests -e -X

# Çalışma aşaması
FROM openjdk:11

# Derlenen JAR dosyasını hedef imaj içine kopyala
COPY --from=build /account/target/*.jar /account.jar

# Giriş noktası
ENTRYPOINT ["java", "-jar", "/account.jar"]


