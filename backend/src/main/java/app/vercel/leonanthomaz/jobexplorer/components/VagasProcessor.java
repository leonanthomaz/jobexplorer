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
public class VagasProcessor extends JobProcessor{
    @Override
    protected void navigateToSearchResults(WebDriver driver, String key) {
        // Navega até a página de resultados de busca específica do Vagas.com.br
        driver.get("https://www.vagas.com.br/vagas-de-" + key + "-em-rio-de-janeiro?ordenar_por=mais_recentes");
    }
    @Override
    protected List<Job> extractJobData(WebDriver driver) {
        List<Job> jobList = new ArrayList<>();
        log.info("INICIO: {}", jobList);

        // Encontra o elemento ul que contém as vagas de emprego
        WebElement ulElement = driver.findElement(By.xpath("//*[@id='todasVagas']/ul"));

        // Encontra todos os elementos li que representam as vagas de emprego dentro do ul
        List<WebElement> jobElements = ulElement.findElements(By.xpath(".//li"));

        // Itera sobre os elementos das vagas de emprego e extrai os dados
        for (WebElement jobElement : jobElements) {
            log.info("ELEMENTO: {}", jobElement);

            // Extrai os dados da vaga
            String title = "";
            WebElement titleElement = jobElement.findElement(By.xpath(".//header/div[2]/h2"));
            if (titleElement != null) {
                title = titleElement.getText().trim();
            }

            String location = "";
            WebElement locationElement = jobElement.findElement(By.xpath(".//footer/span[1]/i"));
            if (locationElement != null) {
                location = locationElement.getText().trim();
            }

            String timePosted = "";
            WebElement timePostedElement = jobElement.findElement(By.xpath(".//footer/span[2]/i"));
            if (timePostedElement != null) {
                timePosted = timePostedElement.getText().trim();
            }

            String link = "";
            WebElement linkElement = jobElement.findElement(By.xpath(".//h2[@class='cargo']/a"));
            if (linkElement != null) {
                link = linkElement.getAttribute("href").trim();
            }

            Job job = new Job(title, location, timePosted, link);
            jobList.add(job);


        }
        log.info("FIM: {}", jobList);
        return jobList;
    }
    @Override
    protected void filterSearchResults(WebDriver driver) {
        // Implementação do filtro de resultados, se necessário
    }
}
