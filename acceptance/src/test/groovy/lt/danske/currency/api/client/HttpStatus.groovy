package lt.danske.currency.api.client

class HttpStatus {

    final int code
    final int series

    HttpStatus(int code) {
        this.code = code
        series = code / 100
    }

    boolean is2xx() {
        return series == 2
    }

    boolean is3xx() {
        return series == 3
    }

    boolean is4xx() {
        return series == 4
    }

    boolean is5xx() {
        return series == 5
    }

}
