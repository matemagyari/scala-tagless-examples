package home.examples

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future

class ServiceTest extends FlatSpec with Matchers with ScalaFutures {

  "Asynch" should "work" in {
    import cats.instances.future._
    import scala.concurrent.ExecutionContext.Implicits.global
    val service: PersonServiceImpl[Future] =
      new PersonServiceImpl(new AsynchPersonRepository)

    val person = Person(0)

    service.save(person).futureValue shouldBe Right()

    service.find(0).futureValue shouldBe Some(person)

  }

  "Synch" should "work" in {
    import cats.Id
    val service: PersonServiceImpl[Id] = new PersonServiceImpl(new InMemoryPersonRepository)
    val person = Person(0)

    service.save(person) shouldBe Right()

    service.find(0) shouldBe Some(person)
  }
}
