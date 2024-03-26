package app.vercel.leonanthomaz.jobexplorer.components;

import app.vercel.leonanthomaz.jobexplorer.config.EdgeConfig;
import app.vercel.leonanthomaz.jobexplorer.model.Job;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public abstract class JobProcessor {
    protected abstract void navigateToSearchResults(WebDriver driver, String key);
    private String getEdgeDriverPath() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path edgedriverPath = currentPath.resolve("C:\\dev\\github\\jobexplorer\\backend\\src\\main\\resources\\msedgedriver.exe");
        return edgedriverPath.toString();
    }
    private WebDriver initializeDriver() {
        // Define o caminho para o executável do Microsoft Edge Driver
        String edgedriverPath = getEdgeDriverPath();
        System.setProperty("webdriver.edge.driver", edgedriverPath);

        // Inicializa uma nova instância do WebDriver do Microsoft Edge com as opções configuradas
        EdgeConfig edgeConfig = new EdgeConfig();
        return new EdgeDriver(edgeConfig.optionsEdge());
    }
    public List<Job> searchJobs(String key) {
        List<Job> jobList = new ArrayList<>();
        WebDriver driver = null;

        try {
            // Inicializa o driver do navegador
            driver = initializeDriver();
            driver.manage().window().minimize();

            // Navega até a página de resultados de busca específica
            navigateToSearchResults(driver, key);

            // Filtra os resultados de busca
            filterSearchResults(driver);

            // Extrai os dados das vagas de emprego
            jobList = extractJobData(driver);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao processar requisição.", e);
        } finally {
            // Fecha o navegador após a conclusão
            if (driver != null) {
                driver.quit();
            }
        }

        return jobList;
    }
    protected abstract List<Job> extractJobData(WebDriver driver);
    protected abstract void filterSearchResults(WebDriver driver);
    protected WebElement waitForElement(WebDriver driver, By by, Duration duration) {
        WebDriverWait wait = new WebDriverWait(driver, duration);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
