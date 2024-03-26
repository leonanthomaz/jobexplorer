package app.vercel.leonanthomaz.jobexplorer.components;

import app.vercel.leonanthomaz.jobexplorer.model.Job;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinkedinProcessor extends JobProcessor{
    @Override
    protected void navigateToSearchResults(WebDriver driver, String key) {
        // Navega até a página de resultados de busca do LinkedIn
        driver.get("https://www.linkedin.com/jobs/search?keywords=%22" + key + "%22&location=Rio%20de%20Janeiro");
    }
    @Override
    protected List<Job> extractJobData(WebDriver driver) {
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
    @Override
    protected void filterSearchResults(WebDriver driver) {
        WebElement filtrar = waitForElement(driver, By.xpath("//*[@id=\"jserp-filters\"]/ul/li[1]/div/div/button"), Duration.ofSeconds(0));
        filtrar.click();

        WebElement porTempo = waitForElement(driver, By.xpath(" //*[@id=\"jserp-filters\"]/ul/li[1]/div/div/div/div/div/div[1]/label"), Duration.ofSeconds(0));
        porTempo.click();

        WebElement concluido = waitForElement(driver, By.xpath(" //*[@id=\"jserp-filters\"]/ul/li[1]/div/div/div/button"), Duration.ofSeconds(0));
        concluido.click();
    }
}
