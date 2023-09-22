{% macro generateImportsForProducers(asyncapi, params) %}

import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

{% for channelName, channel in asyncapi.channels() %}
    {%- if channel.hasPublish() %}
        {%- for message in channel.publish().messages() %}
import {{ params['userJavaPackage'] }}.models.{{message.payload().uid() | camelCase | upperFirst}};
        {%- endfor %}
    {%- endif %}
{%- endfor %}

{% endmacro %}