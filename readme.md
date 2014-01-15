# larmic-cdi-viewscope

By default CDI does not have something like the JSF view scope. This project adds a view scope to your JSF application.
Additional to that you can use it in unit test using weld-se (instead of weld-ee).

NOTE: JSF 2.2 brings it own view scope extension. If you already have CDI and JSF 2.2 running in your project, this
should work out of the box by using `@javax.faces.view.ViewScoped`.

[Apache MyFaces CODI](http://myfaces.apache.org/extensions/cdi/index.html) brings it own view access scope and is worth a look.

## Install

larmic-cdi-viewscope is accessible by maven central repository. Add following dependency to use it

```xml
<dpendency>
    <groupId>de.larmic.extension.cdi</groupId>
    <artifactId>larmic-cdi-viewscope</artifactId>
    <version>1.0</version>
</dependency>
```

## Getting started

Create (or update) `javax.enterprise.inject.spi.Extension` file in the `$project/src/main/resources/META-INF/services/` and add

    de.larmic.cdi.context.ViewContextExtension

CDI auto detects new scopes so de.larmic.cdi.context.ViewScope` will be available. The following listing shows a CDI bean in the view scope.
```java
@javax.inject.Named
@de.larmic.cdi.context.ViewScope
public class MyViewScopeBean {
    ...
}
```

## Use it in JavaSE unit tests

For me I like writing lightweight unit tests, so I look for a possibility to enable view scope to JavaSE. Out of the box CDI default scopes will be supported.

By adding `org.jboss.weld.manager.WeldServletScopesSupportForSe` to your test `javax.enterprise.inject.spi.Extension` file the view scope will be usable.

Look at `org.manager.WeldServletScopesSupportForSeTest` to see how it could be used.