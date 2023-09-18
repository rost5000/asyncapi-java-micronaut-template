{% macro defaultConsumer(asyncapi, serverName) %}

@jakarta.inject.Singleton
public static final class ImplOf{{serverName | camelCase | upperFirst}}Consumer implements {{serverName | camelCase | upperFirst}}Consumer{
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JmsConsumers.class.getName());

    {% for channelName, channel in asyncapi.channels() %}
    {% if channel.hasSubscribe() %}
    {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
         {%- set typeName = channel.subscribe().message().payload().uid() | camelCase | upperFirst %}
         @Override
         {%- if channel.subscribe().deprecated %}@Deprecated{%- endif %}
         public void {{channel.subscribe().id() | camelCase}}(
           {{typeName}} data{%- for propName, prop in channel.subscribe().message().headers().properties() %}{%- if prop.type() == 'string' or prop.type() == 'integer' %},
           {% if prop.type() === 'string'%}String{% elif prop.type() === 'integer' %}Integer{% endif %} {{propName | camelCase}}Header{%- endif %}{%- endfor %}
         ) {
         logger.warning("The method {{channel.subscribe().id() | camelCase}} is not implemented");
         }
    {% endif %}
    {% endif %}
    {% endfor %}
}

{% endmacro %}