Welcome to expojo
------------------

Applications built on ExPojo are built in a highly productive way and have excellent runtime performance because it eliminates the need for the most common memory hogging and CPU burning operations which normally occur with the inclusion of frameworks like Spring with its many reflection based, memory and stack abusing mechanisms required to make "the complicated obsfuscated magic" happen.

Unleash the true power of Java by making your apps Spring free!
-------------------

Traditional Java applications written in Spring are *almost* the defacto standard and so many developers just blindly follow the crowd and do things this way because "that's how we've always done it".

In the modern world innovation and the ability to allow yourself to think "outside the box" and dare to make a change for the better is what sets apart modern, agile companies from their more traditional peers.

ExPojo lets you claim back your right, as a Java developer, to write "awesome, lightning fast apps!" that are not crippled by the usual interminable behind the scenes reflection and other CPU hogging "obsfuscated magic" required to perform dependency injection.

Why ExPojo?
-----------

Have you ever been debugging in your favorite IDE and single stepped through code in an app built on Spring or any other reflection/annotation oriented framework? You soon realise that your app, when seemingly making a simple call from one of your classes to another usually ends up navigating 37 levels (ok, it varies but usually surprisingly high for what should be a single, direct call)  of stack frames before your calling class reaches the actual method that it calls.

In the days before Spring that call ended up being a direct call like it should be - and virtually instant (sub microsecond) - like it should be.

Spring and other IoC frameworks provide us with, among other things, dependency injection. Unfortunately it did it, first via ugly, cryptic XML and then via annotations but both can still suffer from the performance zapping issues discussed above.

Dependency injection is actually a specialized form of Dependency provisioning i.e. provide me with access to something that I need without me explicitly passing it in as a parameter to my method or somehow making it available as an attribute of my current class instance.

Like all things in life there often alternative approaches and ExPojo makes a high performance, simple to use, reflection and annotation free mechanism for dependency provisiong a reality - allowing Java developers to once again achieve the lightning fast performance people used to expect from Java applications.

# How does it work?

ExPojo is extremely simple to integrate into your app. No annotations are required for every possible dependency that you need provisiosed because it doesn't need to provision them using class dependency injection. You don't even need to create the crowd of annotated dependencies as attributes at the top of each of your classes - all that ugliness and obsfuscated magic dissappears. It's all implemented in straightforward, pure Java and uses tried and proven Java and Object Oriented constructs.

The way ExPojo performs its dependency provisioning is via a clever filter that you configure in your web app context, once!

For every HTTP request the filter sets up an ExpojoContext object and binds it to the thread that is serving the HTTP request.

The ExpojoContext is then available to any of your code that is executed by that thread - without having to previously declare any annotated components as attributes of each class that might want to use it ... any code, anywhere!

After the HTTP request has been serviced by your app execution returns to the ExpojoFilter filter which can perform any 'post request' processing (e.g. close transactions etc.,) and then detaches the ExpojoContext from the thread.

In the context of Object Relational Mappers (ORMs) technologies like JPA/JDO/Hibernate this is commonly referred to "Open Session in View" or "Open Persistence Manager In View" but, while the ExpojoContext provides access to ORM dependencies (to perist objects, commit transactions etc.,) it can also provide instant access to services and repository classes that you have created.

The ExpojoContext is the key to avoiding declaring dependencies as annotated attributes in each class where you might need to access a dependency. All of your dependencies are now available from this single ExpojoContext instance - instantly and without any runtime reflection and without jumping through 37 stack frames each time you access one component/class from another.

ExPojo also avoids another common issue with reflection based AOP mechanism as used in Spring and many other frameworks: reflection based AOP is implemented via proxies hobbled together at runtime and so silently fail when you call one method from another method inside the same class because that does not go via the proxy and hence the intended magic does not occur (silently!). You might have experienced this if you ever called a @Transactional method in a class from a non transactional method in that same class - that method will get called but the necessary magic to make the transactional method "transactional" is bypassed which is ... unintituitive and downright dangerous!

You can see similar "wrinkles in the reflection based AOP duct tape" when you do things like mark a transactional method as synchronized ... a seemingly legitimate construct but one that was the cause of duplicating a $3 million dollar direct debit transaction in one case that I heard of. The customer was not amused!

# Persistence Technologies
ExPojo makes it easy for you to manage the persistence of your POJO based domain model in a persistence technology independent way.

It is ideally suited to the "JDO Open PersistenceManager in View" and "Hibernate Open Session in View" architecture for servlet/J2EE based applications.

See http://expojo.com for more details on how to compile the system and integrate it into your projects.

Licensing
---------

The expojo framework is released under Apache 2.0 license according to your preference so it can be used in commercial applications without requiring you to make the source code of your application available.

expojo Documentation
--------------------

Javelin Design/Class Diagram:

The *.vcm files in the src directory are Javelin UML class diagram/design files. These design files act as the documentation for the project. To open the design and view and/or edit the class diagram simply download the Javelin Modeler (freeware) or Javelin Modeler+Coder (free 15 day trial) from:

	http://stepaheadsoftware.com/products/javelin/javelin.htm

Javelin is a lightweight, unobtrusive visual object development enviroment for Java that drives development and management of a Java project via a collection of "live" class diagrams. JDO and Hibernate meta data is also managed in zero time to facilitate persistence of your object model with very little effort.

Javelin is a Windows application that is also compatible with Wine on Linux so it can run on both Windows and Linux.

Features
--------

expojo takes advantage of the emerging commonality between the new range of transparent persistence technologies, especially JDO and Hibernate and even a Mock system (allows you to develop and test your model and its business logic much more efficiently by writing objects to memory or a serialized stream instead of a database). The architecture is flexible and generic enough to support other persistence technologies in the future. It also contains a ServletFilter that can be easily hooked into any web application framework to perform dependency injection.
