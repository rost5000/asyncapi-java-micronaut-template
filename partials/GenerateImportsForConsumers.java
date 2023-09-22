{% macro generateImportsForConsumers(asyncapi, params) %}

import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

{% for channelName, channel in asyncapi.channels() %}
    {%- if channel.hasSubscribe() %}
        {%- for message in channel.subscribe().messages() %}
import {{ params['userJavaPackage'] }}.models.{{message.payload().uid() | camelCase | upperFirst}};
        {%- endfor %}
    {%- endif %}
{%- endfor %}

{% endmacro %}