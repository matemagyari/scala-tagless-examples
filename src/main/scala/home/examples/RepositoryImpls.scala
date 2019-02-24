package home.examples

import cats.Id

import scala.concurrent.{ExecutionContext, Future}

class InMemoryPersonRepository extends PersonRepository[Id] {

  private var persons: Map[Int, Person] = Map.empty

  override def find(id: Int): Option[Person] = persons.get(id)

  override def save(person: Person): Unit = {
    persons = persons + (person.id → person)
  }
}

class AsynchPersonRepository(implicit ec: ExecutionContext) extends PersonRepository[Future] {

  private var persons: Map[Int, Person] = Map.empty

  override def find(id: Int): Future[Option[Person]] = Future.successful(persons.get(id))

  override def save(person: Person): Future[Unit] = Future {
    persons = persons + (person.id → person)
  }
}
