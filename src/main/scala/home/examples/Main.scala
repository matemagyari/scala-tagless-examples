package home.examples

import cats.Id

import scala.concurrent.Future

object Main extends App {

  {
    val service: PersonServiceImpl[Id] = new PersonServiceImpl(new InMemoryPersonRepository)
    service.save(Person(0))
    val p: Option[Person] = service.find(0)
  }

  {
    import cats.instances.future._
    import scala.concurrent.ExecutionContext.Implicits.global
    val service: PersonServiceImpl[Future] =
      new PersonServiceImpl(new AsynchPersonRepository)

    val p: Future[Option[Person]] = service.find(0)
  }

}
