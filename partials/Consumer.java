{% macro consumer(asyncapi, serverName) %}


public interface {{serverName | camelCase | upperFirst}}Consumer {

    {% for channelName, channel in asyncapi.channels() %}
    {% if channel.hasPublish() %}
    {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
         {%- set typeName = channel.publish().message().payload().uid() | camelCase | upperFirst %}
         void {{channel.subscribe().id() | camelCase}}(
           {{typeName}} data
         );
    {% endif %}
    {% endif %}
    {% endfor %}
}

{% endmacro %}