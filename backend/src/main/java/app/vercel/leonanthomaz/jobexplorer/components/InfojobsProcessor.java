package app.vercel.leonanthomaz.jobexplorer.components;

import app.vercel.leonanthomaz.jobexplorer.model.Job;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class InfojobsProcessor extends JobProcessor{
    @Override
    protected void navigateToSearchResults(WebDriver driver, String key) {
        // Navega até a página de resultados de busca do InfoJobs
        driver.get("https://www.infojobs.com.br/vagas-de-emprego-%22" + key + "%22-em-rio-janeiro,-rj.aspx?Antiguedad=1");
    }
    @Override
    protected List<Job> extractJobData(WebDriver driver) {
        List<Job> jobList = new ArrayList<>();

        // Encontra todos os elementos que representam as vagas de emprego
        List<WebElement> jobElements = driver.findElements(By.xpath("//div[contains(@id, 'vacancy')]"));

        // Itera sobre os elementos das vagas de emprego e extrai os dados
        for (WebElement jobElement : jobElements) {
            try {
                // Extrai os dados da vaga
                String title = jobElement.findElement(By.xpath(".//a")).getText();
                String link = jobElement.findElement(By.xpath(".//a")).getAttribute("href");

                // Cria um objeto Job e adiciona à lista
                Job job = new Job(title, "", "", link);
                jobList.add(job);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jobList;
    }
    @Override
    protected void filterSearchResults(WebDriver driver){}
}
