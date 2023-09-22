# Java Micronaut Generator
Use your AsyncAPI definition to generate java (code based on Micronaut framework) to subscribe and publish messages.

This project is based on [java-spring-template](https://github.com/asyncapi/java-spring-template.git)
## Micronaut Documentation

- [User Guide](https://docs.micronaut.io/4.1.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.1.0/api/index.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

Additional features  in the project:
- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)
- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)
- [Micronaut JMS documentation](https://micronaut-projects.github.io/micronaut-jms/snapshot/guide/index.html)

## Usage
### AsyncAPI definitions
To have correctly generated code, your AsyncAPI file MUST define `operationId` for every operation and `jmsConnectionFactory` should be set.

In order for the generator to know what names to use for some parameters it's necessary to make use of AsyncAPI specification (JMS bindings)[https://github.com/asyncapi/bindings/tree/master/jms].

Complete example of JMS usage [here](./tests/jms.yaml)
### From the command-line interface (CLI)

```bash
  Usage: ag [options] <asyncapi> https://github.com/rost5000/asyncapi-java-micronaut-template.git

  Options:

    -V, --version                 output the version number
    -o, --output <outputDir>       directory where to put the generated files (defaults to current directory)
    -p, --param <name=value>       additional param to pass to templates
    -h, --help                    output usage information
```
Example command: 
```bash
 asyncapi generate fromTemplate jms.yaml https://github.com/rost5000/asyncapi-java-micronaut-template.git
```

| Name                 | Description                                                                                                          | Required | Default      |
|----------------------|----------------------------------------------------------------------------------------------------------------------|----------|--------------|
| defaultImplementaion | Generate implementation of the subscriber interfaces by default.                                                     | false    | true         |
| javaPackage          | The Java package of the generated classes. Alternatively you can set the specification extension info.x-java-package | false    | com.asyncapi |
| generateConsumers    | Tell generator to generate consumers                                                                                 | false    | true         |
| generateProducers    | Tell generator to generate producers                                                                                 | false    | true         |
## Micronaut specific properties
You can specify `serviceExecutor` property for generating service executor for queue listener. For example:
```yaml
servers:
  production:
# ...
    bindings:
# ...
      properties:
        - name: serviceExecutor
          value: serviceExecutor
```
It is needed sometimes for micronaut [Queue](https://micronaut-projects.github.io/micronaut-jms/1.0.x/api/io/micronaut/jms/annotations/Queue.html#executor--) annotation.
## Additional links
- [AsyncAPI](https://www.asyncapi.com/docs)
- How to generate code from [AsyncAPI CLI](https://www.asyncapi.com/docs/tools/cli/usage#asyncapi-generate)
- JMS specification [bindings](https://github.com/asyncapi/bindings/tree/master/jms)