{%- from "partials/JmsConsumer.java" import jmsConsumer -%}
{%- from "partials/Consumer.java" import consumer -%}
{%- from "partials/DefaultConsumer.java" import defaultConsumer -%}
{%- from "partials/GenerateImportsForConsumers.java" import generateImportsForConsumers -%}

package {{ params['userJavaPackage'] }}.api.consumers;

{{- generateImportsForConsumers(asyncapi, params) -}}

public final class JmsConsumers {
  {%- if params['generateConsumers'] %}
  {%- for serverName, server in asyncapi.servers() -%}{%- if server.protocol() == 'jms' -%}
  static {{-  jmsConsumer(asyncapi, server, serverName, params)  -}}
  {%- endif -%}{%- endfor -%}

  {%- for serverName, server in asyncapi.servers() -%}{%- if server.protocol() == 'jms' -%}
  static {{-  consumer(asyncapi, serverName, params)  -}}
  {%- endif -%}{%- endfor -%}

  {%- if params['defaultImplementaion'] -%}{%- for serverName, server in asyncapi.servers() -%}{%- if server.protocol() == 'jms' -%}
  static {{-  defaultConsumer(asyncapi, serverName, params)  -}}
  {%- endif -%}{%- endfor -%}{%- endif -%}
  {%- endif %}

}
