{% macro jmsConsumer(asyncapi, server, serverName) %}

@JMSListener("{{- server.bindings().jmsConnectionFactory}}")
public static final class {{serverName | camelCase | upperFirst}}JmsConsumer {

    private final {{serverName | camelCase | upperFirst}}Consumer consumer;

    public {{serverName | camelCase | upperFirst}}JmsConsumer({{serverName | camelCase | upperFirst}}Consumer consumer) {
        this.consumer = consumer;
    }
    {% for channelName, channel in asyncapi.channels() %}{% if channel.hasSubscribe() %}
    {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
         {%- set typeName = channel.subscribe().message().payload().uid() | camelCase | upperFirst %}
         {%- if channel.subscribe().deprecated %}@Deprecated{%- endif %}
         {% if channel.subscribe().binding('jms') and channel.subscribe().binding('jms').destination | isDefined %}
         @Queue(
           value = "{{channel.subscribe().binding('jms').destination}}",
           executors = "{{server | getExecutorService}}"
         )
         {% else %}
         @Queue(
           value = "{{channelName}}",
           executors = "{{server | getExecutorService}}"
         )
         {% endif %}
         void {{channel.subscribe().id() | camelCase}}(
           @MessageBody {{typeName}} data{%- for propName, prop in channel.subscribe().message().headers().properties() %}{%- if prop.type() == 'string' or prop.type() == 'integer' %},
           @MessageHeader("{{propName}}") {% if prop.type() === 'string'%}String{% elif prop.type() === 'integer' %}Integer{% endif %} {{propName | camelCase}}{%- endif %}{%- endfor %}
         ) {
            this.consumer.{{channel.subscribe().id() | camelCase}}(
              data{%- for propName, prop in channel.subscribe().message().headers().properties() %}{%- if prop.type() == 'string' or prop.type() == 'integer' %},
              {{propName | camelCase}}{%- endif %}{%- endfor %}
            );
         }
    {% endif %}{% endif %}{% endfor %}
}
{% endmacro %}