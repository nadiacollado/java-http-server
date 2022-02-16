package httpserver.response;

import httpserver.utils.StatusCodes;
import httpserver.utils.Constants;
import httpserver.interfaces.IResponseWriter;
import httpserver.models.Request;
import httpserver.models.Response;

public class ResponseWriter implements IResponseWriter {

    public Response buildSuccessResponse(Request request, String[] methods) {
        Response response;
        if (request.path.equals("/simple_get_with_body")) {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .addHeader("Allow", methods)
                    .setBody("Hello world")
                    .build();
        } else {
            response = new ResponseBuilder()
                    .setStatusCode(StatusCodes.SUCCESS)
                    .addHeader("Allow", methods)
                    .build();
        }
        return response;
    }

    public Response buildPageNotFoundResponse(Request request) {
        Response response = new ResponseBuilder()
                .setStatusCode(StatusCodes.PAGE_NOT_FOUND)
                .build();
        return response;
    }

    public String formatResponse(Response response) {
        String headers = null;
        StringBuilder formattedResponse = new StringBuilder();
        String statusLine = getStatusLine(response);
        formattedResponse.append(statusLine);

        if (hasHeaders(response) && (!hasBody(response))) {
            headers = stringifyHeaders(response);
            formattedResponse.append(headers);
            return formattedResponse.toString();
        }

        if (hasBody(response)){
            headers = stringifyHeaders(response);
            formattedResponse.append(headers);
            formattedResponse.append(response.body);
            return formattedResponse.toString();
        }

        formattedResponse.append(Constants.LINE_BREAK);
        return formattedResponse.toString();
    }

    public String stringifyHeaders(Response response) {
        StringBuilder formattedHeaders = new StringBuilder();
        formattedHeaders.append("Allow: ");

        String[] methodsList = response.headers.get("Allow");
        for (int i = 0; i < methodsList.length; i++) {
            formattedHeaders.append(methodsList[i]);
            if (i < (methodsList.length - 1)) {
                formattedHeaders.append(", ");
            }
        }
        formattedHeaders.append(Constants.DOUBLE_LINE_BREAK);
        return formattedHeaders.toString();
    }

    public String getStatusLine(Response response) {
        return response.protocol + " " + response.statusCode + Constants.LINE_BREAK;
    }

    private boolean hasHeaders(Response response) {
        return response.headers.size() >= 1;
    }

    private boolean hasBody(Response response) {
        return response.body != null;
    }
}
