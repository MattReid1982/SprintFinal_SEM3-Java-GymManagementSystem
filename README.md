# SprintFinal_SEM3-Java-GymManagementSystem

This project is configured as a Maven application with a basic console UI for gym management.

## Project structure

- Java package: `com.keyin.gymmanagement`
- Model package: `com.keyin.gymmanagement.models`
- Main class: `com.keyin.gymmanagement.App`

## Maven plugins configured

- `maven-compiler-plugin` for Java 17 compilation
- `maven-jar-plugin` for jar packaging and manifest main class
- `exec-maven-plugin` for direct execution from Maven

## Build and run

1. Build the project:

```bash
mvn clean package
```

2. Run directly through Maven:

```bash
mvn compile exec:java
```

3. Use the team-friendly execution alias:

```bash
mvn exec:java@run
```

### Console output styling

The main console uses ANSI helper methods in `src/main/java/com/keyin/gymmanagement/App.java` to colorize output:

- `printSection(...)` for section headers
- `printSuccess(...)` for success messages
- `printError(...)` for error messages
- `printPrompt(...)` for user prompts

> Run these commands from the project root: `/Users/student2/Documents/Keyin/SprintFinal_SEM3-Java-GymManagementSystem`.
