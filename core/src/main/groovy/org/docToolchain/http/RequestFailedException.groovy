package org.docToolchain.http

import org.apache.hc.core5.http.HttpResponse

class RequestFailedException extends RuntimeException{

    RequestFailedException(HttpResponse response, Exception reason) {
        super(buildMessage(response, reason), reason);
    }

    private static String buildMessage(HttpResponse response, Exception reason) {
        String responseLog = response != null ? statusLine(response) : "<none>"
        String reasonLog = reason != null ? reason.getMessage() : "<none>"
        String possibleSolution

        switch (response.getCode()) {
            case 401:
                possibleSolution = "please check your credentials in config file or passed parameters"
                break
            case 400:
                possibleSolution = "please check the your config file or passed parameters"
                break
            case 429:
                possibleSolution = "please check if you need to decrease the rate limit in your config file"
                break
            default:
                possibleSolution = "please check your config. If you are sure that everything is correct, " +
                    "please open an issue at https://github.com/docToolchain/docToolchain/issues"
        }

        return "something went wrong - request failed" + " (" +
            "\nresponse: " + responseLog + ", " +
            "\nreason: " + reasonLog + ", " +
            "\npossible solution: " + possibleSolution +
            ")";
    }

    private static String statusLine(HttpResponse response) {
        return response.getCode() + " " + response.getReasonPhrase();
    }
}
