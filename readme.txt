Welcome to expojo!
------------------

expojo is Step Ahead Software's open source lightweight, highly focussed, dependency injecting "Exposed Domain Model" pattern framework for POJOs (Plain Old Java Objects).

The exposed domain model pattern is highly productive and is one of the most transparent methods of implementing persistence for your POJOs/domain model objects. You can read about it in Chris Richardson's excellent landscape changing book called "POJOs in Action".

This framework makes it easy for you to manage the persistence of your POJO based domain model in a persistence technology independent way.

It is ideally suited to the "JDO Open PersistenceManager in View" and "Hibernate Open Session in View" architecture for servlet/J2EE based applications.

See http://expojo.com for more details on how to compile the system and integrate it into your projects.

Licensing
---------

The expojo framework is released under LGPL, BSD or Apache 2.0 license according to your preference so it can be used in commercial applications without requiring you to make the source code of your application available.

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