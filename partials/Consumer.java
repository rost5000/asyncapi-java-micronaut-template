{% macro consumer(asyncapi, serverName, params) %}


public interface {{serverName | camelCase | upperFirst}}Consumer {

    {% for channelName, channel in asyncapi.channels() %}
    {% if channel.hasSubscribe() %}
    {%- if channel.subscribe().deprecated or  channel.subscribe().deprecated === 'true' %}@Deprecated{%- endif %}
    {% if (serverName in channel.servers()) or (channel.servers() | isArrayDefinedOrEmpty) %}
         {%- set typeName = channel.subscribe().message().payload().uid() | camelCase | upperFirst %}
         {%- if channel.subscribe().deprecated %}@Deprecated{%- endif %}
         void {{channel.subscribe().id() | camelCase}}(
           {{typeName}} data{%- for propName, prop in channel.subscribe().message().headers().properties() %}{%- if prop.type() == 'string' or prop.type() == 'integer' %},
           {% if prop.type() === 'string'%}String{% elif prop.type() === 'integer' %}Integer{% endif %} {{propName | camelCase}}Header{%- endif %}{%- endfor %}
         );
    {% endif %}
    {% endif %}
    {% endfor %}
}

{% endmacro %}