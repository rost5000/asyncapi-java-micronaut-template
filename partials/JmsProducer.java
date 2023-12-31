{% macro jmsProducer(asyncapi, server, serverName, params) %}

@JMSProducer("{{- server.bindings().jmsConnectionFactory}}")
public interface {{serverName | camelCase | upperFirst}}Producer{
  {% for channelName, channel in asyncapi.channels() %}{% if channel.hasPublish() %}
  {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
       {%- set typeName = channel.publish().message().payload().uid() | camelCase | upperFirst %}
       {%- if (channel.publish().deprecated === true) or (channel.publish().deprecated === 'true') or (channel.deprecated === true) or (channel.deprecated === 'true') %}@Deprecated{%- endif %}
       {% if channel.publish().binding('jms') and channel.publish().binding('jms').destination | isDefined %}
       @Queue(
         value = "{{channel.publish().binding('jms').destination}}",
         executor = "{{server | getExecutorService}}"
       ){% elif channel.binding('jms') and channel.binding('jms').destination | isDefined %}
       @Queue(
         value = "{{channel.binding('jms').destination}}",
         executor = "{{server | getExecutorService}}"
       ){% else %}
       @Queue(
         value = "{{channelName}}",
         executor = "{{server | getExecutorService}}"
       ){% endif %}
       void {{channel.publish().id() | camelCase}}(
         @MessageBody {{typeName}} data{%- for propName, prop in channel.publish().message().headers().properties() %}{%- if prop.type() == 'string' or prop.type() == 'integer' %},
         @MessageHeader("{{propName}}") {% if prop.type() === 'string'%}String{% elif prop.type() === 'integer' %}Integer{% endif %} {{propName | camelCase}}{%- endif %}{%- endfor %}
       );
  {% endif %}{% endif %}
  {% endfor %}
}
{% endmacro %}