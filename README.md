# Дипломный проект по тестированию API для [reqres.in](https://reqres.in/)
## :scroll: Содержание:

- [Используемый стек](#computer-используемый-стек)
- [Запуск автотестов](#arrow_forward-запуск-автотестов)
- [Сборка в Jenkins](#-сборка-в-jenkins)
- [Пример Allure-отчета](#-пример-allure-отчета)

## :computer: Используемый стек

<p align="center">
<img width="6%" title="IntelliJ IDEA" src="images/logo/Intelij_IDEA.svg">
<img width="6%" title="Java" src="images/logo/Java.svg">
<img width="6%" title="Allure Report" src="images/logo/Allure_Report.svg">
<img width="6%" title="Gradle" src="images/logo/Gradle.svg">
<img width="6%" title="JUnit5" src="images/logo/JUnit5.svg">
<img width="6%" title="GitHub" src="images/logo/GitHub.svg">
<img width="6%" title="Jenkins" src="images/logo/Jenkins.svg">
</p>

Тесты в данном проекте написаны на языке <code>Java</code>, сборщик - <code>Gradle</code>. Так же были использованы фреймворки <code>JUnit 5</code>.
Для удаленного запуска реализована джоба в <code>Jenkins</code> с формированием Allure-отчета.

Содержание Allure-отчета:
* Шаги теста;
* Полный вид отправки запроса;
* Полный вид полученного ответа;

## :arrow_forward: Запуск автотестов

### Запуск тестов из терминала
```
gradle clean api_tests
```

## <img width="4%" style="vertical-align:middle" title="Jenkins" src="images/logo/Jenkins.svg"> Сборка в Jenkins
<p align="center">
<img title="Jenkins Build" src="images/screens/jenkinsBuild.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Allure Report" src="images/logo/Allure_Report.svg"> Пример Allure-отчета
### Overview

<p align="center">
<img title="Allure Overview" src="images/screens/allureReportMain.png">
</p>

<p align="center">
<img title="Allure Overview" src="images/screens/allureReport.png">
</p>
