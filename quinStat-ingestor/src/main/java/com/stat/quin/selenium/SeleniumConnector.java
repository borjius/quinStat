package com.stat.quin.selenium;


import com.stat.quin.bean.WebMatchBean;
import com.stat.quin.elastic.dto.TeamDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

@Component
public class SeleniumConnector {

    private static final String HOME_PAGE = "https://www.bdfutbol.com/es/t/t.html#primera";
    private static final String LEAGUE_CLASS = "pestanya_texte";
    private static final String DAYS_SELECTOR = "jornada";
    private static final String MATCHES_ID = "jornada_classi";
    private static final String ID_POSITIONS_TABLE = "classific";
    private static final String CLASS_TEAM_NAME_IN_POSITIONS_TABLE = "text-nowrap";
    private static final String CLASS_POINTS_IN_POSITIONS_TABLE = "font-weight-bold";
    private static final String CLASS_STATS_IN_POSITIONS_TABLE = "resp_off";
    private static final String CLASS_POSITION_IN_POSITIONS_TABLE = "vora-left";
    private WebDriver driver;


    public SeleniumConnector() {
        System.setProperty("webdriver.chrome.driver",
            "~/Downloads/chromedriver");
    }

    public void start() {
        driver = new ChromeDriver();
    }

    public void loadHome() {
        driver.navigate().to(HOME_PAGE);
    }

    public void clickOnLeagueSeason(int league, String season) {
        driver.findElements(By.className(LEAGUE_CLASS)).get(league - 1).findElement(By.linkText(season)).click();
    }

    public List<Integer> getDays() {
        Select selector = new Select(driver.findElement(By.id(DAYS_SELECTOR)));
        int days = selector.getOptions().size();
        return IntStream.range(2, days + 1).boxed().collect(Collectors.toList());
    }

    public void clickOnSeasonDay(Integer day) {
        Select selector = new Select(driver.findElement(By.id(DAYS_SELECTOR)));
        selector.selectByValue(String.valueOf(day));
    }

    public List<String> getTablePositions() {
        final List<WebElement> listElements = getPositionsTable();

        return listElements.stream()
            .filter(element -> element.findElements(By.tagName("th")).size() == 0)
            .map(element -> element.findElement(By.className(CLASS_TEAM_NAME_IN_POSITIONS_TABLE)).getText())
            .collect(Collectors.toList());
    }

    public List<TeamDto> getTeamsResults() {
        final List<WebElement> listElements = getPositionsTable();

        return listElements.stream()
            .filter(element -> element.findElements(By.tagName("th")).size() == 0)
            .map(element -> {
                String teamName = element.findElement(By.className(CLASS_TEAM_NAME_IN_POSITIONS_TABLE)).getText();
                int points = Integer.parseInt(element.findElement(By.className(CLASS_POINTS_IN_POSITIONS_TABLE)).getText());
                int position = Integer.parseInt(element.findElement(By.className(CLASS_POSITION_IN_POSITIONS_TABLE)).getText());
                final List<WebElement> elements = element.findElements(By.className(CLASS_STATS_IN_POSITIONS_TABLE));
                int wins = Integer.parseInt(elements.get(0).getText());
                int withdraw = Integer.parseInt(elements.get(1).getText());
                int lose = Integer.parseInt(elements.get(2).getText());
                int goals = Integer.parseInt(elements.get(3).getText());
                int goalsAgainst = Integer.parseInt(elements.get(4).getText());
                return new TeamDto(teamName, position, points, goals, goalsAgainst, wins, withdraw, lose);
            })
            .collect(Collectors.toList());

    }

    public List<WebMatchBean> getWebResults(int day) {
        return driver.findElement(By.id(MATCHES_ID))
            .findElement(By.className("ij" + day))
            .findElements(By.className("row"))
            .stream()
            .map(element -> {
                List<WebElement> teams = element.findElement(By.className("imgmr2"))
                    .findElements(By.className("partiteq"));

                List<WebElement> goals = element.findElement(By.className("col-auto"))
                    .findElements(By.className("partiteq"));
                return new WebMatchBean(teams.get(0).getText(), teams.get(1).getText(),
                    Integer.parseInt(goals.get(0).getText()), Integer.parseInt(goals.get(1).getText()));
            }).collect(Collectors.toList());
    }

    private List<WebElement> getPositionsTable() {
        return driver.findElement(By.id(ID_POSITIONS_TABLE))
            .findElement(By.tagName("tbody"))
            .findElements(By.tagName("tr"));
    }
}
