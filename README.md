# TinyList
Create and share your lists

![Demo](/misc/demo.gif)

The goal of this project was to learn:
* [scala.js](https://www.scala-js.org/)
* [React](https://facebook.github.io/react/)
* [akka-http](https://github.com/akka/akka-http)
* [Autowire](https://github.com/lihaoyi/autowire)
* [Bootstrap](https://getbootstrap.com/)

## Build

```bash
sbt server/run
```

if you want TMDB support:
```bash
sbt -DTMDBApiKey=DEFINE_IT_HERE server/run
```