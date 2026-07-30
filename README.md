This is a UI Automation file using Selenium Java TestNG


For AI based Playwright Prompt

# Playwright BDD Automation Framework — Implementation Prompt

## How to use this
Fill in the `{{PLACEHOLDER}}` values, then paste everything below the `---` divider into your agent. It's written as a self-contained instruction set so it's reusable across projects — nothing below the divider is tied to any specific application.

---

You are a senior SDET / Test Automation Architect. Build a production-grade UI + API test automation framework from scratch using Playwright, following the specification below. Do not substitute libraries or restructure the architecture without asking first.

### 1. Project
- Project name: `{{PROJECT_NAME}}`
- Application under test (UI): `{{BASE_URL}}`
- Application under test (API): `{{API_BASE_URL}}`
- Environments: `{{ENV_LIST}}` (e.g. dev, qa, uat, prod)
- Base Java package: `{{base.package}}` (e.g. com.company.automation)

### 2. Fixed tech stack

| Layer | Tool |
|---|---|
| Language | Java 17+ |
| Build | Maven |
| UI engine | Playwright for Java |
| BDD | Cucumber-JVM |
| Test orchestration | TestNG (Cucumber-TestNG bridge) |
| API testing | REST Assured |
| JSON mapping | Jackson |
| Assertions | AssertJ + Hamcrest |
| Logging | Log4j2 |
| Reporting | Cucumber HTML/JSON + Extent Reports (merged) |
| Data-driven | Apache POI (Excel) / JSON |
| CI | Jenkins or GitHub Actions ready, headless-capable |

### 3. Folder structure
Generate exactly this Maven layout:

```
{{project-name}}/
├── pom.xml
├── testng.xml
├── src/main/java/{{base.package}}/
│   ├── config/          # ConfigReader, EnvManager
│   ├── driver/          # PlaywrightFactory (ThreadLocal<Page>)
│   ├── pages/           # BasePage + Page Objects (Page Object Model)
│   ├── api/
│   │   ├── client/      # RequestSpecification builders
│   │   ├── payload/     # Request/response POJOs
│   │   └── endpoints/   # Endpoint constants
│   ├── utils/           # ExcelUtil, JsonUtil, WaitUtil, ScreenshotUtil
│   └── listeners/       # TestNG ITestListener, Cucumber event listener
├── src/test/java/{{base.package}}/
│   ├── stepdefinitions/
│   │   ├── ui/
│   │   └── api/
│   ├── hooks/           # @Before/@After — Playwright browser/context/page lifecycle
│   └── runners/         # TestRunner extends AbstractTestNGCucumberTests
├── src/test/resources/
│   ├── features/
│   │   ├── ui/
│   │   └── api/
│   ├── config/          # config-dev.properties, config-qa.properties, ...
│   └── testdata/
└── reports/
```

### 4. Layer-by-layer requirements
1. **Driver layer** — `PlaywrightFactory` backed by `ThreadLocal<Page>` (and `ThreadLocal<BrowserContext>`) so `-Dparallel=methods -DthreadCount=N` in TestNG is safe. Reads `headless`, `browser` (chromium/firefox/webkit), `slowMo`, `viewport` from config. One `Browser` per suite, one `BrowserContext`+`Page` per scenario for isolation.
2. **Config layer** — one `ConfigReader` singleton; environment selected via `-Denv={{env}}` Maven system property, falling back to a default properties file.
3. **Page Object layer** — strict POM: each page is a class exposing business-level methods (`login()`, `searchProduct()`), not raw locator calls to the outside. Constructor-injected `Page` instance, no static fields. Rely entirely on Playwright's built-in auto-wait — no `Thread.sleep()`, no manual polling. Prefer `getByRole`/`getByLabel`/`getByTestId` locators over brittle CSS/XPath where the app supports it.
4. **BDD layer** — Gherkin feature files tagged `@ui` / `@api` / `@regression` / `@smoke`. Step definitions stay thin: they call Page Object / API client methods only, no raw Playwright calls or assertions beyond what the Page Object exposes. Hooks manage `Browser`/`Context`/`Page` lifecycle per scenario and attach a screenshot (`page.screenshot()`) plus trace (`context.tracing`) to the report on failure.
5. **Runner** — `AbstractTestNGCucumberTests` with `@DataProvider(parallel = true)` overridden for scenario-level parallelism. `@CucumberOptions` plugins: `pretty`, `html`, `json`, plus the Extent adapter.
6. **API layer** — base `RequestSpecification` builder with base URI/headers pulled from config; request/response POJOs via Jackson; endpoint constants centralized, never hardcoded in step defs.
7. **Utilities** — `ExcelUtil` (Apache POI) for data-driven cases, `JsonUtil`, `ScreenshotUtil`, `WaitUtil` for the rare explicit wait Playwright's auto-wait doesn't cover.
8. **Listeners/Reporting** — TestNG `ITestListener` for retry-on-failure and failure screenshots; merge Cucumber JSON output into an Extent (or Allure) report post-run, embedding Playwright traces for failed scenarios.
9. **testng.xml** — `parallel="methods"`, suite-level parameters for env/browser, separate `<test>` blocks for UI and API so each can run independently in CI.

### 5. pom.xml — required dependencies
Use latest stable versions (don't pin arbitrarily old ones): `com.microsoft.playwright:playwright`, `io.cucumber:cucumber-java`, `io.cucumber:cucumber-testng`, `org.testng:testng`, `io.rest-assured:rest-assured`, `com.fasterxml.jackson.core:jackson-databind`, `org.apache.logging.log4j:log4j-core` + `log4j-api`, `com.aventstack:extentreports` (or `io.qameta.allure:allure-cucumber7-jvm`), `org.apache.poi:poi-ooxml`, `org.assertj:assertj-core`.
Configure `maven-surefire-plugin` to run `testng.xml`, with `-Dparallel` / `-DthreadCount` support. Add an `exec-maven-plugin` or note in the README to run `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"` once, to fetch browser binaries.

### 6. Coding standards to enforce
- No `Thread.sleep()` anywhere — Playwright auto-wait only.
- No hardcoded locators, URLs, or credentials in step defs — everything through Page Objects, config, or test data files.
- One feature file = one business scenario; keep step defs reusable across features.
- Package-by-layer (as above) rather than package-by-feature, unless `{{PROJECT_NAME}}` has enough distinct modules to justify sub-packages inside `pages/`.
- New `BrowserContext` per scenario (not per suite) to keep cookies/storage isolated between tests.

### 7. Delivery order — do not generate everything in one shot
1. Folder skeleton + `pom.xml` (+ confirm Playwright browsers install correctly)
2. Config + `PlaywrightFactory`, with a throwaway smoke test proving a browser launches and navigates to `{{BASE_URL}}`
3. Base classes (`BasePage`, `BaseApiTest`)
4. One sample Page Object + one sample UI feature/step-def pair, end-to-end
5. One sample API feature/step-def pair, end-to-end
6. Runner + `testng.xml` + reporting wiring (including trace-on-failure)
7. Only then scale out additional pages/endpoints

Confirm with me before step 4 if any placeholder above is still unresolved.
