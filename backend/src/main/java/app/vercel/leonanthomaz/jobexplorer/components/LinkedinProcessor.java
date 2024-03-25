package app.vercel.leonanthomaz.jobexplorer.components;

import app.vercel.leonanthomaz.jobexplorer.config.EdgeConfig;
import app.vercel.leonanthomaz.jobexplorer.model.Job;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinkedinProcessor {

    public List<Job> searchJobs(String key) {
        List<Job> jobList;
        WebDriver driver = null;

        try {
            // Inicializa o driver do navegador
            driver = initializeDriver();
//            driver.manage().window().minimize();

            // Navega até a página de resultados de busca do LinkedIn
            driver.get("https://www.linkedin.com/jobs/search?keywords=%22" + key + "%22&location=Rio%20de%20Janeiro");

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

    private WebDriver initializeDriver() {
        // Define o caminho para o executável do Microsoft Edge Driver
        String edgedriverPath = getEdgeDriverPath();
        System.setProperty("webdriver.edge.driver", edgedriverPath);

        // Inicializa uma nova instância do WebDriver do Microsoft Edge com as opções configuradas
        EdgeConfig edgeConfig = new EdgeConfig();
        return new EdgeDriver(edgeConfig.optionsEdge());
    }

    private void filterSearchResults(WebDriver driver) {
        WebElement filtrar = waitForElement(driver, By.xpath("//*[@id=\"jserp-filters\"]/ul/li[1]/div/div/button"), Duration.ofSeconds(10));
        filtrar.click();

        WebElement porTempo = waitForElement(driver, By.xpath(" //*[@id=\"jserp-filters\"]/ul/li[1]/div/div/div/div/div/div[1]/label"), Duration.ofSeconds(10));
        porTempo.click();

        WebElement concluido = waitForElement(driver, By.xpath(" //*[@id=\"jserp-filters\"]/ul/li[1]/div/div/div/button"), Duration.ofSeconds(10));
        concluido.click();
    }

    private List<Job> extractJobData(WebDriver driver) {
        List<Job> jobList = new ArrayList<>();

        // Encontra todos os elementos que representam as vagas de emprego
        List<WebElement> jobElements = driver.findElements(By.xpath("//*[@id=\"main-content\"]/section[2]/ul/li"));

        // Itera sobre os elementos das vagas de emprego e extrai os dados
        for (WebElement jobElement : jobElements) {
            // Extrai os dados da vaga
            String title = jobElement.findElement(By.xpath("./a/div[2]/h3")).getText();
            String location = jobElement.findElement(By.xpath("./a/div[2]/div/span")).getText();
            String timePosted = jobElement.findElement(By.xpath("./a/div[2]/div/time")).getText();
            String link = jobElement.findElement(By.xpath("./a")).getAttribute("href");

            // Cria um objeto Job e adiciona à lista
            Job job = new Job(title, location, timePosted, link);
            jobList.add(job);
        }

        return jobList;
    }

    private String getEdgeDriverPath() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path edgedriverPath = currentPath.resolve("C:\\dev\\github\\jobexplorer\\backend\\src\\main\\resources\\msedgedriver.exe");
        return edgedriverPath.toString();
    }

    private WebElement waitForElement(WebDriver driver, By by, Duration duration) {
        WebDriverWait wait = new WebDriverWait(driver, duration);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

}
