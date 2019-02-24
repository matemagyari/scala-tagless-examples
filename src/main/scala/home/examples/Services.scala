package home.examples

import cats.Monad
import cats.syntax.all._

case class Person(id: Int)

trait PersonRepository[F[_]] {
  def find(id: Int): F[Option[Person]]
  def save(person: Person): F[Unit]
}

sealed trait PersonError
case class PersonAlreadyExists(id: Int) extends PersonError
case class PersonDoesNotExist(id: Int) extends PersonError

class PersonServiceImpl[F[_]: Monad](repository: PersonRepository[F]) //extends PersonService[F]
{
  def find(id: Int): F[Option[Person]] = repository.find(id)
  def save(person: Person): F[Either[PersonError, Unit]] = find(person.id).flatMap {
    case None =>
      repository.save(person).map(_.asRight)
    case Some(existingPerson) =>
      Monad[F].pure(PersonAlreadyExists(existingPerson.id).asLeft)
  }

}
