package app.vercel.leonanthomaz.jobexplorer.components;

import app.vercel.leonanthomaz.jobexplorer.config.EdgeConfig;
import app.vercel.leonanthomaz.jobexplorer.model.Job;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
@Log4j2
public class InfojobsProcessor {
    public List<Job> searchJobs(String key) {
        List<Job> jobList = new ArrayList<>();
        WebDriver driver = null;

        try {
            // Inicializa o driver do navegador
            driver = initializeDriver();

            // Navega até a página de resultados de busca do InfoJobs
            driver.get("https://www.infojobs.com.br/vagas-de-emprego-%22" + key + "%22-em-rio-janeiro,-rj.aspx?Antiguedad=1");

            // Extrai as informações das vagas de emprego na página atual
            jobList.addAll(extractJobInfo(driver));

            // Verifica se há mais páginas e continua para a próxima, se houver
            while (hasNextPage(driver)) {
                // Encontra e clica no botão para ir para a próxima página
                WebElement nextButton = driver.findElement(By.xpath("//a[@class='next']"));
                nextButton.click();

                // Aguarda até que as vagas da próxima página sejam carregadas
                waitForElement(driver, By.xpath("//div[contains(@id, 'vaga')]"), Duration.ofSeconds(10));

                // Extrai as informações das vagas de emprego na página atual
                jobList.addAll(extractJobInfo(driver));
            }

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

    private List<Job> extractJobInfo(WebDriver driver) {
        List<Job> jobs = new ArrayList<>();

        List<WebElement> vagas = driver.findElements(By.xpath("//div[contains(@id, 'vacancy')]"));
        for (WebElement vaga : vagas) {
            try {
                // Extrai os dados da vaga
                String title = vaga.findElement(By.xpath(".//a")).getText();
                String location = "";
//                        vaga.findElement(By.xpath(".//div[contains(@id, 'vacancy')]/div[1]/div[1]/div[3]/div[1]/text()")).getText();
                String timePosted = "";
//                        vaga.findElement(By.xpath(".//div[contains(@class, 'js_date')]")).getText();
                String link = vaga.findElement(By.xpath(".//a")).getAttribute("href");

                // Cria um objeto Job e adiciona à lista
                Job job = new Job(title, location, timePosted, link);
                jobs.add(job);

                // Log dos dados da vaga adicionada
                log.info("Vaga encontrada: {}", job);

            } catch (NoSuchElementException e) {
                // Elemento não encontrado, pode continuar a execução
                e.printStackTrace();
            }
        }
        return jobs;
    }
    private boolean hasNextPage(WebDriver driver) {
        try {
            WebElement nextButton = driver.findElement(By.xpath("//a[@class='next']"));
            return nextButton.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebDriver initializeDriver() {
        // Define o caminho para o executável do Microsoft Edge Driver
        String edgedriverPath = getEdgeDriverPath();
        System.setProperty("webdriver.edge.driver", edgedriverPath);

        // Inicializa uma nova instância do WebDriver do Microsoft Edge com as opções configuradas
        EdgeConfig edgeConfig = new EdgeConfig();
        return new EdgeDriver(edgeConfig.optionsEdge());
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
