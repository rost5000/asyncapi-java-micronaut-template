{% macro jmsConsumer(asyncapi, server, serverName) %}

@JMSListener("{{- server | getConnectionFactory }}")
public static final class {{serverName | camelCase | upperFirst}}JmsConsumer {

    private final {{serverName | camelCase | upperFirst}}Consumer consumer;

    public {{serverName | camelCase | upperFirst}}JmsConsumer({{serverName | camelCase | upperFirst}}Consumer consumer) {
        this.consumer = consumer;
    }

    {% for channelName, channel in asyncapi.channels() %}
    {% if channel.hasPublish() %}
    {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
         {%- set typeName = channel.publish().message().payload().uid() | camelCase | upperFirst %}
         {% if channel.publish().binding('jms') and channel.publish().binding('jms').destination | isDefined %}
         @Queue("{{channel.publish().binding('jms').destination}}")
         {% else %}
         @Queue("{{channelName}}")
         {% endif %}
         void {{channel.subscribe().id() | camelCase}}(
           @MessageBody {{typeName}} data
         ) {
            this.consumer.{{channel.subscribe().id() | camelCase}}(data);
         }
    {% endif %}
    {% endif %}
    {% endfor %}
}

{% endmacro %}