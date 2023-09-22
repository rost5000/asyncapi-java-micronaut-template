package {{ params['userJavaPackage'] }}.api.producers;

{%- from "partials/JmsProducer.java" import jmsProducer -%}
{%- from "partials/GenerateImportsForProducers.java" import generateImportsForProducers -%}

{{- generateImportsForProducers(asyncapi, params) -}}

public final class JmsProducers {
  {%- if params['generateProducers'] and params['generateProducers'] !== 'false' %}
  {%- for serverName, server in asyncapi.servers() -%}{%- if server.protocol() == 'jms' -%}
  static {{-  jmsProducer(asyncapi, server, serverName, params)  -}}
  {%- endif %}{%- endfor -%}
  {%- endif -%}
}