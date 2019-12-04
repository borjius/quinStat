## QUIN STATS

This projects tries to predict the results of quiniela matches, based on previous statistics. 

### Extract Info

An endpoint will retrieve the league to be processed and the seasons to be extracted. With a public website, selenium will extract the info from it.

### Repositories

There are two options, inject in elastic search or in H2 disk storage.

#### H2

using reactive driver, we can store the info and also extract it, by teams in the match or by range position in the league at the moment.

#### elastic

Executed in docker compose with kibana, the info is stored also using reactive repository. Then, the data is there so any query of dashboard can be done. 

### machine learning

experimenting with it. WEKA libraries trying to analyse data. No much conclusions on it...

### Predict

QuinCotroller has same endpoints for prediction, by h2 info stored or machine learning. Giving a match, it will calculate percentages for the results.


## TECH STACK

- java 8
- spring-boot 2
- web flux
- reactive repos h2 & elastic
- weka
- selenium


*** testing strategy eliminated so this is just experimental. Elastic search embedded for testing not implemented.