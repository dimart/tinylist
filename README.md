# TinyList
Create and share your lists.
You can list movies from [TMDB](https://www.themoviedb.org), songs from [Spotify](https://www.spotify.com) and regular text.

![Demo](/misc/demo.gif)

The goal of this project was to learn:
* [scala.js](https://www.scala-js.org/)
* [React](https://facebook.github.io/react/)
* [akka-http](https://github.com/akka/akka-http)
* [Autowire](https://github.com/lihaoyi/autowire)
* [sbt](http://www.scala-sbt.org/)
* [Bootstrap](https://getbootstrap.com/)

## Build

```bash
sbt server/run
```

if you want TMDB support:
```bash
sbt -DTMDBApiKey=DEFINE_IT_HERE server/run
```