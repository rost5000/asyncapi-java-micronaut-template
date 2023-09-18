package {{ params['userJavaPackage'] }}.api.producers;

{%- from "partials/JmsProducer.java" import jmsProducer -%}


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

public final class JmsProducers {
  {%- for serverName, server in asyncapi.servers() -%}
  {%- if server.protocol() == 'jms' -%}
  static {{-  jmsProducer(asyncapi, server, serverName)  -}}
  {%- endif -%}
  {%- endfor -%}
}