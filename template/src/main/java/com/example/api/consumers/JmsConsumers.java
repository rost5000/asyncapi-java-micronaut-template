{%- from "partials/JmsConsumer.java" import jmsConsumer -%}
{%- from "partials/Consumer.java" import consumer -%}
{%- from "partials/DefaultConsumer.java" import defaultConsumer -%}
package {{ params['userJavaPackage'] }}.api.consumers;


import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

{% for channelName, channel in asyncapi.channels() %}
    {%- if channel.hasPublish() %}
        {%- for message in channel.publish().messages() %}
import {{ params['userJavaPackage'] }}.models.{{message.payload().uid() | camelCase | upperFirst}};
        {%- endfor %}
    {%- endif %}
{%- endfor %}

public final class JmsConsumers {



  {%- for serverName, server in asyncapi.servers() -%}
  {%- if server.protocol() == 'jms' -%}
  {{-  jmsConsumer(asyncapi, server, serverName)  -}}
  {%- endif -%}
  {%- endfor -%}

  {%- for serverName, server in asyncapi.servers() -%}
  {%- if server.protocol() == 'jms' -%}
  {{-  consumer(asyncapi, serverName)  -}}
  {%- endif -%}
  {%- endfor -%}

  {%- if params['defaultImplementaion'] -%}
  {%- for serverName, server in asyncapi.servers() -%}
  {%- if server.protocol() == 'jms' -%}
  {{-  defaultConsumer(asyncapi, serverName)  -}}
  {%- endif -%}
  {%- endfor -%}
  {%- endif -%}

}
