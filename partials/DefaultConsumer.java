{% macro defaultConsumer(asyncapi, serverName) %}

@jakarta.inject.Singleton
public static final class ImplOf{{serverName | camelCase | upperFirst}}Consumer implements {{serverName | camelCase | upperFirst}}Consumer{
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JmsConsumers.class.getName());

    {% for channelName, channel in asyncapi.channels() %}
    {% if channel.hasPublish() %}
    {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
         {%- set typeName = channel.publish().message().payload().uid() | camelCase | upperFirst %}
         @Override
         public void {{channel.subscribe().id() | camelCase}}(
           {{typeName}} data
         ) {
         logger.warning("The method {{channel.subscribe().id() | camelCase}} is not implemented");
         }
    {% endif %}
    {% endif %}
    {% endfor %}
}

{% endmacro %}