{% macro jmsProducer(asyncapi, server, serverName) %}

@JMSProducer("{{- server | getConnectionFactory }}")
public interface {{serverName | camelCase | upperFirst}}Producer{
  {% for channelName, channel in asyncapi.channels() %}
  {% if channel.hasSubscribe() %}
  {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
       {%- set typeName = channel.subscribe().message().payload().uid() | camelCase | upperFirst %}
       {% if channel.subscribe().binding('jms') and channel.subscribe().binding('jms').destination | isDefined %}
       @Queue("{{channel.subscribe().binding('jms').destination}}")
       {% else %}
       @Queue("{{channelName}}")
       {% endif %}
       void {{channel.subscribe().id() | camelCase}}(
         @MessageBody {{typeName}} data
       );
  {% endif %}
  {% endif %}
  {% endfor %}
}

{% endmacro %}