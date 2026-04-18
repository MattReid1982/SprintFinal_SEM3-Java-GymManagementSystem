# ✅ Checkstyle Configuration Guide

## Overview

This project uses **Apache Maven Checkstyle Plugin** to enforce consistent code style and quality standards based on the **Google Java Style Guide**. Checkstyle is an open-source tool that automatically checks Java source code for adherence to a defined coding standard.

---

## 🛠️ Plugin Configuration

### Maven Dependency

The checkstyle plugin is configured in `pom.xml` with the following settings:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.1</version>
    <configuration>
        <configLocation>google_checks.xml</configLocation>
        <encoding>UTF-8</encoding>
        <consoleOutput>true</consoleOutput>
        <failsOnError>false</failsOnError>
    </configuration>
    <executions>
        <execution>
            <id>checkstyle</id>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Configuration Details

| Setting | Value | Description |
|---------|-------|-------------|
| **Plugin Version** | 3.3.1 | Latest stable Maven Checkstyle Plugin version |
| **Config Location** | `google_checks.xml` | Google Java Style Guide configuration |
| **Encoding** | UTF-8 | Source file encoding standard |
| **Console Output** | `true` | Display violations in Maven console |
| **Fail on Error** | `false` | Don't block builds on violations (warnings only) |
| **Execution Phase** | `validate` | Run during Maven validate phase |

### External Repository

The project includes a reference to the java-checkstyle-autofix repository for enhanced tooling:

```xml
<repository>
    <id>java-checkstyle-autofix</id>
    <name>Java Checkstyle Autofix Repository</name>
    <url>https://github.com/kbishopzz/java-checkstyle-autofix</url>
</repository>
```

---

## 🚀 Running Checkstyle

### During Build

Checkstyle runs automatically during the Maven build process in the `validate` phase:

```bash
mvn clean compile
```

### Explicit Validation

Run checkstyle independently:

```bash
mvn checkstyle:check
```

### Generate HTML Report

Create a detailed HTML report of violations:

```bash
mvn checkstyle:checkstyle
```

Output will be generated at: `target/checkstyle-result.xml`

Convert to HTML:

```bash
mvn checkstyle:checkstyle && open target/site/checkstyle.html
```

---

## 📋 Google Style Guide Summary

The project follows **Google Java Style Guide** conventions:

### Line Length

- **Maximum 80 characters** per line
- Line breaks at logical breakpoints

### Naming Conventions

| Element | Convention | Example |
|---------|-----------|---------|
| Classes | UpperCamelCase | `GymClassDAO` |
| Methods | lowerCamelCase | `getMemberID()` |
| Constants | UPPER_SNAKE_CASE | `MAX_CAPACITY` |
| Package | lowercase.dot.separated | `com.keyin.gymmanagement` |

### Indentation

- **4 spaces** per indentation level
- No tabs
- Consistent spacing around operators

### Braces

- **Allman style** (opening brace on same line)
- Example:
  ```java
  if (condition) {
      statement;
  }
  ```

### JavaDoc Comments

- Required for all public classes and methods
- Format: `/** Description. */`
- @param and @return tags for complex methods

### Import Statements

- No wildcard imports (`import java.util.*` ❌)
- Organized and sorted alphabetically
- Grouped by package

---

## ⚠️ Common Violations & Fixes

### 1. Line Too Long (>80 characters)

**Violation:**
```java
public static void processLongMethodName(String firstParameter, String secondParameter) {
    // Line exceeds 80 characters
}
```

**Fix:**
```java
public static void processLongMethodName(
        String firstParameter,
        String secondParameter) {
    // Broken into multiple lines
}
```

### 2. Missing JavaDoc

**Violation:**
```java
public String getUsername() {
    return username;
}
```

**Fix:**
```java
/**
 * Gets the username.
 */
public String getUsername() {
    return username;
}
```

### 3. Unused Imports

**Violation:**
```java
import java.util.ArrayList;
import java.util.List;  // Never used
import java.io.IOException;

public class Example {
    private ArrayList<String> items;
}
```

**Fix:**
```java
import java.util.ArrayList;

public class Example {
    private ArrayList<String> items;
}
```

### 4. Wildcard Imports

**Violation:**
```java
import java.util.*;
import java.sql.*;
```

**Fix:**
```java
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
```

### 5. Inconsistent Indentation

**Violation:**
```java
if (condition) {
statement;  // 0 spaces
}
```

**Fix:**
```java
if (condition) {
    statement;  // 4 spaces
}
```

---

## 🔍 Interpreting Checkstyle Output

### Console Output Example

```
[INFO] Starting audit...
[WARNING] src/main/java/com/keyin/gymmanagement/models/Member.java:45: Line is longer than 80 characters (found 95).
[WARNING] src/main/java/com/keyin/gymmanagement/dao/MemberDAO.java:120: Missing a Javadoc comment.
[INFO] Audit done.
[INFO] Checkstyle violations: 2
```

### Understanding Messages

- **Line Number** - Location of violation (filename:line_number)
- **Column Number** - Character position (if shown)
- **Message** - Description of what violates the style guide
- **Severity** - WARNING (reported) or ERROR (fails build if failsOnError=true)

---

## ✨ Best Practices

### 1. Format Code Regularly

Use IDE formatting before committing:

**IntelliJ IDEA:**
```
Code → Reformat Code (Cmd+Opt+L on Mac)
```

**Eclipse:**
```
Source → Format (Shift+Cmd+F on Mac)
```

### 2. Configure IDE with Google Style

**IntelliJ IDEA:**
1. Settings → Editor → Code Style → Java
2. Set Line length limit to **80**
3. Import Google Code Style scheme

**Eclipse:**
1. Eclipse → Preferences → Java → Code Style
2. Import google-java-style.xml

### 3. Add Pre-commit Hook (Optional)

Create `.git/hooks/pre-commit`:

```bash
#!/bin/bash
mvn checkstyle:check
if [ $? -ne 0 ]; then
    echo "Checkstyle violations found. Commit aborted."
    exit 1
fi
```

### 4. Commit Frequently

Check style before each commit to catch violations early.

---

## 📚 Resources

- **Google Java Style Guide:** https://google.github.io/styleguide/javaguide.html
- **Apache Maven Checkstyle Plugin:** https://maven.apache.org/plugins/maven-checkstyle-plugin/
- **Checkstyle Project:** https://checkstyle.sourceforge.io/
- **java-checkstyle-autofix:** https://github.com/kbishopzz/java-checkstyle-autofix

---

## 🔧 Troubleshooting

### Plugin Not Running

**Problem:** Checkstyle doesn't execute during build

**Solution:**
```bash
# Clear Maven cache and rebuild
mvn clean checkstyle:check
```

### False Positives

**Problem:** Checkstyle reports violations that seem correct

**Solution:**
1. Check current configuration in `pom.xml`
2. Verify `google_checks.xml` is in classpath
3. Review violation against Google Style Guide at https://google.github.io/styleguide/javaguide.html

### Build Fails on Checkstyle

**Problem:** Build stops due to checkstyle violations (if `failsOnError=true`)

**Solution:**
1. Fix reported violations
2. Re-run `mvn clean compile`
3. Or set `failsOnError=false` in pom.xml to convert to warnings

---

## 🎯 Configuration Summary

| Aspect | Setting | Rationale |
|--------|---------|-----------|
| **Standard** | Google Java Style | Industry standard, widely adopted |
| **Line Length** | 80 characters | Improves readability, fits most monitors |
| **Indentation** | 4 spaces | Standard Java convention |
| **Failure Policy** | Warnings only | Don't block development, encourage compliance |
| **Console Output** | Enabled | Real-time feedback during builds |
| **Repository** | java-checkstyle-autofix | Enhanced tooling for auto-fixes (future) |

---

## 📝 Notes for Team Members

- Checkstyle runs **automatically** during `mvn clean compile`
- Violations are **non-blocking** (reported as warnings)
- All team members should aim for **zero warnings** before committing
- Use IDE formatting tools to maintain compliance
- Review Google Style Guide for quick reference: https://google.github.io/styleguide/javaguide.html

---

**Last Updated:** April 2026  
**Version:** 1.0  
**Plugin Version:** Maven Checkstyle Plugin 3.3.1

