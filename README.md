
# Серсис агрегации информации с разлчных интернет магазинов (Демо)




## Стэк технологий

**Серверная часть:** Kotlin, Spring Framework, Vaadin Framework, PostgreSQL, GraphQL


## Приложение

Основной сервис платформы агрегации данных для сбора информации с
раличных интернет-магазинов.
Для инициализации после сборки или разворётки контейнера необходимо добавить информацию
о сборщике информации с интернет-магазина, категорию платформы, а также внешние категории,
по которым будет производиться сборка информации.

#### Пример сбощика информации
https://github.com/nnicrow/aggregation-parser


## API Reference

#### GraphQL запросы

```http
  POST /graphql
```

#### Просмотр GraphQL Playground

```http
  /graphql
```