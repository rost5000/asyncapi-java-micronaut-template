package {{ params['userJavaPackage'] }}.api.producers;

{%- from "partials/JmsProducer.java" import jmsProducer -%}
{%- from "partials/GenerateImportsForProducers.java" import generateImportsForProducers -%}

{{- generateImportsForProducers(asyncapi) -}}

public final class JmsProducers {
  {%- for serverName, server in asyncapi.servers() -%}{%- if server.protocol() == 'jms' -%}{%- if params['generateProducers']%}
  static {{-  jmsProducer(asyncapi, server, serverName, params)  -}}
  {%- endif %}{%- endif -%}{%- endfor -%}
}