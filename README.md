**How to run:**

Required Java 21 

mvn test

---

Hello dear reviewer from Societe Generale

I would like to give a brief explanation of my decisions that shaped this implementation in a way it is.

First of all I'm not at all dogmatic about my approach. This is just one of ways I implemented systems and so far I personally like this one the most. I'm always open to ideas and criticism.

Key thing to point out is that I used this for small to medium microservices (10-100k loc) or modules that I knew off the bat would not grow to huge sizes.

For insanely big bounded context it's probably better to try something else.

I fully understand there are multiple ways of expressing same solution and each has its own advantages.

---

Main idea:
-
Although it was not stated in tasks requirements, I did implement it using Hexagonal because I expect you would like to see how I understand it based on how often I was asked about it during initial interview :)

Main advantage of hexagonal for me is separating I/O, queues, api requests and all that jazz into separate packages ensuring that domain is not directly dependent on any of them but rather loosly coupled.
Benefit of that is of course the flexibility and potential interchangeability of tech that's used. 
This is achieved by hiding output/driven/outgoing adapters under interface(port) implementations and exposing business logic to input/driving/incoming adapters via API in form of a facade or an interface(port).

As for what's going on in the core/domain/application part, I personally believe is a different problem not strictly related to whole Hexagonal concept.

One thing for sure is that it should not be littered with framework except for a sprinkle of IoC.

---

**Key things to point out:**

I think the first controversial bit that not everybody would agree with at first glance is that there's no split between DDD's application/domain layer.

Everything "domain" related is tossed into a single package. By that I mean aggregates, domain services, app services, use cases and more.

Reason for that is that I very much enjoy having as little public classes as possible in my projects.

Having a single class being public in "domain" - AccountFacade - makes huge sense for me from TDD POV. My assumption is:

If I have a single point of entry to my module(package) I know exactly where I should start writing my tests whenever I add a new feature. 

In theory, I could cover all the classes in the package with just a single test class. 

Domain tests will remain as close to established requirements in verbiage because it's so much easier to focus on testing behavior instead of implementation if you unit test from package level.

In other words, a PM should be able to read those tests and fully understand what the system does despite him not having tech skills (that's of course a perfect world scenario)

In order to achieve that, I need to stray away a bit from the classic DDD approach because:

Usually domain and application layer are split into separate packages with domain being home of aggregates and domain services and application is use cases + orchestration.

That forces domain aggregates to be public and I do not like that. Also, it requires more test classes and more mocking.

More public members = more tests because in my view a public member of any package cannot be untested directly. Perhaps this is controversial take too.

Any meaningful refactors are more painful this way. More test classes - more annoying refactors.


---
**Advantages:**

Reduced test complexity

easier adherence to BDD

IntelliJ suggests you classes that you actually may use

TDD is cakewalk

stress-free refactors

clear entry point to my system

---

**As for the cons:**

There's an argument to be made that my domain is dependent on "transport shapes" i.e. DTOs hence not exactly adhering to DDD philosophy

I do not return an aggregate from repository but rather a DTO which is mapped in adapters, and then in domain DTO is mapped to an aggregate.

Mapper hell is one of drawbacks here for sure.

Eventually the package will grow and Java does not offer an elegant way to ensure that package A.B is only visible by package A. Personally having dozens of classes in one package is not a problem but I can understand why some see it this way.

---
To be fully transparent these are not completely original ideas (but they are battle tested) as I base everything hexagonal on these three sources:

https://www.youtube.com/watch?v=ILBX9fa9aJo

https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/

https://blog.allegro.tech/2020/05/hexagonal-architecture-by-example.html

Also also as I understand, you guys use Kotlin which has a bit of a different approach to package scope so my whole sales pitch here may very well go to hell :D

---

**Smaller decisions and clarifications:**

Adapters are public because I'm not using IoC and I want to simulate integration test (AccountAcceptanceTest). If this was a spring project all the adapter classes would be package private.

If real persistence was implemented, entities would not be records but classes. Adding entities for this task is probably an overkill anyways but I wanted to highlight that persistence model should live in adapters.

Since there's no I/O requirement for this task I did not include input/driving/incoming ports. If I did I'd probably have 3 different ports for each use case and all of them would be implemented by a facade.

Facade coordinates services and services coordinate aggregates, domain services and driven ports. I did not include any domain services because logic is simple enough to be living in aggregates only.

--- 

After writing this README it really feels like a sales pitch but please understand that the main point is to try and convince you that there's some thought behind what I've written despite it looking perhaps not conventional.










