package app.vercel.leonanthomaz.jobexplorer.components;

import app.vercel.leonanthomaz.jobexplorer.model.Job;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class IndeedProcessor extends JobProcessor{
    @Override
    protected void navigateToSearchResults(WebDriver driver, String key) {
        // Navega até a página de resultados de busca específica do Indeed
        driver.get("https://br.indeed.com/jobs?q=%22"+key+"%22&l=Rio+de+Janeiro%2C+RJ&sort=date");
    }
    @Override
    protected List<Job> extractJobData(WebDriver driver) {
        List<Job> jobList = new ArrayList<>();
        log.info("INICIO: {}", jobList);

        // Encontra o elemento ul que contém as vagas de emprego
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ulElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mosaic-provider-jobcards\"]/ul")));


        // Encontra todos os elementos li que representam as vagas de emprego dentro do ul
        List<WebElement> jobElements = ulElement.findElements(By.xpath("./li"));

        // Itera sobre os elementos das vagas de emprego e extrai os dados
        for (WebElement jobElement : jobElements) {
            log.info("ELEMENTO: {}", jobElement);

            // Extrai os dados da vaga
            String title = jobElement.findElement(By.xpath(".//h2")).getText();
            String location = jobElement.findElement(By.xpath(".//div[contains(@class, 'location')]")).getText();
            String timePosted = jobElement.findElement(By.xpath(".//span[contains(@class, 'time')]")).getText();
            String link = jobElement.findElement(By.tagName("a")).getAttribute("href");

            // Cria um objeto Job e adiciona à lista
            Job job = new Job(title, location, timePosted, link);
            jobList.add(job);
        }

        log.info("FIM: {}", jobList);
        return jobList;
    }
    @Override
    protected void filterSearchResults(WebDriver driver){}

}
