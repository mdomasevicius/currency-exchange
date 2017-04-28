package lt.danske.currency.api.client

import groovy.json.JsonOutput
import groovy.transform.PackageScope
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

import static groovyx.net.http.ContentType.JSON

class FluentRestClient {

    private final RESTClient delegate
    private final Map<String, String> defaultHeaders = [:]

    static FluentRestClient defaultRestClient() {
        return new FluentRestClient(new URL('http://localhost:8080').toURI())
    }

    FluentRestClient(URI baseUri) {
        delegate = new RESTClient(baseUri, JSON)
        delegate.handler.failure = delegate.handler.success
    }

    FluentResponse get(String path, Map<String, ?> query = [:]) {
        get(path, [:], query)
    }

    FluentResponse get(String path, Map<String, String> headers, Map<String, ?> query) {
        HttpResponseDecorator response = (HttpResponseDecorator) delegate.get(
            path: path,
            headers: headers + defaultHeaders,
            query: query)
        return FluentResponse.wrap(response)
    }

}

class FluentResponse {
    int status
    HttpStatus httpStatus
    Map<String, String> headers
    Object body
    String cookie

    @PackageScope
    static FluentResponse wrap(HttpResponseDecorator response) {
        new FluentResponse(
            status: response.status,
            httpStatus: new HttpStatus(response.status),
            headers: response.headers.collectEntries {
                [(it.name): it.value]
            },
            body: response.data,
            cookie: response.headers['Set-Cookie']?.value
        )
    }

    @Override
    String toString() {
        return """FluentResponse [status=${status},body=
            |${JsonOutput.prettyPrint(JsonOutput.toJson(body))}
            |]""".stripMargin()
    }
}
